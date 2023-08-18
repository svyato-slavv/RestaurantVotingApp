package ru.ivanov.restaurantvotingapplication.util;

import ru.ivanov.restaurantvotingapplication.model.Dish;
import ru.ivanov.restaurantvotingapplication.model.Restaurant;
import ru.ivanov.restaurantvotingapplication.to.RestaurantTo;

import java.util.List;
import java.util.stream.Collectors;

public class RestaurantUtil {
    public static RestaurantTo getTos(Restaurant restaurant, List<Dish> menu) {
        return new RestaurantTo(restaurant.getId(), restaurant.getName(), menu.stream().map(DishUtil::getTos).collect(Collectors.toList()));
    }
}
