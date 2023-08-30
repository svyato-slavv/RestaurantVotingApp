package ru.ivanov.restaurantvotingapplication.to;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.criteria.CriteriaBuilder;
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
    @Schema(hidden = true)
    Date date;
    @NotNull
    Integer price;
    @Schema(accessMode = Schema.AccessMode.WRITE_ONLY)
    Integer restaurantId;


    public DishTo() {

    }

    public DishTo(Integer id, String name, Integer price) {
        super(id);
        this.name = name;
        this.price = price;
    }

}
