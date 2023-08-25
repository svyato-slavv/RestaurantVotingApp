package ru.ivanov.restaurantvotingapplication.service;

import ru.ivanov.restaurantvotingapplication.model.Dish;
import ru.ivanov.restaurantvotingapplication.to.DishTo;

import java.util.List;


public interface DishService {

    List<Dish> getSorted();

    Dish get(int id);

    Dish create(Dish dish);

    void delete(int id);

    void update(Dish dish, DishTo dishTo);

}
