package com.nobodyhub.learn.multiplication.domain;

import lombok.Getter;
import lombok.ToString;

/**
 * This class represents a Multiplication in our application
 *
 * @author yan_h
 */
@Getter
@ToString
public class Multiplication {
    private final int factorA;
    private final int factorB;
    private int result;

    public Multiplication(int factorA, int factorB) {
        this.factorA = factorA;
        this.factorB = factorB;
        this.result = factorA * factorB;
    }
}
