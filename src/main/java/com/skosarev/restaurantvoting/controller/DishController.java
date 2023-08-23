package com.skosarev.restaurantvoting.controller;

import com.skosarev.restaurantvoting.dto.DishDTO;
import com.skosarev.restaurantvoting.service.DishService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/dishes")
public class DishController {
    private final DishService dishService;

    @Autowired
    public DishController(DishService dishService) {
        this.dishService = dishService;
    }

    @GetMapping
    public ResponseEntity<List<DishDTO>> getAll(@RequestParam(value = "restaurantId") Optional<Integer> restaurantId) {
        return ResponseEntity.ok(dishService.getAllActual(restaurantId));
    }

    @GetMapping("/history")
    public ResponseEntity<List<DishDTO>> getHistory(@RequestParam(value = "restaurantId") Optional<Integer> restaurantId) {
        return ResponseEntity.ok(dishService.getHistory(restaurantId));
    }

    @PostMapping
    public ResponseEntity<List<DishDTO>> create(@RequestBody List<@Valid DishDTO> dishDTOS,
                                                @RequestParam("restaurantId") int restaurantId) {
        return ResponseEntity.ok(dishService.saveAll(dishDTOS, restaurantId));
    }
}
