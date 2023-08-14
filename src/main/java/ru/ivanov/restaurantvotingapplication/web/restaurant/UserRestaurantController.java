package ru.ivanov.restaurantvotingapplication.web.restaurant;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ivanov.restaurantvotingapplication.model.Restaurant;


import java.util.List;

@RestController
@RequestMapping(value = UserRestaurantController.REST_URL,produces = MediaType.APPLICATION_JSON_VALUE)
public class UserRestaurantController extends AbstractRestaurantController {
     static final String REST_URL = "/user/restaurants";

     @GetMapping()
     public List<Restaurant> restaurantList() {
          return super.getAll();
     }

     @GetMapping("/{id}")
     public Restaurant get(@PathVariable int id){
          return super.get(id);
     }

     public void vote(int id) {
          //TODO
     }
}
