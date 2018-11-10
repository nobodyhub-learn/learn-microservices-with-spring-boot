package com.nobodyhub.learn.gamification.domain;

/**
 * Enumeration with the different types of Badges that a user can win.
 *
 * @author yan_h
 */
public enum Badge {
    // Badges depending on score
    BRONZE_MULTIPLICATOR,
    SILVER_MULTIPLICATOR,
    GOLD_MULTIPLICATOR,

    // Other badges won for different conditions
    FIRST_ATTEMPT,
    FIRST_WON,
    LUCKY_NUMBER
}
