package ru.ivanov.restaurantvotingapplication.web.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.ivanov.restaurantvotingapplication.model.User;
import ru.ivanov.restaurantvotingapplication.service.UserService;
import ru.ivanov.restaurantvotingapplication.to.UserTo;
import ru.ivanov.restaurantvotingapplication.util.UsersUtil;

import java.net.URI;
import java.util.List;

import static ru.ivanov.restaurantvotingapplication.util.ValidationUtil.assureIdConsistent;
import static ru.ivanov.restaurantvotingapplication.util.ValidationUtil.checkNew;

@Slf4j
@RestController
@RequestMapping(value = AdminUserController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class AdminUserController {
    static final String REST_URL = "/api/admin/users";

    private final UserService service;

    private final UniqueMailValidator emailValidator;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(emailValidator);
    }


    @GetMapping()
    public List<User> getAll() {
        log.info("get all users with roles");
        return service.getAll();
    }

    @GetMapping("/{id}")
    public User get(@PathVariable int id) {
        log.info("get user with id= {}", id);
        return service.get(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> createWithLocation(@Valid @RequestBody User user) {
        log.info("create user= {}", user);
        checkNew(user);
        User created = service.prepareAndSave(user);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody UserTo userTo, @PathVariable int id) {
        log.info("update user with id= {} from userTo= {}", id, userTo);
        assureIdConsistent(userTo, id);
        User user = service.get(id);
        service.prepareAndSave(UsersUtil.updateFromTo(user, userTo));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete user with id= {}", id);
        service.delete(id);
    }


}
