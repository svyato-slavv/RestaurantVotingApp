package ru.ivanov.restaurantvotingapplication.service;

import ru.ivanov.restaurantvotingapplication.model.Dish;

import java.util.List;
import java.util.Optional;

public interface DishService {

    Optional<Dish> get(int id);

    Dish create(Dish dish);

    void delete(int id);

    List<Dish> getAll();


    void update(Dish dish);
}
