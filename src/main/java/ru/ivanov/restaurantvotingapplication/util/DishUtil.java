package ru.ivanov.restaurantvotingapplication.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;
import org.springframework.beans.factory.annotation.Autowired;
import ru.ivanov.restaurantvotingapplication.model.Dish;
import ru.ivanov.restaurantvotingapplication.model.Restaurant;
import ru.ivanov.restaurantvotingapplication.service.RestaurantService;
import ru.ivanov.restaurantvotingapplication.to.DishTo;
import ru.ivanov.restaurantvotingapplication.to.RestaurantTo;

import java.util.Date;
import java.util.List;

@UtilityClass
public class DishUtil {

    public static final Date TODAY = new Date();

    public static DishTo getTos(Dish dish) {
        return new DishTo(dish.getName(), dish.getPrice());
    }

    public static List<Dish> menuFromTo(List<DishTo> dishToList){
        return dishToList.stream().map(DishUtil::getDishFromTo).toList();
    }

    public static DishTo getTosWithRestaurant(Dish dish) {
        return new DishTo(dish.getName(), dish.getPrice(), dish.getRestaurant());
    }

    public static Dish createNewFromTo(DishTo dishTo) {
        return new Dish(null, dishTo.getName(), dishTo.getPrice(), dishTo.getRestaurant());
    }

    public static Dish getDishFromTo(DishTo dishTo) {
        return new Dish(dishTo.getId(), dishTo.getName(), dishTo.getPrice(), dishTo.getRestaurant());
    }

}
