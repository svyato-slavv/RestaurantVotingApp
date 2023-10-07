package ru.ivanov.restaurantvotingapplication.web.user;

import ru.ivanov.restaurantvotingapplication.MatcherFactory;
import ru.ivanov.restaurantvotingapplication.model.Role;
import ru.ivanov.restaurantvotingapplication.model.User;
import ru.ivanov.restaurantvotingapplication.to.UserTo;
import ru.ivanov.restaurantvotingapplication.util.JsonUtil;

import java.util.Collections;
import java.util.Date;

public class UserTestData {

    public static final MatcherFactory.Matcher<User> USER_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(User.class, "registered", "password", "votes");

    public static final MatcherFactory.Matcher<UserTo> USER_TO_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(UserTo.class, "password");


    public static final int USER_ID = 1;
    public static final int ADMIN_ID = 2;
    public static final int GUEST_ID = 3;
    public static final int USER_2_ID = 4;
    public static final int NOT_FOUND = 100;

    public static final String USER_MAIL = "user@yandex.ru";
    public static final String ADMIN_MAIL = "admin@gmail.com";
    public static final String GUEST_MAIL = "guest@gmail.com";
    public static final String USER_2_MAIL = "user2@yandex.ru";

    public static final User user = new User(USER_ID, "User", USER_MAIL, "password", Role.USER);

    public static final User admin = new User(ADMIN_ID, "Admin", ADMIN_MAIL, "admin", Role.ADMIN, Role.USER);
    public static final User guest = new User(GUEST_ID, "Guest", GUEST_MAIL, "guest");
    public static final User user2 = new User(USER_2_ID, "User2", USER_2_MAIL, "password", Role.USER);



    public static User getNew() {
        return new User(null, "New", "new@gmail.com", "newPass", true, new Date(), Collections.singleton(Role.USER));
    }

    public static User getUpdated() {
        return new User(USER_ID, "UpdatedName", USER_MAIL, "newPass", true, new Date(), Collections.singleton(Role.USER));
    }

    public static String jsonWithPassword(User user, String passw) {
        return JsonUtil.writeAdditionProps(user, "password", passw);
    }

}
