package com.skosarev.restaurantvoting.service;

import com.skosarev.restaurantvoting.dto.RestaurantDTO;
import com.skosarev.restaurantvoting.model.Restaurant;
import com.skosarev.restaurantvoting.repository.RestaurantRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public RestaurantService(RestaurantRepository restaurantRepository, ModelMapper modelMapper) {
        this.restaurantRepository = restaurantRepository;
        this.modelMapper = modelMapper;
    }

    public Restaurant get(int id) {
        return restaurantRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        String.format("Restaurant with id=%d not exists", id))
        );
    }

    public List<RestaurantDTO> getAll() {
        return restaurantRepository.findAll()
                .stream().map(this::convertToDTO).toList();
    }

    @Transactional
    public RestaurantDTO create(RestaurantDTO restaurantDTO) {
        return convertToDTO(restaurantRepository.save(convertToRestaurant(restaurantDTO)));
    }

    @Transactional
    public RestaurantDTO update(int id, RestaurantDTO updatedRestaurantDTO) {
        Restaurant updatedRestaurant = convertToRestaurant(updatedRestaurantDTO);

        Restaurant restaurant = restaurantRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Restaurant with id=%d wasn't found", id)));

        restaurant.setName(updatedRestaurant.getName());

        return convertToDTO(restaurant);
    }

    @Transactional
    public void delete(int id) {
        Restaurant restaurant = restaurantRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Restaurant with id=%d wasn't found", id)));

        restaurantRepository.delete(restaurant);
    }

    public Restaurant convertToRestaurant(RestaurantDTO restaurantDTO) {
        return modelMapper.map(restaurantDTO, Restaurant.class);
    }

    public RestaurantDTO convertToDTO(Restaurant restaurant) {
        return modelMapper.map(restaurant, RestaurantDTO.class);
    }
}
