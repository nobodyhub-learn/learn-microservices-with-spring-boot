package com.nobodyhub.learn.gamification.service;

import com.nobodyhub.learn.gamification.domain.Badge;
import com.nobodyhub.learn.gamification.domain.BadgeCard;
import com.nobodyhub.learn.gamification.domain.GameStats;
import com.nobodyhub.learn.gamification.domain.ScoreCard;
import com.nobodyhub.learn.gamification.repository.BadgeCardRepository;
import com.nobodyhub.learn.gamification.repository.ScoreCardRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implement methods in {@link GameService}
 *
 * @author yan_h
 */
@Service
@Slf4j
public class GameServiceImpl implements GameService {
    private final ScoreCardRepository scoreCardRepository;
    private final BadgeCardRepository badgeCardRepository;

    @Autowired
    public GameServiceImpl(final ScoreCardRepository scoreCardRepository,
                           final BadgeCardRepository badgeCardRepository) {
        this.scoreCardRepository = scoreCardRepository;
        this.badgeCardRepository = badgeCardRepository;
    }

    @Override
    public GameStats newAttemptForUser(Long userId,
                                       Long attemptId,
                                       boolean correct) {
        if (correct) {
            ScoreCard scoreCard = new ScoreCard(userId, attemptId);
            scoreCardRepository.save(scoreCard);
            log.info("User with id {} scored {} points for attempt id {}",
                    userId, scoreCard.getScore(), attemptId);
            List<BadgeCard> badgeCards = processForBadges(userId, attemptId);
            return new GameStats(userId, scoreCard.getScore(),
                    badgeCards
                            .stream()
                            .map(BadgeCard::getBadge)
                            .collect(Collectors.toList()));
        }
        return GameStats.emptyStats(userId);
    }

    /**
     * Checks the total score and the different score cards obtained
     * to give new badges in case their conditions are met.
     *
     * @param userId
     * @param attemptId
     * @return
     */
    private List<BadgeCard> processForBadges(Long userId, Long attemptId) {
        List<BadgeCard> badgeCards = new ArrayList<>();
        int totalScore = scoreCardRepository.getTotalScoreForUser(userId);
        log.info("New score for user {} is {}", userId, totalScore);
        List<ScoreCard> scoreCardList = scoreCardRepository.findByUserIdOrderByScoreTimestampDesc(userId);
        List<BadgeCard> badgeCardList = badgeCardRepository.findByUserIdOrderByBadgeTimestampDesc(userId);
        checkAndGiveBadgeBasedOnScore(badgeCardList, Badge.BRONZE_MULTIPLICATOR, totalScore, 100, userId)
                .ifPresent(badgeCards::add);
        checkAndGiveBadgeBasedOnScore(badgeCardList, Badge.SILVER_MULTIPLICATOR, totalScore, 500, userId)
                .ifPresent(badgeCards::add);
        checkAndGiveBadgeBasedOnScore(badgeCardList, Badge.GOLD_MULTIPLICATOR, totalScore, 999, userId)
                .ifPresent(badgeCards::add);
        if (scoreCardList.size() == 1 &&
                !containsBadge(badgeCardList, Badge.FIRST_WON)) {
            BadgeCard firstWonBadge = giveBadgeToUser(Badge.FIRST_WON, userId);
            badgeCards.add(firstWonBadge);
        }
        return badgeCards;
    }

    @Override
    public GameStats retrieveStatsForUser(Long userId) {
        int score = scoreCardRepository.getTotalScoreForUser(userId);
        List<BadgeCard> badgeCards = badgeCardRepository.findByUserIdOrderByBadgeTimestampDesc(userId);
        return new GameStats(userId, score,
                badgeCards.stream()
                        .map(BadgeCard::getBadge)
                        .collect(Collectors.toList()));
    }

    /**
     * Convenience method to check the current score against
     * the different thresholds to gain badges.
     * It also assigns badge to user if the conditions are met.
     */
    private Optional<BadgeCard> checkAndGiveBadgeBasedOnScore(final List<BadgeCard> badgeCards,
                                                              final Badge badge,
                                                              final int score,
                                                              final int scoreThreshold,
                                                              final long userId) {
        if (score >= scoreThreshold && !containsBadge(badgeCards, badge)) {
            return Optional.of(giveBadgeToUser(badge, userId));
        }
        return Optional.empty();
    }

    /**
     * Checks if the passed list of badges includes the one being checked
     */
    private boolean containsBadge(final List<BadgeCard>
                                          badgeCards,
                                  final Badge badge) {
        return badgeCards.stream().anyMatch(b -> b.getBadge().equals(badge));
    }

    /**
     * Assigns a new badge to the given user
     */
    private BadgeCard giveBadgeToUser(final Badge badge, final
    Long userId) {
        BadgeCard badgeCard = new BadgeCard(userId, badge);
        badgeCardRepository.save(badgeCard);
        log.info("User with id {} won a new badge: {}", userId,
                badge);
        return badgeCard;
    }
}
