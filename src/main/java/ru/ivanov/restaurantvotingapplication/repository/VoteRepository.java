package ru.ivanov.restaurantvotingapplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.ivanov.restaurantvotingapplication.model.Vote;

import java.util.Date;
import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote,Integer> {

    Optional<Vote> findAllByUser_Id(int id);
    @Modifying
    @Query("UPDATE Vote v SET v.restaurant.id = :restaurantId, v.voteDate = :date WHERE v.id = :id")
    void updateVote(Integer restaurantId, Date date, Integer id);
}
