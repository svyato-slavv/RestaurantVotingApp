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

    @GetMapping() // получаем список всех блюд с ресторанами, сортированные по id ресторана по возрастанию
    public List<Dish> getDishes() {
        return repository.findAllSortedByRestaurantId();
    }

    @GetMapping("/{id}") //получаем конкретное блюдо с рестораном
    public DishTo get(@PathVariable int id) {
        return DishUtil.getTosWithRestaurant(service.get(id));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)//создаем еду
    public ResponseEntity<Dish> createWithLocation(@RequestBody DishTo dishTo) {
        checkNew(dishTo);
        Dish created = service.create(DishUtil.createNewFromTo(dishTo));
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)//изменяем еду или назначаем ей ресторан
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody Dish dish, @PathVariable int id) {
        assureIdConsistent(dish, id);
        service.update(dish,id);
    }


    @DeleteMapping("/{id}")//удаление еды
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        service.delete(id);
    }
}
