package ru.ivanov.restaurantvotingapplication.web.dish;

import ru.ivanov.restaurantvotingapplication.MatcherFactory;
import ru.ivanov.restaurantvotingapplication.model.Dish;
import ru.ivanov.restaurantvotingapplication.to.DishTo;

import java.time.LocalDate;
import java.util.List;

import static ru.ivanov.restaurantvotingapplication.web.restaurant.RestaurantsTestData.*;

public class DishTestData {

    public static final MatcherFactory.Matcher<Dish> DISH_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Dish.class, "date", "restaurant");
    public static final MatcherFactory.Matcher<DishTo> DISH_TO_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(DishTo.class, "date","restaurantId");


    public static final int FIRST_DISH_ID = 1;
    public static final int SECOND_DISH_ID = 2;
    public static final int THIRD_DISH_ID = 3;
    public static final int FOURTH_DISH_ID = 4;
    public static final int FIFTH_DISH_ID = 5;
    public static final int SIXTH_DISH_ID = 6;
    public static final int SEVENTH_DISH_ID = 7;
    public static final int EIGHTH_DISH_ID = 8;

    public static final int NOT_FOUND = 100;

    public static final Dish firstDish = new Dish(FIRST_DISH_ID, "Завтрак1", 100,firstRestaurant,LocalDate.now());
    public static final Dish secondDish = new Dish(SECOND_DISH_ID, "Обед1", 150,firstRestaurant,LocalDate.now());
    public static final Dish thirdDish = new Dish(THIRD_DISH_ID, "Завтрак2", 200,secondRestaurant,LocalDate.now());
    public static final Dish fourthDish = new Dish(FOURTH_DISH_ID, "Обед2", 100,secondRestaurant,LocalDate.now());
    public static final Dish fifthDish = new Dish(FIFTH_DISH_ID, "Завтрак3", 100,thirdRestaurant,LocalDate.now());
    public static final Dish sixthDish = new Dish(SIXTH_DISH_ID, "Обед3", 100,thirdRestaurant,LocalDate.now());
    public static final Dish seventhDish = new Dish(SEVENTH_DISH_ID, "Завтрак4", 100,fourthRestaurant,LocalDate.now());
    public static final Dish eighthDish = new Dish(EIGHTH_DISH_ID, "Обед4", 100,fourthRestaurant,LocalDate.now());

    public static final List<Dish> dishes = List.of(
            firstDish,
            secondDish,
            thirdDish,
            fourthDish,
            fifthDish,
            sixthDish,
            seventhDish,
            eighthDish
    );

    public static DishTo getNew() {
        return new DishTo(null, "Новая еда", LocalDate.now().plusDays(1),500,secondRestaurant.id());
    }
    public static DishTo getUpdated() {
        return new DishTo(FIFTH_DISH_ID,"Завтрак1 обновленный",LocalDate.now(),800,secondRestaurant.id());
    }
}
