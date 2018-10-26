package com.nobodyhub.learn.multiplication.service;

import com.nobodyhub.learn.multiplication.domain.Multiplication;
import com.nobodyhub.learn.multiplication.domain.MultiplicationResultAttempt;
import com.nobodyhub.learn.multiplication.domain.User;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.BDDMockito.given;

/**
 * @author yan_h
 */
public class MultiplicationServiceImplTest {
    private MultiplicationServiceImpl multiplicationServiceImpl;

    @Mock
    private RandomGeneratorService randomGeneratorService;

    @Before
    public void setUp() {
        // With this call to initMocks we tell Mockito to process the annotations
        MockitoAnnotations.initMocks(this);
        multiplicationServiceImpl = new MultiplicationServiceImpl
                (randomGeneratorService);
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
        MultiplicationResultAttempt attempt = new MultiplicationResultAttempt(user, multiplication, 3000, true);
        //when
        MultiplicationResultAttempt attempResult = multiplicationServiceImpl.checkAttempt(attempt);
        //assert
        assertThat(attempResult.isCorrect()).isTrue();
    }

    @Test
    public void checkWrongAttemptTest() {
        //given
        Multiplication multiplication = new Multiplication(50, 60);
        User user = new User("yan");
        MultiplicationResultAttempt attempt = new MultiplicationResultAttempt(user, multiplication, 3010, false);
        //when
        MultiplicationResultAttempt attempResult = multiplicationServiceImpl.checkAttempt(attempt);
        //assert
        assertThat(attempResult.isCorrect()).isFalse();
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
}