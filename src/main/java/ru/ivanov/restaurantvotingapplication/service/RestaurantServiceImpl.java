package ru.ivanov.restaurantvotingapplication.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.time.DateUtils;
import org.hibernate.Hibernate;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.ivanov.restaurantvotingapplication.error.NotFoundException;
import ru.ivanov.restaurantvotingapplication.model.Dish;
import ru.ivanov.restaurantvotingapplication.model.Restaurant;
import ru.ivanov.restaurantvotingapplication.repository.DishRepository;
import ru.ivanov.restaurantvotingapplication.repository.RestaurantRepository;
import ru.ivanov.restaurantvotingapplication.to.DishTo;
import ru.ivanov.restaurantvotingapplication.util.DishUtil;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static ru.ivanov.restaurantvotingapplication.util.ValidationUtil.checkNotFoundWithId;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final DishRepository dishRepository;

    @Cacheable(value = "restaurants")
    @Override
    public List<Restaurant> restaurantList() {
        List<Restaurant> allRestaurants = restaurantRepository.findAll();
        allRestaurants.forEach(restaurant -> restaurant.getVoteCount(null));
        return allRestaurants;
    }

    @Override
    public Restaurant get(int id) {
        Restaurant restaurant = restaurantRepository.findById(id).orElseThrow(() -> new NotFoundException("Restaurant with id = " + id + " not found"));
        Hibernate.initialize(restaurant.getVoteCount(null));
        return restaurant;
    }

    @CacheEvict(value = "restaurants", allEntries = true)
    @Transactional
    @Override
    public Restaurant create(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    @CacheEvict(value = "restaurants", allEntries = true)
    @Transactional
    @Override
    public void update(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        checkNotFoundWithId(restaurantRepository.save(restaurant), restaurant.id());
    }

    @CacheEvict(value = "restaurants", allEntries = true)
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
                .filter(dish -> dish.getDate().isEqual(LocalDate.now()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Dish> showMenuByDate(int id, LocalDate localDate) {
        Date date = Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        return dishRepository.findAllByRestaurantId(id)
                .stream()
                .filter(dish -> dish.getDate().isEqual(localDate))
                .collect(Collectors.toList());
    }


    @Transactional
    @Override
    public void setNewMenu(List<DishTo> newMenuTo, int restaurantId) {
        List<Dish> newTodayMenu = newMenuTo.stream().map(DishUtil::getDishFromTo).toList();
        newTodayMenu.forEach(dish -> dish.setRestaurant(get(restaurantId)));
        dishRepository.saveAll(newTodayMenu);
    }
}
