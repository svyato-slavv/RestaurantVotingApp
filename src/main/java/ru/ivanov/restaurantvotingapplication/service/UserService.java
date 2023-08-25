package ru.ivanov.restaurantvotingapplication.service;

import ru.ivanov.restaurantvotingapplication.model.User;
import ru.ivanov.restaurantvotingapplication.to.UserTo;

import java.util.List;

public interface UserService {
    User get(int id);

    User create(User user);

    void delete(int id);

    List<UserTo> getAll();

    void update(User user);
    User prepareAndSave(User user);

}
