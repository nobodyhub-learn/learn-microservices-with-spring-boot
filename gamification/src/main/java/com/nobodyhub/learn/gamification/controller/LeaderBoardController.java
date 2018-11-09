package com.nobodyhub.learn.gamification.controller;

import com.nobodyhub.learn.gamification.domain.LeaderBoardRow;
import com.nobodyhub.learn.gamification.service.LeaderBoardService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * This class implements a REST API for the Gamification LeaderBoard service.
 *
 * @author yan_h
 */
@RestController
@RequestMapping("/leaders")
public class LeaderBoardController {
    private final LeaderBoardService leaderBoardService;

    public LeaderBoardController(final LeaderBoardService leaderBoardService) {
        this.leaderBoardService = leaderBoardService;
    }

    @GetMapping
    public List<LeaderBoardRow> getLeaderBorad() {
        return leaderBoardService.getCurrentLeaderBoard();
    }
}
