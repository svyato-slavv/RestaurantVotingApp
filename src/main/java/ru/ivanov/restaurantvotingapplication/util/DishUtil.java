package ru.ivanov.restaurantvotingapplication.util;

import ru.ivanov.restaurantvotingapplication.model.Dish;
import ru.ivanov.restaurantvotingapplication.model.Restaurant;
import ru.ivanov.restaurantvotingapplication.to.DishTo;
import ru.ivanov.restaurantvotingapplication.to.RestaurantTo;

import java.util.List;

public class DishUtil {
    public static DishTo getTos(Dish dish) {
        return new DishTo(dish.getName(), dish.getPrice());
    }
    public static DishTo getTosWithRestaurant(Dish dish) {
        return new DishTo( dish.getName(), dish.getPrice(),dish.getRestaurant());
    }
}
