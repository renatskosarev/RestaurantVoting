package com.skosarev.restaurantvoting.controller;

import com.skosarev.restaurantvoting.dto.PersonDTO;
import com.skosarev.restaurantvoting.dto.VoteDTO;
import com.skosarev.restaurantvoting.service.PersonService;
import com.skosarev.restaurantvoting.service.VoteService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {
    private final PersonService personService;
    private final VoteService voteService;
    private final Logger logger = LoggerFactory.getLogger(ProfileController.class);

    @Autowired
    public ProfileController(PersonService personService, VoteService voteService) {
        this.personService = personService;
        this.voteService = voteService;
    }

    @GetMapping
    public ResponseEntity<PersonDTO> get(@AuthenticationPrincipal UserDetails userDetails) {
        logger.info("Get: {}", userDetails.getUsername());
        return ResponseEntity.ok(personService.getDTOByEmail(userDetails.getUsername()));
    }

    @GetMapping("/votes")
    public ResponseEntity<List<VoteDTO>> getVotes(@AuthenticationPrincipal UserDetails userDetails) {
        logger.info("Get votes: {}", userDetails.getUsername());
        return ResponseEntity.ok(voteService.getAllByPersonEmail(userDetails.getUsername()));
    }

    @PatchMapping
    public ResponseEntity<PersonDTO> update(@RequestBody @Valid PersonDTO personDTO,
                                            @AuthenticationPrincipal UserDetails userDetails) {
        logger.info("Update: {}", userDetails.getUsername());
        return ResponseEntity.ok(personService.update(personService.getByEmail(userDetails.getUsername()).getId(), personDTO));
    }

    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> delete(@AuthenticationPrincipal UserDetails userDetails) {
        logger.info("Delete: {}", userDetails.getUsername());
        personService.deleteByEmail(userDetails.getUsername());
        return ResponseEntity.status(204).body(String.format("Person with email=%s deleted", userDetails.getUsername()));
    }
}
