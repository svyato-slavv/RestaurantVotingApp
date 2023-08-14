package ru.ivanov.restaurantvotingapplication.web.restaurant;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import ru.ivanov.restaurantvotingapplication.model.Dish;
import ru.ivanov.restaurantvotingapplication.model.Restaurant;
import ru.ivanov.restaurantvotingapplication.service.DishService;
import ru.ivanov.restaurantvotingapplication.service.RestaurantService;

import java.util.List;

import static ru.ivanov.restaurantvotingapplication.util.ValidationUtil.assureIdConsistent;
import static ru.ivanov.restaurantvotingapplication.util.ValidationUtil.checkNew;


public abstract class AbstractRestaurantController {
    @Autowired
    private RestaurantService service;

    public List<Restaurant> getAll() {
        return service.restaurantList();
    }

    public Restaurant get(int id) {
        return service.get(id);
    }

    public Restaurant create(Restaurant restaurant) {
        checkNew(restaurant);
        return service.create(restaurant);
    }

    public void delete(int id) {
        service.delete(id);
    }

    public void update(Restaurant restaurant, int id) {
        assureIdConsistent(restaurant, id);
        service.update(restaurant);
    }

    public List<Dish> menu(int id) {
       return service.showRestaurantMenu(id);
    }
}
