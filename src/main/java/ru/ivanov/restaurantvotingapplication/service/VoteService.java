package ru.ivanov.restaurantvotingapplication.service;

import ru.ivanov.restaurantvotingapplication.model.User;
import ru.ivanov.restaurantvotingapplication.model.Vote;

import java.util.List;
import java.util.Optional;


public interface VoteService {

    Vote get(int id, Integer userId);

    Vote vote(Integer restaurantId, User user);

    void update(Integer restaurantId, User user);

    List<Vote> allVotes(User user);

    Optional<Vote> todayVote(User user);

}
