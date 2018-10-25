package com.nobodyhub.learn.multiplication.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * This class represents a Multiplication in our application
 *
 * @author yan_h
 */
@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public final class Multiplication {
    private final int factorA;
    private final int factorB;
    private int result;

    // Empty constructor for JSON (de)serialization
    protected Multiplication() {
        this(0, 0);
    }
}
