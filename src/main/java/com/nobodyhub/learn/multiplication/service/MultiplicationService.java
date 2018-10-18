package com.nobodyhub.learn.multiplication.service;

import com.nobodyhub.learn.multiplication.domain.Multiplication;

/**
 * @author yan_h
 */
public interface MultiplicationService {
    /**
     * Creates a Multiplication object with two randomly-generated
     * factors
     * between 11 and 99.
     *
     * @return a Multiplication object with random factors
     */
    Multiplication createRandomMultiplication();

}
