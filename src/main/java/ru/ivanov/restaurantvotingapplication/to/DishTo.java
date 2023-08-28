package ru.ivanov.restaurantvotingapplication.to;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import ru.ivanov.restaurantvotingapplication.model.Restaurant;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
public class DishTo extends BaseTo {
    @NotBlank
    @Size(min = 2, max = 128)
    String name;
    Date date;
    @NotNull
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
