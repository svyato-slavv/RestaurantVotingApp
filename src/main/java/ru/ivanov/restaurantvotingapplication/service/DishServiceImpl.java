package ru.ivanov.restaurantvotingapplication.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.ivanov.restaurantvotingapplication.error.NotFoundException;
import ru.ivanov.restaurantvotingapplication.model.Dish;
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



    @Override
    public List<Dish> getSorted() {
        return dishRepository.findSortedList();
    }

    @Override
    public Dish get(int id) {
        return dishRepository.findOneWithJoinFetch(id).orElseThrow(()->new NotFoundException("Dish with id = "+id+" not found"));
    }


    @Override
    @Transactional
    public Dish create(Dish dish) {
        return dishRepository.save(dish);
    }

    @Override
    @Transactional
    public void delete(int id) {
        dishRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void update(Dish dish, DishTo dishTo) {
        Assert.notNull(dishTo, "dish must not be null");
        checkNotFoundWithId(dishRepository.save(DishUtil.updateFromTo(dish, dishTo)), dishTo.id());
    }

}
