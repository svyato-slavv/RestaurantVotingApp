package ru.ivanov.restaurantvotingapplication.util;


import lombok.experimental.UtilityClass;
import ru.ivanov.restaurantvotingapplication.model.Dish;
import ru.ivanov.restaurantvotingapplication.model.Restaurant;
import ru.ivanov.restaurantvotingapplication.to.DishTo;


@UtilityClass
public class DishUtil {

    public static DishTo getTo(Dish dish) {
        return new DishTo(dish.id(), dish.getName(), dish.getPrice(), dish.getDate(), dish.getRestaurant().getId());
    }


    public static Dish createNewFromTo(DishTo dishTo, Restaurant restaurant) {
        return new Dish(null, dishTo.getName(), dishTo.getPrice(), restaurant, dishTo.getDate());
    }

}
