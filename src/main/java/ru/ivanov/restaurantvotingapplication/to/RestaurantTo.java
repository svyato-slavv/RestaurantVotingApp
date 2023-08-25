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
    Integer voteCount;


    public RestaurantTo(Integer id, String name, List<DishTo> menu, Integer voteCount) {
        super(id, name);
        this.menu = menu;
        this.voteCount = voteCount;
    }

}
