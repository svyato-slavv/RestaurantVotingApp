package ru.ivanov.restaurantvotingapplication.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.ivanov.restaurantvotingapplication.model.Restaurant;
import ru.ivanov.restaurantvotingapplication.repository.RestaurantRepository;

import java.util.List;


@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;

    @Cacheable(value = "restaurants")
    @Override
    @Transactional
    public List<Restaurant> getAll() {
        List<Restaurant> allRestaurants = restaurantRepository.findAll();
        allRestaurants.forEach(restaurant -> restaurant.getVoteCount(null));
        return allRestaurants;
    }

    @Override
    @Transactional
    public Restaurant get(int id) {
        Restaurant restaurant = restaurantRepository.getExisted(id);
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
    @Override
    public void update(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        restaurantRepository.update(restaurant.getName(), restaurant.id());
    }

    @CacheEvict(value = "restaurants", allEntries = true)
    @Override
    public void delete(int id) {
        restaurantRepository.deleteExisted(id);
    }

}
