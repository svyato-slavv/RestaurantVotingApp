package ru.ivanov.restaurantvotingapplication.to;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class VoteTo extends BaseTo {
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime voteDateTime;
    @NotNull
    Integer restaurantId;

    public VoteTo(Integer id, Integer restaurantId, LocalDateTime voteDateTime) {
        super(id);
        this.restaurantId = restaurantId;
        this.voteDateTime = voteDateTime;
    }
}
