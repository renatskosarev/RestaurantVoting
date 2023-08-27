package com.skosarev.restaurantvoting.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class DishDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private int id;

    @NotEmpty(message = "Dish name should be not empty")
    @Size(min = 2, message = "Name should contain at least two characters")
    private String name;

    @NotNull(message = "Dish price should be at least 0 cents")
    @Min(0)
    private int price;

    @NotNull(message = "Date should be not null")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date date;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private int restaurantId;
}
