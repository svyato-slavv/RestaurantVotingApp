package ru.ivanov.restaurantvotingapplication;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.ivanov.restaurantvotingapplication.service.RestaurantService;

@SpringBootApplication
@RequiredArgsConstructor
public class RestaurantVotingApplication {
    public static void main(String[] args) {
        SpringApplication.run(RestaurantVotingApplication.class, args);
    }

}
