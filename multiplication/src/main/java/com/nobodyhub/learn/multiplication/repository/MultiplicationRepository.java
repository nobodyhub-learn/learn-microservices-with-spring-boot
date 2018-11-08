package com.nobodyhub.learn.multiplication.repository;

import com.nobodyhub.learn.multiplication.domain.Multiplication;
import org.springframework.data.repository.CrudRepository;

/**
 * This interface allows us to save and retrieve Multiplications
 *
 * @author yan_h
 */
public interface MultiplicationRepository
        extends CrudRepository<Multiplication, Long> {
}
