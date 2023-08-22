DROP TABLE IF EXISTS vote;
DROP TABLE IF EXISTS dish;
DROP TABLE IF EXISTS restaurant;
DROP TABLE IF EXISTS person_role;
DROP TABLE IF EXISTS person;

CREATE TABLE person
(
    id       INT PRIMARY KEY AUTO_INCREMENT,
    name     VARCHAR NOT NULL,
    email    VARCHAR NOT NULL,
    password VARCHAR NOT NULL
);

CREATE TABLE person_role
(
    person_id INT REFERENCES person (id) ON DELETE CASCADE,
    role      VARCHAR,
    CONSTRAINT person_role_idx UNIQUE (person_id, role)
);

CREATE TABLE restaurant
(
    id   INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR NOT NULL
);

CREATE TABLE dish
(
    id            INT PRIMARY KEY AUTO_INCREMENT,
    restaurant_id INT REFERENCES restaurant (id) ON DELETE CASCADE,
    name          VARCHAR NOT NULL,
    price         INT     NOT NULL,
    date          DATE    NOT NULL
);

CREATE TABLE vote
(
    id            INT PRIMARY KEY AUTO_INCREMENT,
    date          DATE NOT NULL,
    person_id     INT REFERENCES person (id) ON DELETE CASCADE,
    restaurant_id INT REFERENCES restaurant (id) ON DELETE CASCADE,
    CONSTRAINT date_person_idx UNIQUE (date, person_id)
)
