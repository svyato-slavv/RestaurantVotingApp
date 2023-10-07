package ru.ivanov.restaurantvotingapplication.web.vote;

import ru.ivanov.restaurantvotingapplication.model.Vote;

import java.util.List;

import static ru.ivanov.restaurantvotingapplication.web.restaurant.RestaurantsTestData.firstRestaurant;
import static ru.ivanov.restaurantvotingapplication.web.restaurant.RestaurantsTestData.secondRestaurant;
import static ru.ivanov.restaurantvotingapplication.web.user.UserTestData.*;

public class VoteTestData {
    public static final int FIRST_VOTE_ID = 1;
    public static final int SECOND_VOTE_ID = 2;
    public static final int NOT_FOUND = 100;
    public static final Vote firstVote = new Vote(FIRST_VOTE_ID, user, firstRestaurant);
    public static final Vote secondVote = new Vote(SECOND_VOTE_ID, admin, firstRestaurant);

    public static final List<Vote> votes = List.of(firstVote, secondVote);


    public static Vote getUpdated() {
        return new Vote(FIRST_VOTE_ID, user, secondRestaurant);
    }

    public static Vote getNew() {
        return new Vote(null, user2, firstRestaurant);
    }
}
