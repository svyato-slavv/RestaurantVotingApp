package ru.ivanov.restaurantvotingapplication.util;


import lombok.experimental.UtilityClass;
import ru.ivanov.restaurantvotingapplication.model.Dish;
import ru.ivanov.restaurantvotingapplication.to.DishTo;

import java.util.List;

@UtilityClass
public class DishUtil {

    public static DishTo getTo(Dish dish) {
        return new DishTo(dish.id(), dish.getName(), dish.getPrice());
    }


    public static DishTo getToWithRestaurant(Dish dish) {
        return new DishTo(dish.id(), dish.getName(), dish.getPrice(), dish.getRestaurant());
    }

    public static Dish createNewFromTo(DishTo dishTo) {
        return new Dish(null, dishTo.getName(), dishTo.getPrice(), dishTo.getRestaurant());
    }

    public static Dish updateFromTo(Dish dish, DishTo dishTo) {
        dish.setName(dishTo.getName());
        dish.setPrice(dishTo.getPrice());
        dish.setRestaurant(dishTo.getRestaurant());
        return dish;
    }

    public static Dish getDishFromTo(DishTo dishTo) {
        return new Dish(dishTo.getId(), dishTo.getName(), dishTo.getPrice(), dishTo.getRestaurant());
    }

}
