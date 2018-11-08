package com.nobodyhub.learn.multiplication.service;

import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * @author yan_h
 */
@Service
public class RandomGeneratorServiceImpl implements RandomGeneratorService {
    final static int MINIMUM_FACTOR = 11;
    final static int MAXIMUM_FACTOR = 99;

    @Override
    public int generateRandomFactor() {
        return new Random().nextInt((MAXIMUM_FACTOR - MINIMUM_FACTOR) + 1) + MINIMUM_FACTOR;
    }
}
