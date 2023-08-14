package ru.ivanov.restaurantvotingapplication.service;

import jakarta.persistence.Table;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.ivanov.restaurantvotingapplication.model.Dish;
import ru.ivanov.restaurantvotingapplication.repository.DishRepository;
import ru.ivanov.restaurantvotingapplication.util.ValidationUtil;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static ru.ivanov.restaurantvotingapplication.util.ValidationUtil.assureIdConsistent;
import static ru.ivanov.restaurantvotingapplication.util.ValidationUtil.checkNotFoundWithId;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DishServiceImpl implements DishService {

    private final DishRepository repository;

    @Override
    public Optional<Dish> get(int id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public Dish create(Dish dish) {
        dish.setDate(new Date());
        return repository.save(dish);
    }

    @Override
    @Transactional
    public void delete(int id) {
        repository.deleteById(id);
    }

    @Override
    public List<Dish> getAll() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public void update(Dish dish) {
        Assert.notNull(dish, "user must not be null");
        checkNotFoundWithId(repository.save(dish), dish.id());
    }
}
