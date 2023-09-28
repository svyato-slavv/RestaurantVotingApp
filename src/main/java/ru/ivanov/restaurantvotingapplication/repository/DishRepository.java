package ru.ivanov.restaurantvotingapplication.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.ivanov.restaurantvotingapplication.model.Dish;

import java.time.LocalDate;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface DishRepository extends BaseRepository<Dish> {

    @Modifying
    @Transactional
    @Query("UPDATE Dish d SET d.name=:name,d.price=:price, d.restaurant.id = :restaurantId WHERE d.id = :id")
    void update(String name, Integer price, Integer restaurantId, Integer id);

    @Query("select d from Dish d where d.date=:date")
    List<Dish> findByDate(LocalDate date);

    @Query("SELECT d from Dish d WHERE d.restaurant.id=:restaurantId AND d.date=:date")
    List<Dish> findAllByRestaurantIdAndDate(Integer restaurantId, LocalDate date);

}
