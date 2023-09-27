package ru.ivanov.restaurantvotingapplication.service;

import ru.ivanov.restaurantvotingapplication.model.Restaurant;
import ru.ivanov.restaurantvotingapplication.model.User;
import ru.ivanov.restaurantvotingapplication.model.Vote;

import java.util.Optional;


public interface VoteService {
    void vote(Integer restaurantId, User user);

    void update(Integer restaurantId, User user);

    Vote todayVote(User user);

}
