package com.nobodyhub.learn.gamification.event;

import com.nobodyhub.learn.gamification.service.GameService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.stereotype.Component;

/**
 * This class receives the events and triggers the associated business logic
 *
 * @author yan_h
 */
@Component
@Slf4j
public class EventHandler {
    private GameService gameService;

    EventHandler(final GameService gameService) {
        this.gameService = gameService;
    }

    void handleMultiplicationSolved(final MultiplicationSolvedEvent event) {
        log.info("Multiplication Solved Event received: {}",
                event.getMultiplicationResultAttemptId());
        try {
            gameService.newAttemptForUser(event.getUserId(),
                    event.getMultiplicationResultAttemptId(),
                    event.isCorrect());
        } catch (final Exception e) {
            log.error("Error whgen trying to process MultiplcationSolved Event", e);
            // Avoids the event to be re-queued and reprocessed
            throw new AmqpRejectAndDontRequeueException(e);
        }
    }
}
