package com.nobodyhub.learn.multiplication.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * This class represents a Multiplication in our application
 *
 * @author yan_h
 */
@AllArgsConstructor
@Getter
@ToString
public class Multiplication {
    private int factorA;
    private int factorB;
    private int result;
}
