package ru.ivanov.restaurantvotingapplication.service;

import ru.ivanov.restaurantvotingapplication.model.User;

import java.util.List;

public interface UserService {
    User get(int id);

    User create(User user);

    void delete(int id);

    List<User> getAll();

    void update(User user);

}
