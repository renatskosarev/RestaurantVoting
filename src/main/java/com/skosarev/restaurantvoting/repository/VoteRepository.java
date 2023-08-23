package com.skosarev.restaurantvoting.repository;

import com.skosarev.restaurantvoting.model.Person;
import com.skosarev.restaurantvoting.model.Restaurant;
import com.skosarev.restaurantvoting.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Integer> {
    Optional<Vote> findVoteByPersonAndDate(Person person, Date date);

    List<Vote> findAllByRestaurant(Restaurant restaurant);

    List<Vote> findAllByPersonEmail(String email);
}
