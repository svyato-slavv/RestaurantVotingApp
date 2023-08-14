package ru.ivanov.restaurantvotingapplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.ivanov.restaurantvotingapplication.model.Dish;

import java.util.Date;
import java.util.List;

@Repository
public interface DishRepository extends JpaRepository<Dish, Integer> {

     List<Dish> findAllByRestaurantId(int id);
}
