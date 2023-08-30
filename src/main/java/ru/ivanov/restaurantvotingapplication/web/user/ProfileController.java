package ru.ivanov.restaurantvotingapplication.web.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.ivanov.restaurantvotingapplication.model.User;
import ru.ivanov.restaurantvotingapplication.service.UserService;
import ru.ivanov.restaurantvotingapplication.to.UserTo;
import ru.ivanov.restaurantvotingapplication.util.UsersUtil;
import ru.ivanov.restaurantvotingapplication.web.AuthUser;

import java.net.URI;

import static ru.ivanov.restaurantvotingapplication.util.ValidationUtil.assureIdConsistent;
import static ru.ivanov.restaurantvotingapplication.util.ValidationUtil.checkNew;

@Slf4j
@RestController
@RequestMapping(value = ProfileController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ProfileController {
    static final String REST_URL = "/api/profile";
    private final UserService service;

    private final UniqueMailValidator emailValidator;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(emailValidator);
    }

    @GetMapping()
    public UserTo get(@AuthenticationPrincipal AuthUser authUser) {
        log.info("get user with id= {}", authUser.id());
        return UsersUtil.getTo(authUser.getUser());
    }

    @DeleteMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@AuthenticationPrincipal AuthUser authUser) {
        log.info("delete user with id= {}", authUser.id());
        service.delete(authUser.id());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<User> register(@Valid @RequestBody UserTo userTo) {
        log.info("user registration from to= {}", userTo);
        checkNew(userTo);
        User created = service.prepareAndSave(UsersUtil.createNewFromTo(userTo));
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL).build().toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody UserTo userTo, @AuthenticationPrincipal AuthUser authUser) {
        log.info("update user with id= {} from userTo= {}", authUser.id(), userTo);
        assureIdConsistent(userTo, authUser.id());
        User user = authUser.getUser();
        service.prepareAndSave(UsersUtil.updateFromTo(user, userTo));
    }
}
