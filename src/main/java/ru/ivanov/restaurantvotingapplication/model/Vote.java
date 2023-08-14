package ru.ivanov.restaurantvotingapplication.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "vote")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Vote extends BaseEntity {
    @Column(name = "vote_date", nullable = false, columnDefinition = "timestamp default now()")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "Europe/Moscow")
    private Date voteDate = new Date();
    @ManyToOne
    @NotNull
    @JoinColumn(name = "restaurant_id")
    @JsonBackReference
    private Restaurant restaurant;
    @ManyToOne
    @NotNull
    @JoinColumn(name = "user_id")
    private User user;


}