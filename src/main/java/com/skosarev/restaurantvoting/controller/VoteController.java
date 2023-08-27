package com.skosarev.restaurantvoting.controller;

import com.skosarev.restaurantvoting.dto.VoteDTO;
import com.skosarev.restaurantvoting.service.PersonService;
import com.skosarev.restaurantvoting.service.VoteService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/votes")
public class VoteController {
    private final VoteService voteService;
    private final PersonService personService;
    private final Logger logger = LoggerFactory.getLogger(VoteController.class);

    @Autowired
    public VoteController(VoteService voteService, PersonService personService) {
        this.voteService = voteService;
        this.personService = personService;
    }

    @GetMapping
    public ResponseEntity<List<VoteDTO>> getAll(@RequestParam("restaurantId") Optional<Integer> restaurantId) {
        logger.info("Get all by restaurant: {}", restaurantId.orElse(null));
        return ResponseEntity.ok(voteService.getAll(restaurantId));
    }

    @PostMapping
    public ResponseEntity<VoteDTO> vote(@RequestBody @Valid VoteDTO voteDTO,
                                        @AuthenticationPrincipal UserDetails details) {
        logger.info("Person {} voted for {}", details.getUsername(), voteDTO.getRestaurantId());
        voteDTO.setPersonId(personService.getIdByEmail(details.getUsername()));
        return ResponseEntity.ok(voteService.create(voteDTO));
    }

    @PatchMapping
    public ResponseEntity<VoteDTO> changeVote(@RequestBody @Valid VoteDTO voteDTO,
                                        @AuthenticationPrincipal UserDetails details) {
        logger.info("Person {} changed vote to {}", details.getUsername(), voteDTO.getRestaurantId());
        voteDTO.setPersonId(personService.getIdByEmail(details.getUsername()));
        return ResponseEntity.ok(voteService.update(voteDTO, voteDTO.getRestaurantId()));
    }
}
