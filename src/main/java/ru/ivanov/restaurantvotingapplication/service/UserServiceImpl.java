package ru.ivanov.restaurantvotingapplication.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.ivanov.restaurantvotingapplication.error.NotFoundException;
import ru.ivanov.restaurantvotingapplication.model.Role;
import ru.ivanov.restaurantvotingapplication.model.User;
import ru.ivanov.restaurantvotingapplication.repository.UserRepository;
import ru.ivanov.restaurantvotingapplication.to.UserTo;
import ru.ivanov.restaurantvotingapplication.util.UsersUtil;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


import static ru.ivanov.restaurantvotingapplication.config.SecurityConfig.PASSWORD_ENCODER;
import static ru.ivanov.restaurantvotingapplication.util.ValidationUtil.checkNotFoundWithId;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    @Override
    public List<UserTo> getAll() {
        return repository.findAll(Sort.by(Sort.Direction.ASC, "name", "email"))
                .stream()
                .map(UsersUtil::getToWithRoles)
                .collect(Collectors.toList());
    }


    @Override
    public User get(int id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with id = " + id + " not found"));
    }

    @Transactional
    @Override
    public User create(User user) {
        Assert.notNull(user, "user must not be null");
        return repository.save(user);
    }

    @Transactional
    @Override
    public void update(User user) {
        Assert.notNull(user, "user must not be null");
        checkNotFoundWithId(repository.save(user), user.id());
    }

    @Transactional
    @Override
    public User prepareAndSave(User user) {
        user.setPassword(PASSWORD_ENCODER.encode(user.getPassword()));
        user.setEmail(user.getEmail().toLowerCase());
        user.setRoles(Collections.singletonList(Role.USER));
        return repository.save(user);
    }

    @Transactional
    @Override
    public void delete(int id) {
        User userById = repository.findById(id).orElseThrow();
        repository.delete(userById);
    }
}
