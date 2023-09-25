package ru.ivanov.restaurantvotingapplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.ivanov.restaurantvotingapplication.model.Dish;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface DishRepository extends JpaRepository<Dish, Integer> {

    List<Dish> findAllByRestaurantId(int id);

    @Query("select d from Dish d where d.date=:date")
    List<Dish> findByDate(@Param("date")LocalDate date);

    @Query("select d from Dish d join fetch d.restaurant where d.id=:id")
    Optional<Dish> findOneWithJoinFetch(@Param("id") int id);


}
