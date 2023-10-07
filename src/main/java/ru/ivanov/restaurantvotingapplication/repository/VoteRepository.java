package ru.ivanov.restaurantvotingapplication.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.ivanov.restaurantvotingapplication.model.Vote;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface VoteRepository extends BaseRepository<Vote> {

    @Modifying
    @Transactional
    @Query("UPDATE Vote v SET v.restaurant.id = :restaurantId, v.voteDateTime = :voteDateTime WHERE v.id = :id")
    void update(Integer restaurantId, LocalDateTime voteDateTime, Integer id);

    @Query("SELECT v from Vote v WHERE v.user.id=:userId AND v.voteDateTime >= :startDay AND v.voteDateTime < :endDay ")
    Optional<Vote> getTodayVoteByUserId(LocalDateTime startDay, LocalDateTime endDay, int userId);

    @Query("SELECT v from Vote v WHERE v.user.id=:userId")
    List<Vote> findAllByUserId(int userId);

    @Query("select v from Vote v where v.id =:id and v.user.id =:userId")
    Optional<Vote> findByIdAndUserId(int id, int userId);

}
