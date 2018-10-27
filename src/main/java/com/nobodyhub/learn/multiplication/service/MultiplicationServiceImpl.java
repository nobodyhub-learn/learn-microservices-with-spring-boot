package com.nobodyhub.learn.multiplication.service;

import com.nobodyhub.learn.multiplication.domain.Multiplication;
import com.nobodyhub.learn.multiplication.domain.MultiplicationResultAttempt;
import com.nobodyhub.learn.multiplication.domain.User;
import com.nobodyhub.learn.multiplication.repository.MultiplicationResultAttemptRepository;
import com.nobodyhub.learn.multiplication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

/**
 * @author yan_h
 */
@Service
public class MultiplicationServiceImpl implements MultiplicationService {
    private RandomGeneratorService randomGeneratorService;
    private MultiplicationResultAttemptRepository attemptRepository;
    private UserRepository userRepository;

    @Autowired
    public MultiplicationServiceImpl(RandomGeneratorService randomGeneratorService,
                                     MultiplicationResultAttemptRepository attemptRepository,
                                     UserRepository userRepository) {
        this.randomGeneratorService = randomGeneratorService;
        this.attemptRepository = attemptRepository;
        this.userRepository = userRepository;
    }

    public Multiplication createRandomMultiplication() {
        int factorA = randomGeneratorService.generateRandomFactor();
        int factorB = randomGeneratorService.generateRandomFactor();
        return new Multiplication(factorA, factorB);

    }

    @Override
    public MultiplicationResultAttempt checkAttempt(MultiplicationResultAttempt resultAttempt) {
        // Check if the user already exists for that alias
        Optional<User> user = userRepository.findByAlias(resultAttempt.getUser().getAlias());
        boolean correct = resultAttempt.getResultAttempt() ==
                resultAttempt.getMultiplication().getFactorA() *
                        resultAttempt.getMultiplication().getFactorB();
        // Avoids 'hack' attempts
        Assert.isTrue(!resultAttempt.isCorrect(), "You can't send an attempt marked as correct!!");
        // Creates a copy, now setting the 'correct' field accordingly
        MultiplicationResultAttempt checkedAttempt = new MultiplicationResultAttempt(
                user.orElse(resultAttempt.getUser()),
                resultAttempt.getMultiplication(),
                resultAttempt.getResultAttempt(),
                correct);
        attemptRepository.save(checkedAttempt);
        return checkedAttempt;
    }

    @Override
    public List<MultiplicationResultAttempt> getStatsForUser(String userAlias) {
        return attemptRepository.findTop5ByUserAliasOrderByIdDesc(userAlias);
    }
}
