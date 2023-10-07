package ru.ivanov.restaurantvotingapplication.web.restaurant;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.ivanov.restaurantvotingapplication.repository.VoteRepository;
import ru.ivanov.restaurantvotingapplication.service.DishService;
import ru.ivanov.restaurantvotingapplication.service.RestaurantService;
import ru.ivanov.restaurantvotingapplication.service.VoteService;
import ru.ivanov.restaurantvotingapplication.web.AbstractControllerTest;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.ivanov.restaurantvotingapplication.service.VoteServiceImpl.CHANGE_MIND;
import static ru.ivanov.restaurantvotingapplication.web.restaurant.RestaurantsTestData.NOT_FOUND;
import static ru.ivanov.restaurantvotingapplication.web.restaurant.RestaurantsTestData.*;
import static ru.ivanov.restaurantvotingapplication.web.restaurant.UserRestaurantController.REST_URL;
import static ru.ivanov.restaurantvotingapplication.web.user.UserTestData.*;

class UserRestaurantControllerTest extends AbstractControllerTest {
    private static final String REST_URL_SLASH = REST_URL + '/';
    private static final String VOTE = "/vote";
    @Autowired
    private RestaurantService service;
    @Autowired
    private DishService dishService;
    @Autowired
    private VoteService voteService;
    @Autowired
    private VoteRepository voteRepository;


    @Test
    void getUnAuth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(value = GUEST_MAIL)
    void getForbidden() throws Exception {
        perform(MockMvcRequestBuilders.get(AdminRestaurantController.REST_URL))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void restaurantList() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_MATCHER.contentJson(restaurants));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getWithTodayMenu() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + SECOND_RESTAURANT_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_MATCHER.contentJson(secondRestaurant));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getRestaurantNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + NOT_FOUND))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.is("Entity with id=" + NOT_FOUND + " not found")));
    }

    @Test
    @WithUserDetails(value = USER_2_MAIL)
    void vote() throws Exception {
        perform(MockMvcRequestBuilders.post(REST_URL_SLASH + FIRST_RESTAURANT_ID + VOTE))
                .andExpect(status().isCreated());
        assertFalse(voteService.todayVote(user2).isEmpty());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void voteTwice() throws Exception {
        perform(MockMvcRequestBuilders.post(REST_URL_SLASH + FIRST_RESTAURANT_ID + VOTE))
                .andExpect(status().isConflict())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.is("You have already voted.")));
    }


    @Test()
    @WithUserDetails(value = USER_MAIL)
    void updateVote() throws Exception {
        if (LocalDateTime.now().isAfter(LocalDateTime.of(LocalDate.now(), CHANGE_MIND))) {
            perform(MockMvcRequestBuilders.put(REST_URL_SLASH + SECOND_RESTAURANT_ID + VOTE))
                    .andDo(print())
                    .andExpect(status().isConflict())
                    .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.is("You can re-vote until: " + CHANGE_MIND)));
        } else {
            perform(MockMvcRequestBuilders.put(REST_URL_SLASH + THIRD_RESTAURANT_ID + VOTE))
                    .andDo(print())
                    .andExpect(status().isNoContent());
        }
    }
}