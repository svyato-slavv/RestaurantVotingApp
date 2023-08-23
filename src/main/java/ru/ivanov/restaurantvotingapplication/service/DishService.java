package ru.ivanov.restaurantvotingapplication.service;

import ru.ivanov.restaurantvotingapplication.model.Dish;
import ru.ivanov.restaurantvotingapplication.model.Restaurant;
import ru.ivanov.restaurantvotingapplication.to.DishTo;

import java.util.List;
import java.util.Optional;

public interface DishService {

    Dish get(int id);

    Dish create(Dish dish);

    void delete(int id);

    List<Dish> getAll();


    void update(Dish dish, int restaurantId);

}
