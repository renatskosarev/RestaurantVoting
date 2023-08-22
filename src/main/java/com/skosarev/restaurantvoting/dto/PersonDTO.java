package com.skosarev.restaurantvoting.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.skosarev.restaurantvoting.model.Role;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class PersonDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer id;

    @Size(min = 2, message = "Name should contain at least two characters")
    @NotBlank(message = "Name should not be blank")
    private String name;

    @NotNull(message = "Email should not be empty")
    @Email(message = "Email should be correct")
    private String email;

    @NotNull(message = "Password should not be empty")
    @Size(min = 4, message = "Password should be at least 4 characters long")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // read - в json; write - из json в объект
    private String password;

    private Set<Role> roles;
}
