INSERT INTO USERS (name, email, password)
VALUES ('User', 'user@yandex.ru', '{noop}password'),
       ('Admin', 'admin@gmail.com', '{noop}admin'),
       ('Guest', 'guest@gmail.com', '{noop}guest'),
       ('User2', 'user2@yandex.ru', '{noop}password');


INSERT INTO USER_ROLE (role, user_id)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 2),
       ('USER',4);

INSERT INTO RESTAURANT (name)
VALUES ('Евразия'),
       ('Токио-Сити'),
       ('Бахрома'),
       ('Ель');

INSERT INTO VOTE (restaurant_id, user_id)
VALUES (1, 1),
       (1, 2);
INSERT INTO DISH (name, price, restaurant_id)
VALUES ('Завтрак1', 100, 1),
       ('Обед1', 150, 1),
       ('Завтрак2', 200, 2),
       ('Обед2', 100, 2),
       ('Завтрак3', 100, 3),
       ('Обед3', 100, 3),
       ('Завтрак4', 100, 4),
       ('Обед4', 100, 4);
