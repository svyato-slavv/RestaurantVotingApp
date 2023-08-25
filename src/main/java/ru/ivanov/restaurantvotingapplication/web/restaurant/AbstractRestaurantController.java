package ru.ivanov.restaurantvotingapplication.web.restaurant;

import org.springframework.beans.factory.annotation.Autowired;
import ru.ivanov.restaurantvotingapplication.model.Dish;
import ru.ivanov.restaurantvotingapplication.model.Restaurant;
import ru.ivanov.restaurantvotingapplication.service.RestaurantService;
import ru.ivanov.restaurantvotingapplication.to.RestaurantTo;
import ru.ivanov.restaurantvotingapplication.util.RestaurantUtil;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import static ru.ivanov.restaurantvotingapplication.util.ValidationUtil.checkNew;


public abstract class AbstractRestaurantController {
    @Autowired
    private RestaurantService service;


    public List<Restaurant> getAll() {
        return service.restaurantList();
    }

    public RestaurantTo getWithTodayMenu(int id) {
        Restaurant restaurant=service.get(id);
        return RestaurantUtil.getTo(restaurant, service.showTodayMenu(id), restaurant.getVoteCount(null));
    }

    public RestaurantTo getWithMenuByDate(int id, LocalDate localDate){
        Restaurant restaurant=service.get(id);
        Date date= Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        return RestaurantUtil.getTo(restaurant,service.showMenuByDate(id, localDate), restaurant.getVoteCount(date));
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
