package ru.ivanov.restaurantvotingapplication.web.dish;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.ivanov.restaurantvotingapplication.model.Dish;
import ru.ivanov.restaurantvotingapplication.repository.DishRepository;
import ru.ivanov.restaurantvotingapplication.service.DishService;
import ru.ivanov.restaurantvotingapplication.to.DishTo;
import ru.ivanov.restaurantvotingapplication.util.DishUtil;

import java.net.URI;
import java.util.List;


import static ru.ivanov.restaurantvotingapplication.util.ValidationUtil.assureIdConsistent;
import static ru.ivanov.restaurantvotingapplication.util.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = AdminDishController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class AdminDishController {
    static final String REST_URL = "/admin/dishes";
    private final DishService service;
    private final DishRepository repository;

    @GetMapping()
    public List<DishTo> getAllWithRestaurant() {
        return service.getSorted()
                .stream()
                .map(DishUtil::getToWithRestaurant)
                .toList();
    }

    @GetMapping("/{id}")
    public DishTo get(@PathVariable int id) {
        return DishUtil.getToWithRestaurant(service.get(id));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Dish> createWithLocation(@RequestBody DishTo dishTo) {
        checkNew(dishTo);
        Dish created = service.create(DishUtil.createNewFromTo(dishTo));
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody DishTo dishTo, @PathVariable int id) {
        Dish dish = repository.findById(id).orElseThrow();
        assureIdConsistent(dishTo, id);
        service.update(dish, dishTo);
    }


    @DeleteMapping("/{id}")//удаление еды
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        service.delete(id);
    }
}
