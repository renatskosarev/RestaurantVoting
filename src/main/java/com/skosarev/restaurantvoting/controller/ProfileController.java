package com.skosarev.restaurantvoting.controller;

import com.skosarev.restaurantvoting.dto.PersonDTO;
import com.skosarev.restaurantvoting.service.PersonService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {
    private final PersonService personService;

    @Autowired
    public ProfileController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    public ResponseEntity<PersonDTO> get(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(personService.getDTOByEmail(userDetails.getUsername()));
    }

    @PatchMapping
    public ResponseEntity<PersonDTO> update(@RequestBody @Valid PersonDTO personDTO,
                                            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(personService.update(personService.getByEmail(userDetails.getUsername()).getId(), personDTO));
    }

    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> delete(@AuthenticationPrincipal UserDetails userDetails) {
        personService.deleteByEmail(userDetails.getUsername());
        return ResponseEntity.status(204).body(String.format("Person with email=%s deleted", userDetails.getUsername()));
    }
}
