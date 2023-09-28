package ru.ivanov.restaurantvotingapplication.service;

import ru.ivanov.restaurantvotingapplication.model.User;
import ru.ivanov.restaurantvotingapplication.model.Vote;

import java.util.List;


public interface VoteService {
    void vote(Integer restaurantId, User user);

    void update(Integer restaurantId, User user);

    List<Vote> allVotes(User user);

    Vote todayVote(User user);

}
