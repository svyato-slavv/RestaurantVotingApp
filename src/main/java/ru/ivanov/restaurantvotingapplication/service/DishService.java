package ru.ivanov.restaurantvotingapplication.service;

import ru.ivanov.restaurantvotingapplication.model.Dish;
import ru.ivanov.restaurantvotingapplication.to.DishTo;

import java.time.LocalDate;
import java.util.List;


public interface DishService {

    List<Dish> getByDate(LocalDate date);

    Dish get(int id);

    Dish create(DishTo dishTo);

    void delete(int id);

    void update(DishTo dishTo);

    List<Dish> getToday(int restaurantId);

    List<Dish> getByDateAndRestaurant(int restaurantId, LocalDate localDate);

}
