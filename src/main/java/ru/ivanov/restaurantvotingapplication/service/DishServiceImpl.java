package ru.ivanov.restaurantvotingapplication.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.ivanov.restaurantvotingapplication.model.Dish;
import ru.ivanov.restaurantvotingapplication.repository.DishRepository;
import ru.ivanov.restaurantvotingapplication.repository.RestaurantRepository;

import java.util.List;

import static ru.ivanov.restaurantvotingapplication.util.ValidationUtil.checkNotFoundWithId;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DishServiceImpl implements DishService {

    private final DishRepository dishRepository;
    private final RestaurantRepository restaurantRepository;

    @Override
    public Dish get(int id) {
        return dishRepository.findWithJoinFetch(id).orElseThrow();
    }


    @Override
    @Transactional
    public Dish create(Dish dish) {
//        dish.setDate(new Date());
        return dishRepository.save(dish);
    }

    @Override
    @Transactional
    public void delete(int id) {
        dishRepository.deleteById(id);
    }

    @Override
    public List<Dish> getAll() {
        return dishRepository.findAll();
    }

    @Override
    @Transactional
    public void update(Dish dish,int restaurantId) {
        Assert.notNull(dish, "dish must not be null");
        dish.setRestaurant(restaurantRepository.findById(restaurantId).orElseThrow());
        checkNotFoundWithId(dishRepository.save(dish), dish.id());
    }

}
