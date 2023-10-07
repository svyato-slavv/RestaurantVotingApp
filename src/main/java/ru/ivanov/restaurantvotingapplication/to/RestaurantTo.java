package ru.ivanov.restaurantvotingapplication.to;


import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class RestaurantTo extends NamedTo {
    List<DishTo> menu;
    Integer voteCount;

    public RestaurantTo(Integer id, String name, List<DishTo> menu, Integer voteCount) {
        super(id, name);
        this.menu = menu;
        this.voteCount = voteCount;
    }

}
