package ru.ivanov.restaurantvotingapplication.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.time.DateUtils;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "restaurant")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Restaurant extends NamedEntity {
    @Transient
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Integer voteCount;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference
    @JsonIgnore
    private List<Dish> menu;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference
    @JsonIgnore
    private List<Vote> votes = new java.util.ArrayList<>();

    public Restaurant(List<Dish> menu) {
        this.menu = menu;
    }

    public Restaurant(Integer id, String name, List<Dish> menu) {
        super(id, name);
        this.menu = menu;
    }


    public Integer getVoteCount(Date inputDate) {
        voteCount = 0;
        Date date;
        if (inputDate == null) {
            date = new Date();
        } else date = inputDate;

        for (Vote vote : votes) {
            if (DateUtils.isSameDay(vote.getVoteDate(), date)) {
                voteCount++;
            }
        }
        return voteCount;
    }
}
