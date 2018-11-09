package com.nobodyhub.learn.gamification.repository;

import com.nobodyhub.learn.gamification.domain.LeaderBoardRow;
import com.nobodyhub.learn.gamification.domain.ScoreCard;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Handles CRUD operations with ScoreCards
 *
 * @author yan_h
 */
public interface ScoreCardRepository extends CrudRepository<ScoreCard, Long> {
    /**
     * Gets the total score for a given user, being the sum of the scores of all his ScoreCards
     *
     * @param userId the id of the user for which the total score should be retrieved
     * @return the total score for the given user
     */
    @Query("SELECT SUM(s.score) from ScoreCard s WHERE s.userId = :userId GROUP BY s.userId")
    int getTotalScoreForUser(@Param("userId") final Long userId);

    /**
     * Retrieves a list of {@link LeaderBoardRow}s representing the Leader Board of users and their total score
     *
     * @return the leader board, sorted by the highest score first
     */
    @Query("SELECT NEW com.nobodyhub.learn.gamification.domain.LeaderBoardRow(s.userId, SUM(s.score)) FROM ScoreCard s GROUP BY s.userId ORDER BY SUM(S.score) DESC")
    List<LeaderBoardRow> findFirst10();

    /**
     * Retrieves all the ScoreCard for a given user, identiied by his user id
     *
     * @param userId the id for the user
     * @return a list of containing all the ScoreCards for the given user, sorted by most recent
     */
    List<ScoreCard> findByUserIdOrderByScoreTimestampDesc(final Long userId);
}
