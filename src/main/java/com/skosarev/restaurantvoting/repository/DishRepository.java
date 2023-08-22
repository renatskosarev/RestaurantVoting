package com.skosarev.restaurantvoting.repository;

import com.skosarev.restaurantvoting.model.Dish;
import com.skosarev.restaurantvoting.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface DishRepository extends JpaRepository<Dish, Integer> {
    List<Dish> findAllByDateAfterOrderByDate(Date date);

    List<Dish> findAllByDateAfterAndRestaurantOrderByDate(Date date, Restaurant restaurant);

    List<Dish> findAllByRestaurant(Restaurant restaurant);
}
