package com.nobodyhub.learn.multiplication.event;

import com.nobodyhub.learn.multiplication.domain.Multiplication;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * Event that models the fact that a {@link Multiplication}
 * has been solved in the system. Provides some context
 * information about the multiplication.
 *
 * @author yan_h
 */
@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class MultiplicationSolvedEvent implements Serializable {
    private final Long multiplicationResultAttemptId;
    ;
    private final Long userId;
    private final boolean correct;
}
