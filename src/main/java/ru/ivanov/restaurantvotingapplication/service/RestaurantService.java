package ru.ivanov.restaurantvotingapplication.service;

import ru.ivanov.restaurantvotingapplication.model.Dish;
import ru.ivanov.restaurantvotingapplication.model.Restaurant;
import ru.ivanov.restaurantvotingapplication.to.DishTo;
import ru.ivanov.restaurantvotingapplication.to.RestaurantTo;

import java.util.Date;
import java.util.List;

public interface RestaurantService {

    Restaurant get(int id);

    Restaurant create(Restaurant restaurant);

    void update(Restaurant restaurant);

    void delete(int id);

    List<Restaurant> restaurantList();

    List<Dish> showTodayMenu(int id);
    void deleteOldTodayMenu(int restaurantId);

   void newMenu(List<DishTo> newMenuTo, int restaurantId);

}
