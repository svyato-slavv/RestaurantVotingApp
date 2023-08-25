package ru.ivanov.restaurantvotingapplication.util;

import lombok.experimental.UtilityClass;
import ru.ivanov.restaurantvotingapplication.model.Dish;
import ru.ivanov.restaurantvotingapplication.model.Restaurant;
import ru.ivanov.restaurantvotingapplication.to.RestaurantTo;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class RestaurantUtil {
    public static RestaurantTo getTo(Restaurant restaurant, List<Dish> menu, Integer voteCount) {
        return new RestaurantTo(restaurant.getId(), restaurant.getName(), menu.stream().map(DishUtil::getTo).collect(Collectors.toList()), voteCount);
    }
}
