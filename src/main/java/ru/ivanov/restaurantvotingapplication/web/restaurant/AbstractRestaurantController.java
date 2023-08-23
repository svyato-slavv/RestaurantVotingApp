package ru.ivanov.restaurantvotingapplication.web.restaurant;

import org.springframework.beans.factory.annotation.Autowired;
import ru.ivanov.restaurantvotingapplication.model.Dish;
import ru.ivanov.restaurantvotingapplication.model.Restaurant;
import ru.ivanov.restaurantvotingapplication.service.RestaurantService;
import ru.ivanov.restaurantvotingapplication.to.RestaurantTo;
import ru.ivanov.restaurantvotingapplication.util.RestaurantUtil;

import java.util.List;

import static ru.ivanov.restaurantvotingapplication.util.ValidationUtil.checkNew;


public abstract class AbstractRestaurantController {
    @Autowired
    private RestaurantService service;

    public List<Restaurant> getAll() {
        return service.restaurantList();
    }//список ресторанов

    public RestaurantTo getWithTodayMenu(int id) {
        return RestaurantUtil.getTos(service.get(id), service.showTodayMenu(id));
    }//ресторан с сегодняшним меню

    public Restaurant create(Restaurant restaurant) {
        checkNew(restaurant);
        return service.create(restaurant);
    }//создать ресторан

    public void delete(int id) {
        service.delete(id);
    }//удалить ресторан

    public void updateRestaurant(Restaurant restaurant) {
        service.update(restaurant);
    }//редактировать ресторан

    public List<Dish> todayMenu(int id) {
        return service.showTodayMenu(id);
    }//показать только сегодняшнее меню ресторана

}
