package com.skosarev.restaurantvoting.controller;

import com.skosarev.restaurantvoting.dto.VoteDTO;
import com.skosarev.restaurantvoting.model.Person;
import com.skosarev.restaurantvoting.service.PersonService;
import com.skosarev.restaurantvoting.service.RestaurantService;
import com.skosarev.restaurantvoting.service.VoteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/votes")
public class VoteController {
    private final VoteService voteService;
    private final PersonService personService;
    private final RestaurantService restaurantService;

    @Autowired
    public VoteController(VoteService voteService, PersonService personService, RestaurantService restaurantService) {
        this.voteService = voteService;
        this.personService = personService;
        this.restaurantService = restaurantService;
    }

    @GetMapping
    public ResponseEntity<List<VoteDTO>> getAll(@AuthenticationPrincipal UserDetails details) {
        Person person = personService.getByEmail(details.getUsername());
        return ResponseEntity.ok(voteService.getAll(person));
    }

    @PostMapping
    public ResponseEntity<VoteDTO> vote(@RequestBody @Valid VoteDTO voteDTO,
                                        @AuthenticationPrincipal UserDetails details) {
        voteDTO.setPersonId(personService.getIdByEmail(details.getUsername()));
        return ResponseEntity.ok(voteService.create(voteDTO));
    }

    @PatchMapping
    public ResponseEntity<VoteDTO> changeVote(@RequestBody @Valid VoteDTO voteDTO,
                                        @AuthenticationPrincipal UserDetails details) {
        voteDTO.setPersonId(personService.getIdByEmail(details.getUsername()));

        VoteDTO temp = voteService.update(voteDTO, restaurantService.get(voteDTO.getRestaurantId()));
        return ResponseEntity.ok(temp);
    }
}
