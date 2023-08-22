package com.skosarev.restaurantvoting.service;

import com.skosarev.restaurantvoting.dto.DishDTO;
import com.skosarev.restaurantvoting.model.Dish;
import com.skosarev.restaurantvoting.model.Restaurant;
import com.skosarev.restaurantvoting.repository.DishRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class DishService {
    private final DishRepository dishRepository;
    private final RestaurantService restaurantService;
    private final ModelMapper modelMapper;

    public DishService(DishRepository dishRepository, RestaurantService restaurantService, ModelMapper modelMapper) {
        this.dishRepository = dishRepository;
        this.restaurantService = restaurantService;
        this.modelMapper = modelMapper;
    }

    public List<DishDTO> getAllActual(Integer restaurantId) {
        // возвращает список доступных (актуальных) блюд
        if (restaurantId == null) {
            return dishRepository.findAllByDateAfterOrderByDate(convertToDate(LocalDate.now()))
                    .stream().map(this::convertToDTO).toList();
        }

        Restaurant restaurant = restaurantService.get(restaurantId);
        return dishRepository.findAllByDateAfterAndRestaurantOrderByDate(convertToDate(LocalDate.now()), restaurant)
                .stream().map(this::convertToDTO).toList();
    }

    public List<DishDTO> getHistoryByRestaurant(int restaurantId) {
        return dishRepository.findAllByRestaurant(restaurantService.get(restaurantId))
                .stream().map(this::convertToDTO).toList();
    }

    @Transactional
    public List<DishDTO> saveAll(List<DishDTO> dishDTOs, int restaurantId) {
        List<Dish> dishes = dishDTOs.stream().map(this::convertToDish).toList();
        dishes.forEach(d -> d.setRestaurant(restaurantService.get(restaurantId)));
        dishRepository.saveAll(dishes);

        return dishes.stream().map(this::convertToDTO).toList();
    }

    public Dish convertToDish(DishDTO dishDTO) {
        return modelMapper.map(dishDTO, Dish.class);
    }

    public DishDTO convertToDTO(Dish dish) {
        return modelMapper.map(dish, DishDTO.class);
    }

    private Date convertToDate(LocalDate localDate) {
        return Date.from(localDate.minusDays(1).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }
}
