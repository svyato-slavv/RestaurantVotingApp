package ru.ivanov.restaurantvotingapplication.util;

import lombok.experimental.UtilityClass;
import ru.ivanov.restaurantvotingapplication.model.Dish;
import ru.ivanov.restaurantvotingapplication.model.Restaurant;
import ru.ivanov.restaurantvotingapplication.to.RestaurantTo;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class RestaurantUtil {
    public static RestaurantTo getTos(Restaurant restaurant, List<Dish> menu) {
        return new RestaurantTo(restaurant.getId(), restaurant.getName(), menu.stream().map(DishUtil::getTos).collect(Collectors.toList()));
    }

    public static Restaurant getFromTo(RestaurantTo restaurantTo) {
        return new Restaurant(restaurantTo.getId(), restaurantTo.getName(), DishUtil.menuFromTo(restaurantTo.getMenu()));
    }
}
