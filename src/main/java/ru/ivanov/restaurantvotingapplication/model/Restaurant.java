package ru.ivanov.restaurantvotingapplication.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "restaurant")
@Getter
@Setter
@NoArgsConstructor
public class Restaurant extends NamedEntity {
    @Transient
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private int voteCount;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference
    @JsonIgnore
    private List<Dish> dishes;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference
    @JsonIgnore
    private List<Vote> votes = new ArrayList<>();

    public Restaurant(List<Dish> dishes) {
        this.dishes = dishes;
    }

    public Restaurant(Integer id, String name, List<Dish> dishes) {
        super(id, name);
        this.dishes = dishes;
    }


    public int getVoteCount(LocalDate inputDate) {
        LocalDate date = inputDate == null ? LocalDate.now() : inputDate;
        voteCount = (int) votes.stream().filter(vote -> vote.getVoteDateTime().toLocalDate().isEqual(date)).count();
        return voteCount;
    }
}
