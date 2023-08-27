package com.skosarev.restaurantvoting.controller;

import com.skosarev.restaurantvoting.dto.DishDTO;
import com.skosarev.restaurantvoting.service.DishService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Validated
@RestController
@RequestMapping("/api/dishes")
public class DishController {
    private final DishService dishService;
    private final Logger logger = LoggerFactory.getLogger(DishController.class);

    @Autowired
    public DishController(DishService dishService) {
        this.dishService = dishService;
    }

    @GetMapping
    public ResponseEntity<List<DishDTO>> getAll(@RequestParam(value = "restaurantId") Optional<Integer> restaurantId) {
        logger.info("Get all dishes by restaurant: {}", restaurantId.orElse(null));
        return ResponseEntity.ok(dishService.getAllActual(restaurantId));
    }

    @GetMapping("/history")
    public ResponseEntity<List<DishDTO>> getHistory(@RequestParam(value = "restaurantId") Optional<Integer> restaurantId) {
        logger.info("Get dishes history by restaurant: {}", restaurantId.orElse(null));
        return ResponseEntity.ok(dishService.getHistory(restaurantId));
    }

    @PostMapping
    public ResponseEntity<List<DishDTO>> create(@RequestBody List<@Valid DishDTO> dishDTOS,
                                                @RequestParam("restaurantId") int restaurantId) {
        logger.info("Create dishes for restaurant: {}", restaurantId);
        return ResponseEntity.ok(dishService.saveAll(dishDTOS, restaurantId));
    }
}
