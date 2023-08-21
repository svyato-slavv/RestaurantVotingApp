package ru.ivanov.restaurantvotingapplication.to;

import lombok.Data;
import ru.ivanov.restaurantvotingapplication.model.Restaurant;

@Data
public class DishTo {
    Integer id;
    String name;
    Integer price;
    Restaurant restaurant;

    public DishTo( String name, Integer price) {
        this.name = name;
        this.price = price;
    }

    public DishTo(String name, Integer price, Restaurant restaurant) {
        this.name = name;
        this.price = price;
        this.restaurant = restaurant;
    }
}
