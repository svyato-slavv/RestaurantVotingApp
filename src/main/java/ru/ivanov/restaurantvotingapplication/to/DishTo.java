package ru.ivanov.restaurantvotingapplication.to;

import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.*;
import ru.ivanov.restaurantvotingapplication.model.Restaurant;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
public class DishTo extends BaseTo {

    String name;
    Date date;

    Integer price;
    Restaurant restaurant;

    public DishTo() {

    }

    public DishTo(String name, Integer price) {
        this.name = name;
        this.price = price;
    }

    public DishTo(String name, Integer price, Restaurant restaurant) {
        this.name = name;
        this.price = price;
        this.restaurant = restaurant;
    }
}
