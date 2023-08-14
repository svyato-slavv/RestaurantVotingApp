package ru.ivanov.restaurantvotingapplication.web.user;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ivanov.restaurantvotingapplication.web.dish.AdminDishController;

@RestController
@RequestMapping(value = AdminUserController.REST_URL,produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminUserController {
    static final String REST_URL="/admin/users";
}
