package ru.ivanov.restaurantvotingapplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ivanov.restaurantvotingapplication.model.Vote;

@Repository
public interface VoteRepository extends JpaRepository<Vote,Integer> {
}
