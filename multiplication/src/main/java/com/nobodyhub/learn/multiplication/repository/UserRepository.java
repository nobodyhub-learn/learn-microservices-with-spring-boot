package com.nobodyhub.learn.multiplication.repository;

import com.nobodyhub.learn.multiplication.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * This interface allows us to save and retrieve Users
 *
 * @author yan_h
 */
public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByAlias(final String alias);
}

