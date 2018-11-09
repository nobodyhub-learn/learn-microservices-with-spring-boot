package com.nobodyhub.learn.gamification.service;

import com.nobodyhub.learn.gamification.domain.LeaderBoardRow;
import com.nobodyhub.learn.gamification.repository.ScoreCardRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of {@link LeaderBoardService}
 *
 * @author yan_h
 */
@Service
public class LeaderBoardServiceImpl implements LeaderBoardService {
    private ScoreCardRepository scoreCardRepository;

    LeaderBoardServiceImpl(ScoreCardRepository scoreCardRepository) {
        this.scoreCardRepository = scoreCardRepository;
    }

    @Override
    public List<LeaderBoardRow> getCurrentLeaderBoard() {
        return scoreCardRepository.findFirst10();
    }
}
