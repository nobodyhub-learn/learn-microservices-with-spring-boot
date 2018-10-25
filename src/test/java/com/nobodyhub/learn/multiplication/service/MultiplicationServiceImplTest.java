package com.nobodyhub.learn.multiplication.service;

import com.nobodyhub.learn.multiplication.domain.Multiplication;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.BDDMockito.given;

/**
 * @author yan_h
 */
public class MultiplicationServiceImplTest {
    private MultiplicationServiceImpl multiplicationServiceImpl;

    @Mock
    private RandomGeneratorService randomGeneratorService;

    @Before
    public void setUp() {
        // With this call to initMocks we tell Mockito to process the annotations
        MockitoAnnotations.initMocks(this);
        multiplicationServiceImpl = new MultiplicationServiceImpl
                (randomGeneratorService);
    }

    @Test
    public void createRandomMultiplicationTest() {
        // given
        given(randomGeneratorService.generateRandomFactor()).willReturn(50, 30);
        //when
        Multiplication multiplication = multiplicationServiceImpl.createRandomMultiplication();
        //assert
        assertThat(multiplication.getFactorA()).isEqualTo(50);
        assertThat(multiplication.getFactorA()).isEqualTo(30);
        assertThat(multiplication.getFactorA()).isEqualTo(1500);
    }
}