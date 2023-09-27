package ru.ivanov.restaurantvotingapplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.ivanov.restaurantvotingapplication.model.Vote;

import java.time.LocalDateTime;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Integer> {

    @Modifying
    @Query("UPDATE Vote v SET v.restaurant.id = :restaurantId, v.voteDateTime = :voteDateTime WHERE v.id = :id")
    void updateVote(Integer restaurantId, LocalDateTime voteDateTime, Integer id);

    @Query("SELECT v from Vote v WHERE v.user.id=:userId AND v.voteDateTime >= :startDay AND v.voteDateTime < :endDay ")
    Vote getTodayVoteByUserId(@Param("startDay") LocalDateTime startDay, @Param("endDay") LocalDateTime endDay, @Param("userId") int userId);
}
