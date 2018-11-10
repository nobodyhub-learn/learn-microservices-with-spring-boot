package com.nobodyhub.learn.gamification.client.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.nobodyhub.learn.gamification.client.MultiplicationResultAttemptDeserializer;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * Identifies the attempt from a user to solve a multiplication
 *
 * @author yan_h
 */
@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
@JsonDeserialize(using =
        MultiplicationResultAttemptDeserializer.class)
public final class MultiplicationResultAttempt {
    private final String userAlias;

    private final int multiplicationFactorA;
    private final int multiplicationFactorB;
    private final int resultAttempt;

    private final boolean correct;

    // Empty constructor for JSON/JPA
    MultiplicationResultAttempt() {
        userAlias = null;
        multiplicationFactorA = -1;
        multiplicationFactorB = -1;
        resultAttempt = -1;
        correct = false;
    }
}
