package com.nobodyhub.learn.gamification.service;

import com.nobodyhub.learn.gamification.domain.GameStats;
import com.nobodyhub.learn.gamification.repository.BadgeCardRepository;
import com.nobodyhub.learn.gamification.repository.ScoreCardRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public GameStats newAttemptForUser(Long userId, Long attemptId, boolean correct) {
        return null;
    }

    @Override
    public GameStats retrieveStatsForUser(Long userId) {
        return null;
    }
}
