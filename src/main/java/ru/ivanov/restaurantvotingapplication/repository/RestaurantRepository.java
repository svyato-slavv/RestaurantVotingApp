package ru.ivanov.restaurantvotingapplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ivanov.restaurantvotingapplication.model.Restaurant;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant,Integer> {
}
