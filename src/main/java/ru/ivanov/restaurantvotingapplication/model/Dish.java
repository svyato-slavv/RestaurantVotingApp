package ru.ivanov.restaurantvotingapplication.model;

import com.fasterxml.jackson.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "dish")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Dish extends NamedEntity {
    @Column(name = "price", nullable = false)
    private int price;


    @Column(name = "date", nullable = false, columnDefinition = "date default now()", updatable = false)
    @Temporal(TemporalType.DATE)
    @NotNull
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @JsonManagedReference
    private Restaurant restaurant;

    public Dish(Integer id, String name, Integer price) {
        super(id, name);
        this.price = price;
    }

    public Dish(Integer id, String name, Integer price, LocalDate date) {
        super(id, name);
        this.price = price;
        this.date = date;
    }

    public Dish(Integer id, String name, Integer price, Restaurant restaurant,LocalDate date) {
        super(id, name);
        this.price = price;
        this.date = date;
        this.restaurant = restaurant;
    }

}
