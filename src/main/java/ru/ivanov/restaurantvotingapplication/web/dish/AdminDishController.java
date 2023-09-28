package ru.ivanov.restaurantvotingapplication.web.dish;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.ivanov.restaurantvotingapplication.model.Dish;
import ru.ivanov.restaurantvotingapplication.service.DishService;
import ru.ivanov.restaurantvotingapplication.to.DishTo;
import ru.ivanov.restaurantvotingapplication.util.DishUtil;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static ru.ivanov.restaurantvotingapplication.util.ValidationUtil.assureIdConsistent;
import static ru.ivanov.restaurantvotingapplication.util.ValidationUtil.checkNew;

@Slf4j
@RestController
@RequestMapping(value = AdminDishController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class AdminDishController {
    static final String REST_URL = "/api/admin/dishes";
    private final DishService service;


    @GetMapping()
    public List<DishTo> getAllByDate(@RequestParam LocalDate localDate) {
        log.info("get dishes by date = {}", localDate);
        return service.getByDate(localDate)
                .stream()
                .map(DishUtil::getTo)
                .toList();
    }

    @GetMapping("/{id}")
    public DishTo get(@PathVariable int id) {
        log.info("get dish with id= {}", id);
        return DishUtil.getTo(service.get(id));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Dish> createWithLocation(@RequestBody DishTo dishTo) {
        log.info("create new dish from dishTo= {}", dishTo);
        checkNew(dishTo);
        Dish created = service.create(dishTo);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody DishTo dishTo, @PathVariable int id) {
        log.info("update dish with id= {} from dishTo= {}", id, dishTo);
        assureIdConsistent(dishTo, id);
        service.update(dishTo);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete dish with id= {}", id);
        service.delete(id);
    }
}
