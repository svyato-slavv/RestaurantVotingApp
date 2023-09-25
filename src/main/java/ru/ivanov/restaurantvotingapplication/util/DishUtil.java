package ru.ivanov.restaurantvotingapplication.util;


import lombok.experimental.UtilityClass;
import ru.ivanov.restaurantvotingapplication.model.Dish;
import ru.ivanov.restaurantvotingapplication.model.Restaurant;
import ru.ivanov.restaurantvotingapplication.to.DishTo;

import java.time.LocalDate;


@UtilityClass
public class DishUtil {

    public static DishTo getTo(Dish dish) {
        return new DishTo(dish.id(), dish.getName(), dish.getPrice(),dish.getDate());
    }


    public static Dish createNewFromTo(DishTo dishTo, Restaurant restaurant) {
        return new Dish(null, dishTo.getName(), dishTo.getPrice(), restaurant);
    }

    public static Dish updateFromTo(Dish dish, DishTo dishTo, Restaurant restaurant) {
        dish.setName(dishTo.getName());
        dish.setPrice(dishTo.getPrice());
        dish.setRestaurant(restaurant);
        return dish;
    }

    public static Dish getDishFromTo(DishTo dishTo) {
        return new Dish(dishTo.getId(), dishTo.getName(), dishTo.getPrice(), LocalDate.now());
    }

}
