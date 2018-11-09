package com.nobodyhub.learn.gamification.service;

import com.nobodyhub.learn.gamification.domain.Badge;
import com.nobodyhub.learn.gamification.domain.BadgeCard;
import com.nobodyhub.learn.gamification.domain.GameStats;
import com.nobodyhub.learn.gamification.domain.ScoreCard;
import com.nobodyhub.learn.gamification.repository.BadgeCardRepository;
import com.nobodyhub.learn.gamification.repository.ScoreCardRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

/**
 * @author yan_h
 */
public class GameServiceImplTest {
    private GameServiceImpl gameService;
    @Mock
    private ScoreCardRepository scoreCardRepository;
    @Mock
    private BadgeCardRepository badgeCardRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.gameService = new GameServiceImpl(scoreCardRepository, badgeCardRepository);
    }

    @Test
    public void newAttemptForUserTest_FirstAttempt() {
        //given
        Long userId = 1L;
        Long attemptId = 8L;
        int totalScore = 10;
        ScoreCard scoreCard = new ScoreCard(userId, attemptId);
        given(scoreCardRepository.getTotalScoreForUser(userId))
                .willReturn(totalScore);
        given(scoreCardRepository.findByUserIdOrderByScoreTimestampDesc(userId))
                .willReturn(Collections.singletonList(scoreCard));
        given(badgeCardRepository.findByUserIdOrderByBadgeTimestampDesc(userId))
                .willReturn(Collections.emptyList());
        //when
        GameStats iteration = gameService.newAttemptForUser(userId, attemptId, true);
        //then
        assertThat(iteration.getScore()).isEqualTo(scoreCard.getScore());
        assertThat((iteration.getBadges())).containsOnly(Badge.FIRST_WON);
    }

    @Test
    public void newAttemptForUserTest_BronzeBadge() {
        //given
        Long userId = 1L;
        Long attemptId = 29L;
        int totalScore = 100;
        BadgeCard firstWonBadge = new BadgeCard(userId, Badge.FIRST_WON);
        given(scoreCardRepository.getTotalScoreForUser(userId))
                .willReturn(totalScore);
        given(scoreCardRepository.findByUserIdOrderByScoreTimestampDesc(userId))
                .willReturn(createNScoreCards(10, userId));
        given(badgeCardRepository.findByUserIdOrderByBadgeTimestampDesc(userId))
                .willReturn(Collections.singletonList(firstWonBadge));
        //when
        GameStats iteration = gameService.newAttemptForUser(userId, attemptId, true);
        //then
        assertThat(iteration.getScore()).isEqualTo(ScoreCard.DEFAULT_SCORE);
        assertThat(iteration.getBadges()).containsOnly(Badge.BRONZE_MULTIPLICATOR);
    }

    @Test
    public void newAttemptForUserTest_WrongAttempt() {
        //given
        Long userId = 1L;
        Long attemptId = 8L;
        int totalScore = 10;
        ScoreCard scoreCard = new ScoreCard(userId, attemptId);
        given(scoreCardRepository.getTotalScoreForUser(userId))
                .willReturn(totalScore);
        given(badgeCardRepository.findByUserIdOrderByBadgeTimestampDesc(userId))
                .willReturn(Collections.emptyList());
        //when
        GameStats iteration = gameService.newAttemptForUser(userId, attemptId, false);
        //given
        assertThat(iteration.getScore()).isEqualTo(0);
        assertThat(iteration.getBadges()).isEmpty();
    }

    private List<ScoreCard> createNScoreCards(int n, Long userId) {
        return IntStream.range(0, n)
                .mapToObj(i -> new ScoreCard(userId, (long) i))
                .collect(Collectors.toList());
    }


    @Test
    public void retrieveStatsForUserTest() {
        //given
        Long userId = 1L;
        int totalScore = 1000;
        BadgeCard badgeCard = new BadgeCard(userId, Badge.SILVER_MULTIPLICATOR);
        given(scoreCardRepository.getTotalScoreForUser(userId))
                .willReturn(totalScore);
        given(badgeCardRepository.findByUserIdOrderByBadgeTimestampDesc(userId))
                .willReturn(Collections.singletonList(badgeCard));
        //when
        GameStats stats = gameService.retrieveStatsForUser(userId);
        //then
        assertThat(stats.getScore()).isEqualTo(totalScore);
        assertThat(stats.getBadges()).containsOnly(Badge.SILVER_MULTIPLICATOR);
    }
}