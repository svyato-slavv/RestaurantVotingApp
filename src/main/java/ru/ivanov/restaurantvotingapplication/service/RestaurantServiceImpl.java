package ru.ivanov.restaurantvotingapplication.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ivanov.restaurantvotingapplication.model.Dish;
import ru.ivanov.restaurantvotingapplication.model.Restaurant;
import ru.ivanov.restaurantvotingapplication.repository.DishRepository;
import ru.ivanov.restaurantvotingapplication.repository.RestaurantRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static ru.ivanov.restaurantvotingapplication.util.ValidationUtil.checkNotFoundWithId;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final DishRepository dishRepository;

    @Override
    public List<Restaurant> restaurantList() {

        return restaurantRepository.findAll();
    }

    @Override
    public Restaurant get(int id) {
        return restaurantRepository.findById(id).orElseThrow();
    }

    @Transactional
    @Override
    public Restaurant create(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    @Transactional
    @Override
    public void update(Restaurant restaurant) {
        restaurantRepository.save(restaurant);
    }

    @Transactional
    @Override
    public void delete(int id) {
        restaurantRepository.deleteById(id);
    }

    @Override
    public List<Dish> showRestaurantMenu(int id) {
        List<Dish> menuOfDay = new ArrayList<>();
        Date dateNow = new Date();
        for (Dish dish : dishRepository.findAllByRestaurantId(id)) {
            if (DateUtils.isSameDay(dish.getDate(), dateNow)) {
                menuOfDay.add(dish);
            }
        }
        return menuOfDay;
    }
}
