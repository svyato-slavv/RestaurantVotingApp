package ru.ivanov.restaurantvotingapplication.service;

import ru.ivanov.restaurantvotingapplication.model.Dish;
import ru.ivanov.restaurantvotingapplication.model.Restaurant;
import ru.ivanov.restaurantvotingapplication.to.DishTo;

import java.time.LocalDate;
import java.util.List;


public interface DishService {

    List<Dish> getByDate(LocalDate date);

    Dish get(int id);

    Dish create(Dish dish);

    void delete(int id);

    void update(Dish dish, DishTo dishTo, Restaurant restaurant);
    List<Dish> getToday(int restaurantId);
    List<Dish> getByDateAndRestaurant(int restaurantId, LocalDate localDate);

}
