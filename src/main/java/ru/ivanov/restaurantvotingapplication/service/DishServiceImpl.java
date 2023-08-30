package ru.ivanov.restaurantvotingapplication.service;

import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Cache;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.ivanov.restaurantvotingapplication.error.NotFoundException;
import ru.ivanov.restaurantvotingapplication.model.Dish;
import ru.ivanov.restaurantvotingapplication.model.Restaurant;
import ru.ivanov.restaurantvotingapplication.repository.DishRepository;
import ru.ivanov.restaurantvotingapplication.to.DishTo;
import ru.ivanov.restaurantvotingapplication.util.DishUtil;

import java.util.List;

import static ru.ivanov.restaurantvotingapplication.util.ValidationUtil.checkNotFoundWithId;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DishServiceImpl implements DishService {

    private final DishRepository dishRepository;


    @Cacheable(value = "dishes")
    @Override
    public List<Dish> getSorted() {
        return dishRepository.findSortedList();
    }


    @Override
    public Dish get(int id) {
        Dish dish = dishRepository.findById(id).orElseThrow(() -> new NotFoundException("Dish with id = " + id + " not found"));
        Hibernate.initialize(dish.getRestaurant().getVoteCount(null));
        return dish;
    }


    @CacheEvict(value = "dishes", allEntries = true)
    @Override
    @Transactional
    public Dish create(Dish dish) {
        return dishRepository.save(dish);
    }

    @CacheEvict(value = "dishes", allEntries = true)
    @Override
    @Transactional
    public void delete(int id) {
        dishRepository.deleteById(id);
    }

    @CacheEvict(value = "dishes", allEntries = true)
    @Override
    @Transactional
    public void update(Dish dish, DishTo dishTo, Restaurant restaurant) {
        Assert.notNull(dishTo, "dish must not be null");
        checkNotFoundWithId(dishRepository.save(DishUtil.updateFromTo(dish, dishTo, restaurant)), dishTo.id());
    }

}
