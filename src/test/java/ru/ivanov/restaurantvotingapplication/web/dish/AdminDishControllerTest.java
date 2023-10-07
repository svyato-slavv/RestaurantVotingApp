package ru.ivanov.restaurantvotingapplication.web.dish;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.ivanov.restaurantvotingapplication.error.NotFoundException;
import ru.ivanov.restaurantvotingapplication.repository.DishRepository;
import ru.ivanov.restaurantvotingapplication.service.DishService;
import ru.ivanov.restaurantvotingapplication.to.DishTo;
import ru.ivanov.restaurantvotingapplication.util.DishUtil;
import ru.ivanov.restaurantvotingapplication.util.JsonUtil;
import ru.ivanov.restaurantvotingapplication.web.AbstractControllerTest;

import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.ivanov.restaurantvotingapplication.web.dish.AdminDishController.REST_URL;
import static ru.ivanov.restaurantvotingapplication.web.dish.DishTestData.*;
import static ru.ivanov.restaurantvotingapplication.web.user.UserTestData.ADMIN_MAIL;
import static ru.ivanov.restaurantvotingapplication.web.user.UserTestData.USER_MAIL;

class AdminDishControllerTest extends AbstractControllerTest {

    private static final String REST_URL_SLASH = REST_URL + '/';

    @Autowired
    private DishService service;
    @Autowired
    private DishRepository repository;

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
    void getAllByDate() throws Exception {
        LocalDate today = LocalDate.now();
        perform(MockMvcRequestBuilders.get("/api/admin/dishes?localDate=" + today))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DISH_TO_MATCHER.contentJson(dishes.stream().map(DishUtil::getTo).toList()));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + FIRST_DISH_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DISH_TO_MATCHER.contentJson(DishUtil.getTo(firstDish)));

    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getDishNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + NOT_FOUND))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.is("Entity with id=" + NOT_FOUND + " not found")));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void createWithLocation() throws Exception {
        DishTo newDish = DishTestData.getNew();
        perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newDish)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(9))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Новая еда"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(500));
        assertThat(repository.findAll(), Matchers.hasSize(9));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void update() throws Exception {
        DishTo newDish = DishTestData.getUpdated();
        newDish.setId(null);
        perform(MockMvcRequestBuilders.put(REST_URL_SLASH + FIRST_DISH_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newDish)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertThat(service.get(1).getName(), Matchers.is("Завтрак1 обновленный"));
        assertThat(service.get(1).getPrice(), Matchers.is(800));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL_SLASH + FOURTH_DISH_ID))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class,
                () -> service.get(FOURTH_DISH_ID));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void deleteNotFound() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL_SLASH + NOT_FOUND))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.is("Entity with id=" + NOT_FOUND + " not found")));
    }
}