package com.nobodyhub.learn.multiplication.controller;

import com.nobodyhub.learn.multiplication.domain.MultiplicationResultAttempt;
import com.nobodyhub.learn.multiplication.service.MultiplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author yan_h
 */
@RestController
@RequestMapping("/results")
public final class MultiplicationResultAttemptController {
    private final MultiplicationService multiplicationService;

    @Autowired
    public MultiplicationResultAttemptController(final MultiplicationService multiplicationService) {
        this.multiplicationService = multiplicationService;
    }

    @PostMapping
    public ResponseEntity<MultiplicationResultAttempt> postResult(@RequestBody MultiplicationResultAttempt attempt) {
        return ResponseEntity.ok(multiplicationService.checkAttempt(attempt));
    }

    @GetMapping
    public ResponseEntity<List<MultiplicationResultAttempt>> getStatistics(@RequestParam("alias") String alias) {
        return ResponseEntity.ok(
                multiplicationService.getStatsForUser(alias)
        );
    }

    @GetMapping("/{resultId}")
    ResponseEntity<MultiplicationResultAttempt> getResultById(
            @PathVariable("resultId") Long resultId) {
        return ResponseEntity.ok(multiplicationService.getResultById(resultId));
    }
}
