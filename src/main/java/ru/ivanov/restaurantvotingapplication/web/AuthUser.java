package ru.ivanov.restaurantvotingapplication.web;

import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import ru.ivanov.restaurantvotingapplication.model.User;

@Getter
public class AuthUser extends org.springframework.security.core.userdetails.User {

    private final User user;

    public AuthUser(@NonNull User user) {
        super(user.getEmail(), user.getPassword(), user.getRoles());
        this.user = user;
    }

    public int id() {
        return user.id();
    }
}
