package ru.ivanov.restaurantvotingapplication.service;

import ru.ivanov.restaurantvotingapplication.model.Dish;
import ru.ivanov.restaurantvotingapplication.model.Restaurant;

import java.util.List;
import java.util.Optional;

public interface RestaurantService {

    Restaurant get(int id);

    Restaurant create(Restaurant restaurant);

    void update(Restaurant restaurant);

    void delete(int id);

    List<Restaurant> restaurantList();

    List<Dish> showRestaurantMenu(int id);
}
