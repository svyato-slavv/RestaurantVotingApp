package ru.ivanov.restaurantvotingapplication.web.restaurant;

import org.springframework.beans.factory.annotation.Autowired;
import ru.ivanov.restaurantvotingapplication.model.Dish;
import ru.ivanov.restaurantvotingapplication.model.Restaurant;
import ru.ivanov.restaurantvotingapplication.service.RestaurantService;
import ru.ivanov.restaurantvotingapplication.to.RestaurantTo;
import ru.ivanov.restaurantvotingapplication.util.RestaurantUtil;

import java.util.List;

import static ru.ivanov.restaurantvotingapplication.util.ValidationUtil.assureIdConsistent;
import static ru.ivanov.restaurantvotingapplication.util.ValidationUtil.checkNew;


public abstract class AbstractRestaurantController {
    @Autowired
    private RestaurantService service;

    public List<Restaurant> getAll() {
        return service.restaurantList();
    }

    public RestaurantTo getWithTodayMenu(int id) {
        return RestaurantUtil.getTos(service.get(id), service.showTodayMenu(id));
    }

    public Restaurant create(Restaurant restaurant) {
        checkNew(restaurant);
        return service.create(restaurant);
    }

    public void delete(int id) {
        service.delete(id);
    }

    public void updateRestaurant(Restaurant restaurant) {
        service.update(restaurant);
    }

    public List<Dish> todayMenu(int id) {
        return service.showTodayMenu(id);
    }

}
