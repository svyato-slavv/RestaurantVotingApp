package ru.ivanov.restaurantvotingapplication.web.restaurant;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.ivanov.restaurantvotingapplication.error.NotFoundException;
import ru.ivanov.restaurantvotingapplication.model.Restaurant;
import ru.ivanov.restaurantvotingapplication.service.DishService;
import ru.ivanov.restaurantvotingapplication.service.RestaurantService;
import ru.ivanov.restaurantvotingapplication.util.DishUtil;
import ru.ivanov.restaurantvotingapplication.util.JsonUtil;
import ru.ivanov.restaurantvotingapplication.web.AbstractControllerTest;
import ru.ivanov.restaurantvotingapplication.web.dish.DishTestData;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.ivanov.restaurantvotingapplication.web.dish.DishTestData.*;
import static ru.ivanov.restaurantvotingapplication.web.restaurant.AdminRestaurantController.REST_URL;
import static ru.ivanov.restaurantvotingapplication.web.restaurant.RestaurantsTestData.NOT_FOUND;
import static ru.ivanov.restaurantvotingapplication.web.restaurant.RestaurantsTestData.*;
import static ru.ivanov.restaurantvotingapplication.web.user.UserTestData.ADMIN_MAIL;
import static ru.ivanov.restaurantvotingapplication.web.user.UserTestData.USER_MAIL;


class AdminRestaurantControllerTest extends AbstractControllerTest {

    private static final String REST_URL_SLASH = REST_URL + '/';

    private static final String MENU = "/dishes";

    private static final String REQUEST_PARAM_DATE = "?date=";
    @Autowired
    private RestaurantService service;
    @Autowired
    private DishService dishService;


    @Test
    void getUnAuth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getForbidden() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void restaurantList() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_MATCHER.contentJson(restaurants));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getRestaurant() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + FIRST_RESTAURANT_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_MATCHER.contentJson(firstRestaurant));
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
    @WithUserDetails(value = ADMIN_MAIL)
    void update() throws Exception {
        Restaurant restaurant = RestaurantsTestData.getUpdated();
        restaurant.setId(null);
        perform(MockMvcRequestBuilders.put(REST_URL_SLASH + SECOND_RESTAURANT_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(restaurant)))
                .andDo(print())
                .andExpect(status().isNoContent());

        RESTAURANT_MATCHER.assertMatch(service.get(SECOND_RESTAURANT_ID), RestaurantsTestData.getUpdated());
    }


    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void createWithLocation() throws Exception {
        Restaurant newRestaurant = RestaurantsTestData.getNew();
        ResultActions action = perform(post("/api/admin/restaurants")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newRestaurant)))
                .andDo(print())
                .andExpect(status().isCreated());
        Restaurant created = RESTAURANT_MATCHER.readFromJson(action);
        int newId = created.id();
        newRestaurant.setId(newId);
        RESTAURANT_MATCHER.assertMatch(created, newRestaurant);
        RESTAURANT_MATCHER.assertMatch(service.get(newId), newRestaurant);

    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL_SLASH + FOURTH_RESTAURANT_ID))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class,
                () -> service.get(FOURTH_RESTAURANT_ID));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void deleteNotFound() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL_SLASH + NOT_FOUND))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.is("Entity with id=" + DishTestData.NOT_FOUND + " not found")));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void menuOfRestaurantByDateWithoutRequestParam() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + FIRST_RESTAURANT_ID + MENU))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
                .andExpect(DISH_TO_MATCHER.contentJson(DishUtil.getTo(firstDish), DishUtil.getTo(secondDish)));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void menuOfRestaurantByDateToday() throws Exception {
        LocalDate today = LocalDate.now();
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + SECOND_RESTAURANT_ID + MENU + REQUEST_PARAM_DATE + today))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
                .andExpect(DISH_TO_MATCHER.contentJson(DishUtil.getTo(thirdDish), DishUtil.getTo(fourthDish)));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void menuOfRestaurantByDateYesterday() throws Exception {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + SECOND_RESTAURANT_ID + MENU + REQUEST_PARAM_DATE + yesterday))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(0)));
    }
}