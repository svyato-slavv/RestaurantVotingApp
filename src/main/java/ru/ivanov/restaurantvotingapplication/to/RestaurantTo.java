package ru.ivanov.restaurantvotingapplication.to;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;

import java.util.List;

@Value
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class RestaurantTo extends NamedTo {
    List<DishTo> menu;

    public RestaurantTo(Integer id, String name, List<DishTo> menu) {
        super(id, name);
        this.menu = menu;
    }

}
