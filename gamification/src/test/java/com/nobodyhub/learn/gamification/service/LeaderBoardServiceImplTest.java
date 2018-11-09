package com.nobodyhub.learn.gamification.service;

import com.nobodyhub.learn.gamification.domain.LeaderBoardRow;
import com.nobodyhub.learn.gamification.repository.ScoreCardRepository;
import org.junit.Test;
import org.mockito.Mock;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

/**
 * @author yan_h
 */
public class LeaderBoardServiceImplTest {
    private LeaderBoardServiceImpl leaderBoardService;

    @Mock
    private ScoreCardRepository scoreCardRepository;

    @Test
    public void getCurrentLeaderBoardTest() {
        // given
        Long userId = 1L;
        LeaderBoardRow leaderBoardRow = new LeaderBoardRow(userId, 300L);
        List<LeaderBoardRow> expectedLeaderBoard = Collections.singletonList(leaderBoardRow);
        given(scoreCardRepository.findFirst10())
                .willReturn(expectedLeaderBoard);
        // when
        List<LeaderBoardRow> leaderBoard = leaderBoardService.getCurrentLeaderBoard();
        // then
        assertThat(leaderBoard).isEqualTo(expectedLeaderBoard);
    }
}