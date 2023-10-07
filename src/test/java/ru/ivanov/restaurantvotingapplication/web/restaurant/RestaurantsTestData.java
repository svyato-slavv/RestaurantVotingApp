package ru.ivanov.restaurantvotingapplication.web.restaurant;

import ru.ivanov.restaurantvotingapplication.MatcherFactory;
import ru.ivanov.restaurantvotingapplication.model.Restaurant;
import ru.ivanov.restaurantvotingapplication.to.RestaurantTo;

import java.util.List;

public class RestaurantsTestData {
    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Restaurant.class, "votes", "dishes");

    public static final MatcherFactory.Matcher<RestaurantTo> RESTAURANT_TO_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(RestaurantTo.class);
    public static final int FIRST_RESTAURANT_ID = 1;
    public static final int SECOND_RESTAURANT_ID = 2;
    public static final int THIRD_RESTAURANT_ID = 3;
    public static final int FOURTH_RESTAURANT_ID = 4;

    public static final int NOT_FOUND = 100;

    public static final Restaurant firstRestaurant = new Restaurant(FIRST_RESTAURANT_ID, "Евразия", null);

    public static final Restaurant secondRestaurant = new Restaurant(SECOND_RESTAURANT_ID, "Токио-Сити", null);

    public static final Restaurant thirdRestaurant = new Restaurant(THIRD_RESTAURANT_ID, "Бахрома", null);

    public static final Restaurant fourthRestaurant = new Restaurant(FOURTH_RESTAURANT_ID, "Ель", null);

    public static final List<Restaurant> restaurants = List.of(firstRestaurant, secondRestaurant, thirdRestaurant, fourthRestaurant);

    public static Restaurant getNew() {
        return new Restaurant(null, "New Restaurant");
    }

    public static Restaurant getUpdated() {
        return new Restaurant(SECOND_RESTAURANT_ID, "Новое название ресторана 2");
    }
}
