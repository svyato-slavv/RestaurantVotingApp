package ru.ivanov.restaurantvotingapplication.to;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;

import java.util.List;

@Value
public class RestaurantTo {
    Integer id;
    String name;
    List<DishTo> menu;


}
