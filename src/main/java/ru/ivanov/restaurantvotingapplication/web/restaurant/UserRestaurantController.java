package ru.ivanov.restaurantvotingapplication.web.restaurant;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.ivanov.restaurantvotingapplication.model.Restaurant;
import ru.ivanov.restaurantvotingapplication.service.RestaurantService;
import ru.ivanov.restaurantvotingapplication.service.VoteService;
import ru.ivanov.restaurantvotingapplication.to.RestaurantTo;
import ru.ivanov.restaurantvotingapplication.web.AuthUser;


import java.util.List;

@RestController
@RequestMapping(value = UserRestaurantController.REST_URL,produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class UserRestaurantController extends AbstractRestaurantController {
     static final String REST_URL = "/user/restaurants";

     private final RestaurantService restaurantService;
     private final VoteService voteService;

     @GetMapping()
     public List<Restaurant> restaurantList() {
          return super.getAll();
     }

     @GetMapping("/{id}")
     public RestaurantTo getWithTodayMenu(@PathVariable int id){
          return super.getWithTodayMenu(id);
     }

     @PostMapping("/{id}")
     @ResponseStatus(HttpStatus.NO_CONTENT)
     public void vote(@PathVariable int id, @AuthenticationPrincipal AuthUser authUser) {
          voteService.vote(restaurantService.get(id),authUser.getUser());
     }
}
