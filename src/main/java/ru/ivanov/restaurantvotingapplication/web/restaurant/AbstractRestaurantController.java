package ru.ivanov.restaurantvotingapplication.web.restaurant;

import org.springframework.beans.factory.annotation.Autowired;
import ru.ivanov.restaurantvotingapplication.model.Dish;
import ru.ivanov.restaurantvotingapplication.model.Restaurant;
import ru.ivanov.restaurantvotingapplication.service.DishService;
import ru.ivanov.restaurantvotingapplication.service.RestaurantService;
import ru.ivanov.restaurantvotingapplication.to.RestaurantTo;
import ru.ivanov.restaurantvotingapplication.util.RestaurantUtil;

import java.util.List;

import static ru.ivanov.restaurantvotingapplication.util.ValidationUtil.checkNew;


public abstract class AbstractRestaurantController {
    @Autowired
    private RestaurantService restaurantService;
    @Autowired
    private DishService dishService;


    public List<Restaurant> getAll() {
        return restaurantService.getAll();
    }

    public Restaurant findOne(Integer id) {
        return restaurantService.get(id);
    }

    public RestaurantTo getWithTodayMenu(int id) {
        Restaurant restaurant = restaurantService.get(id);
        return RestaurantUtil.getTo(restaurant, dishService.getToday(id), restaurant.getVoteCount(null));
    }

    public Restaurant create(Restaurant restaurant) {
        checkNew(restaurant);
        return restaurantService.create(restaurant);
    }

    public void delete(int id) {
        restaurantService.delete(id);
    }

    public void updateRestaurant(Restaurant restaurant) {
        restaurantService.update(restaurant);
    }

    public List<Dish> todayMenu(int id) {
        return dishService.getToday(id);
    }

}
