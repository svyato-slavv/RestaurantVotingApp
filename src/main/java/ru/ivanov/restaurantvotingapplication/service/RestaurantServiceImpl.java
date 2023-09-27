package ru.ivanov.restaurantvotingapplication.service;

import lombok.RequiredArgsConstructor;
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
        restaurant.getVoteCount(null);
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

}
