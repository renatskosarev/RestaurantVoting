package com.skosarev.restaurantvoting.controller;

import com.skosarev.restaurantvoting.dto.RestaurantDTO;
import com.skosarev.restaurantvoting.service.RestaurantService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/api/restaurants")
public class RestaurantController {
    private final RestaurantService restaurantService;

    @Autowired
    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @PostMapping
    public ResponseEntity<RestaurantDTO> create(@RequestBody @Valid RestaurantDTO restaurantDTO) {
        return ResponseEntity.ok(restaurantService.create(restaurantDTO));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<RestaurantDTO> update(@PathVariable int id,
                                                @RequestBody @Valid RestaurantDTO restaurantDTO) {
        return ResponseEntity.ok(restaurantService.update(id, restaurantDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        restaurantService.delete(id);
        return ResponseEntity.status(204).body(String.format("Restaurant with id=%d deleted", id));
    }

    @GetMapping
    public ResponseEntity<List<RestaurantDTO>> getAll() {
        return ResponseEntity.ok(restaurantService.getAll());
    }
}
