package com.skosarev.restaurantvoting.controller;

import com.skosarev.restaurantvoting.dto.RestaurantDTO;
import com.skosarev.restaurantvoting.service.RestaurantService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/api/restaurants")
public class RestaurantController {
    private final RestaurantService restaurantService;
    private final Logger logger = LoggerFactory.getLogger(RestaurantController.class);

    @Autowired
    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @PostMapping
    public ResponseEntity<RestaurantDTO> create(@RequestBody @Valid RestaurantDTO restaurantDTO) {
        logger.info("Create: {}", restaurantDTO.getName());
        return ResponseEntity.ok(restaurantService.create(restaurantDTO));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<RestaurantDTO> update(@PathVariable int id,
                                                @RequestBody @Valid RestaurantDTO restaurantDTO) {
        logger.info("Update: {}", restaurantDTO.getId());
        return ResponseEntity.ok(restaurantService.update(id, restaurantDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        logger.info("Delete: {}", id);
        restaurantService.delete(id);
        return ResponseEntity.status(204).body(String.format("Restaurant with id=%d deleted", id));
    }

    @GetMapping
    public ResponseEntity<List<RestaurantDTO>> getAll() {
        logger.info("Get all");
        return ResponseEntity.ok(restaurantService.getAll());
    }
}
