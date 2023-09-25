package ru.ivanov.restaurantvotingapplication.web.restaurant;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.ivanov.restaurantvotingapplication.model.Restaurant;
import ru.ivanov.restaurantvotingapplication.service.DishService;
import ru.ivanov.restaurantvotingapplication.service.RestaurantService;
import ru.ivanov.restaurantvotingapplication.to.DishTo;
import ru.ivanov.restaurantvotingapplication.util.DishUtil;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static ru.ivanov.restaurantvotingapplication.util.ValidationUtil.assureIdConsistent;
import static ru.ivanov.restaurantvotingapplication.util.ValidationUtil.checkNew;

@Slf4j
@RestController
@RequestMapping(value = AdminRestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class AdminRestaurantController extends AbstractRestaurantController {
    static final String REST_URL = "/api/admin/restaurants";
    private final DishService dishService;


    @GetMapping()
    public List<Restaurant> restaurantList() {
        log.info("get all restaurants");
        return super.getAll();
    }

    @GetMapping("/{id}")
    public Restaurant getRestaurant(@PathVariable int id) {
        log.info("get restaurant with today menu with id={}", id);
        return super.findOne(id);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody Restaurant restaurant, @PathVariable int id) {
        log.info("update restaurant {} with id={}", restaurant, id);
        assureIdConsistent(restaurant, id);
        super.updateRestaurant(restaurant);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> createWithLocation(@RequestBody Restaurant restaurant) {
        log.info("create restaurant {}", restaurant);
        checkNew(restaurant);
        Restaurant created = super.create(restaurant);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete restaurant with id={}", id);
        super.delete(id);
    }

    @GetMapping("/{id}/dishes")
    public List<DishTo> MenuOfRestaurantByDate(@RequestParam @Nullable LocalDate date,
                                               @PathVariable int id) {
        log.info("get restaurant with menu by date={} with id={}", date, id);
        if (date == null) {
            return super.todayMenu(id).stream().map(DishUtil::getTo).toList();
        }
        return dishService.getByDateAndRestaurant(id,date).stream().map(DishUtil::getTo).toList();
    }
}
