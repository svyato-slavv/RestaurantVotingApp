package ru.ivanov.restaurantvotingapplication.util;

import ru.ivanov.restaurantvotingapplication.model.Vote;
import ru.ivanov.restaurantvotingapplication.to.VoteTo;

public class VoteUtil {

    public static VoteTo getTo(Vote vote) {
        return new VoteTo(vote.getId(), vote.getRestaurant().getId(), vote.getVoteDateTime());
    }
}
