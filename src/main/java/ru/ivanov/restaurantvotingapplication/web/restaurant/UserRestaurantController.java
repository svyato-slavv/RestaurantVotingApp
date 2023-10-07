package ru.ivanov.restaurantvotingapplication.web.restaurant;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.ivanov.restaurantvotingapplication.model.Restaurant;
import ru.ivanov.restaurantvotingapplication.model.Vote;
import ru.ivanov.restaurantvotingapplication.service.VoteService;
import ru.ivanov.restaurantvotingapplication.to.RestaurantTo;
import ru.ivanov.restaurantvotingapplication.web.AuthUser;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = UserRestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class UserRestaurantController extends AbstractRestaurantController {
    static final String REST_URL = "/api/user/restaurants";
    private final VoteService voteService;


    @GetMapping()
    public List<Restaurant> restaurantList() {
        log.info("get list of restaurants with votes");
        return super.getAll();
    }

    @GetMapping("/{id}")
    public RestaurantTo getWithTodayMenu(@PathVariable int id) {
        log.info("get restaurant with today menu with id= {}", id);
        return super.getWithTodayMenu(id);
    }

    @PostMapping(value = "/{id}/vote")
    public ResponseEntity<Vote> createVoteWithLocation(@PathVariable("id") int restaurantId, @AuthenticationPrincipal AuthUser authUser) {
        Vote created = voteService.vote(restaurantId, authUser.getUser());
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/user/votes" + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping("/{id}/vote")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateVote(@PathVariable("id") int restaurantId, @AuthenticationPrincipal AuthUser authUser) {
        log.info("user with id= {} is changing vote for restaurant wit id= {}", authUser.id(), restaurantId);
        voteService.update(restaurantId, authUser.getUser());
    }
}
