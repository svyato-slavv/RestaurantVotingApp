package ru.ivanov.restaurantvotingapplication;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import ru.ivanov.restaurantvotingapplication.service.RestaurantService;

@SpringBootApplication
@EnableCaching
public class RestaurantVotingApplication {
    public static void main(String[] args) {
        SpringApplication.run(RestaurantVotingApplication.class, args);
    }

}
