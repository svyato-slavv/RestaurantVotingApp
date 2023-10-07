package ru.ivanov.restaurantvotingapplication.to;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
public class DishTo extends BaseTo {
    @NotBlank
    @Size(min = 2, max = 128)
    String name;
    LocalDate date;
    @NotNull
    Integer price;
    Integer restaurantId;


    public DishTo(Integer id, String name, LocalDate date, Integer price, Integer restaurantId) {
        super(id);
        this.name = name;
        this.date = date;
        this.price = price;
        this.restaurantId = restaurantId;
    }

}
