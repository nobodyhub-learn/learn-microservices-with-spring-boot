package com.nobodyhub.learn.multiplication.service;

import com.nobodyhub.learn.multiplication.domain.Multiplication;
import com.nobodyhub.learn.multiplication.domain.MultiplicationResultAttempt;
import com.nobodyhub.learn.multiplication.domain.User;
import com.nobodyhub.learn.multiplication.event.EventDispatcher;
import com.nobodyhub.learn.multiplication.event.MultiplicationSolvedEvent;
import com.nobodyhub.learn.multiplication.repository.MultiplicationResultAttemptRepository;
import com.nobodyhub.learn.multiplication.repository.UserRepository;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

/**
 * @author yan_h
 */
public class MultiplicationServiceImplTest {
    private MultiplicationServiceImpl multiplicationServiceImpl;

    @Mock
    private RandomGeneratorService randomGeneratorService;
    @Mock
    private MultiplicationResultAttemptRepository attemptRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private EventDispatcher eventDispatcher;


    @Before
    public void setUp() {
        // With this call to initMocks we tell Mockito to process the annotations
        MockitoAnnotations.initMocks(this);
        multiplicationServiceImpl = new MultiplicationServiceImpl(randomGeneratorService, attemptRepository, userRepository, eventDispatcher);
    }

    @Test
    public void createRandomMultiplicationTest() {
        // given
        given(randomGeneratorService.generateRandomFactor()).willReturn(50, 30);
        //when
        Multiplication multiplication = multiplicationServiceImpl.createRandomMultiplication();
        //assert
        assertThat(multiplication.getFactorA()).isEqualTo(50);
        assertThat(multiplication.getFactorB()).isEqualTo(30);
        assertThat(multiplication.getResult()).isEqualTo(1500);
    }

    @Test
    public void checkCorrectAttempTest() {
        //given
        Multiplication multiplication = new Multiplication(50, 60);
        User user = new User("yan");
        MultiplicationResultAttempt attempt = new MultiplicationResultAttempt(user, multiplication, 3000, false);
        MultiplicationResultAttempt verifiedAttempt = new MultiplicationResultAttempt(user, multiplication, 3000, true);
        MultiplicationSolvedEvent event = new MultiplicationSolvedEvent(attempt.getId(),
                attempt.getUser().getId(), true);
        given(userRepository.findByAlias("yan")).willReturn(Optional.empty());
        //when
        MultiplicationResultAttempt attempResult = multiplicationServiceImpl.checkAttempt(attempt);
        //assert
        assertThat(attempResult.isCorrect()).isTrue();
        verify(attemptRepository).save(verifiedAttempt);
        verify(eventDispatcher).send(eq(event));
    }

    @Test
    public void checkWrongAttemptTest() {
        //given
        Multiplication multiplication = new Multiplication(50, 60);
        User user = new User("yan");
        MultiplicationResultAttempt attempt = new MultiplicationResultAttempt(user, multiplication, 3010, false);
        MultiplicationSolvedEvent event = new MultiplicationSolvedEvent(attempt.getId(),
                attempt.getUser().getId(), false);
        given(userRepository.findByAlias("yan")).willReturn(Optional.empty());
        //when
        MultiplicationResultAttempt attempResult = multiplicationServiceImpl.checkAttempt(attempt);
        //assert
        assertThat(attempResult.isCorrect()).isFalse();
        verify(attemptRepository).save(attempt);
        verify(eventDispatcher).send(eq(event));
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkCheatAttemptTest() {
        //given
        Multiplication multiplication = new Multiplication(50, 60);
        User user = new User("yan");
        MultiplicationResultAttempt attempt = new MultiplicationResultAttempt(user, multiplication, 3010, true);
        //when
        multiplicationServiceImpl.checkAttempt(attempt);
    }

    @Test
    public void retrieveStatsTest() {
        //given
        Multiplication multiplication = new Multiplication(50, 60);
        User user = new User("yan");
        MultiplicationResultAttempt attempt1 = new MultiplicationResultAttempt(user, multiplication, 3010, false);
        MultiplicationResultAttempt attempt2 = new MultiplicationResultAttempt(user, multiplication, 3051, false);
        List<MultiplicationResultAttempt> latestAttempts = Lists.newArrayList(attempt1, attempt2);
        given(userRepository.findByAlias("yan")).willReturn(Optional.empty());
        given(attemptRepository.findTop5ByUserAliasOrderByIdDesc("yan")).willReturn(latestAttempts);
        //when
        List<MultiplicationResultAttempt> latestAttemptResult = multiplicationServiceImpl.getStatsForUser("yan");
        //then
        assertThat(latestAttemptResult).isEqualTo(latestAttempts);
    }
}