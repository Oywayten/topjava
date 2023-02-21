DELETE FROM user_role;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin'),
       ('Guest', 'guest@gmail.com', 'guest');

INSERT INTO user_role (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);


INSERT INTO meals (datetime, description, calories, user_id)
VALUES ('2020-04-04 20:00:00', 'apple', 150, 100000),
       ('2020-01-01 01:00:00', 'cheese', 300, 100000),
       ('2023-02-21 23:21:15', 'cookies', 500, 100001);