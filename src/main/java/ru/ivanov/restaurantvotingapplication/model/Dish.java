package ru.ivanov.restaurantvotingapplication.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "dish")
@Getter
@Setter
@NoArgsConstructor
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

    public Dish(Integer id, String name, Integer price, Restaurant restaurant, LocalDate date) {
        super(id, name);
        this.price = price;
        this.date = date;
        this.restaurant = restaurant;
    }

}
