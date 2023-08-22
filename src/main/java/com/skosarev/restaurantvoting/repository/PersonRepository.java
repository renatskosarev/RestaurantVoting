package com.skosarev.restaurantvoting.repository;

import com.skosarev.restaurantvoting.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {
    Optional<Person> findByEmail(String email);

    void deleteByEmail(String email);
}
