package ru.ivanov.restaurantvotingapplication.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.ivanov.restaurantvotingapplication.model.Dish;
import ru.ivanov.restaurantvotingapplication.model.Restaurant;
import ru.ivanov.restaurantvotingapplication.repository.DishRepository;
import ru.ivanov.restaurantvotingapplication.repository.RestaurantRepository;
import ru.ivanov.restaurantvotingapplication.to.DishTo;
import ru.ivanov.restaurantvotingapplication.to.RestaurantTo;
import ru.ivanov.restaurantvotingapplication.util.DishUtil;
import ru.ivanov.restaurantvotingapplication.util.RestaurantUtil;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static ru.ivanov.restaurantvotingapplication.util.ValidationUtil.checkNotFoundWithId;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final DishRepository dishRepository;

    @Override
    public List<Restaurant> restaurantList() {
        List<Restaurant> all = restaurantRepository.findAll();
        all.forEach(Restaurant::getVoteCount);
        return all;
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
        Assert.notNull(restaurant, "restaurant must not be null");
        checkNotFoundWithId(restaurantRepository.save(restaurant), restaurant.id());
    }

    @Transactional
    @Override
    public void delete(int id) {
        restaurantRepository.deleteById(id);
    }
    @Transactional
    @Override
    public void deleteOldTodayMenu(int restaurantId) {
        List<Dish> oldTodayMenu = showTodayMenu(restaurantId);
        dishRepository.deleteAll(oldTodayMenu);
    }

    @Override
    public List<Dish> showTodayMenu(int id) {
        return dishRepository.findAllByRestaurantId(id)
                .stream()
                .filter(dish -> DateUtils.isSameDay(dish.getDate(), new Date()))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void newMenu(List<DishTo> newMenuTo, int restaurantId) {
        List<Dish> newTodayMenu = newMenuTo.stream().map(DishUtil::getDishFromTo).toList();
        newTodayMenu.forEach(dish -> dish.setRestaurant(get(restaurantId)));
        dishRepository.saveAll(newTodayMenu);


    }
}
