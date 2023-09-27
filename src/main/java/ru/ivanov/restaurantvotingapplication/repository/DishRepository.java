package ru.ivanov.restaurantvotingapplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.ivanov.restaurantvotingapplication.model.Dish;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface DishRepository extends JpaRepository<Dish, Integer> {

    @Query("select d from Dish d where d.date=:date")
    List<Dish> findByDate(@Param("date")LocalDate date);

    @Query("SELECT d from Dish d WHERE d.restaurant.id=:restaurantId AND d.date=:date")
    List<Dish> findAllByRestaurantIdAndDate(@Param("restaurantId") int restaurantId,@Param("date") LocalDate date);

}
