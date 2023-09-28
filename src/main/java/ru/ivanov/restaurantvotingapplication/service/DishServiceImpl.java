package ru.ivanov.restaurantvotingapplication.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.ivanov.restaurantvotingapplication.model.Dish;
import ru.ivanov.restaurantvotingapplication.model.Restaurant;
import ru.ivanov.restaurantvotingapplication.repository.DishRepository;
import ru.ivanov.restaurantvotingapplication.repository.RestaurantRepository;
import ru.ivanov.restaurantvotingapplication.to.DishTo;
import ru.ivanov.restaurantvotingapplication.util.DishUtil;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DishServiceImpl implements DishService {

    private final DishRepository dishRepository;
    private final RestaurantRepository restaurantRepository;


    @Cacheable(value = "dishes")
    @Override
    public List<Dish> getByDate(LocalDate date) {
        return dishRepository.findByDate(date);
    }


    @Override
    public Dish get(int id) {
        return dishRepository.getExisted(id);
    }


    @CacheEvict(value = "dishes", allEntries = true)
    @Override
    @Transactional
    public Dish create(DishTo dishTo) {
        Restaurant restaurant = restaurantRepository.getExisted(dishTo.getRestaurantId());
        return dishRepository.save(DishUtil.createNewFromTo(dishTo, restaurant));
    }

    @CacheEvict(value = "dishes", allEntries = true)
    @Override
    public void delete(int id) {
        dishRepository.deleteExisted(id);
    }

    @CacheEvict(value = "dishes", allEntries = true)
    @Override
    public void update(DishTo dishTo) {
        Assert.notNull(dishTo, "dish must not be null");
        dishRepository.update(dishTo.getName(), dishTo.getPrice(), dishTo.getRestaurantId(), dishTo.getId());
    }

    @Override
    public List<Dish> getToday(int restaurantId) {
        return dishRepository.findAllByRestaurantIdAndDate(restaurantId, LocalDate.now());
    }

    @Override
    public List<Dish> getByDateAndRestaurant(int restaurantId, LocalDate date) {
        return dishRepository.findAllByRestaurantIdAndDate(restaurantId, date);
    }

}
