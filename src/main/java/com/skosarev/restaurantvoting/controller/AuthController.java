package com.skosarev.restaurantvoting.controller;

import com.skosarev.restaurantvoting.dto.PersonDTO;
import com.skosarev.restaurantvoting.service.PersonService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final PersonService personService;

    @Autowired
    public AuthController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping("/signup")
    public ResponseEntity<PersonDTO> signup(@Valid @RequestBody PersonDTO personDTO) {
        return ResponseEntity.ok(personService.signup(personDTO));
    }
}
