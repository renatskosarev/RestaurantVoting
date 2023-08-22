package com.skosarev.restaurantvoting.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "person")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Min(1)
    @Column(name = "id")
    private Integer id;

    @NotBlank(message = "Name should not be blank")
    @Size(min = 2, message = "Name should contain at least two characters")
    @Column(name = "name")
    private String name;

    @NotNull(message = "Email should not be empty")
    @Email(message = "Email should be correct")
    @Column(name = "email")
    private String email;

    @NotNull(message = "Password should not be empty")
    @Size(min = 4, message = "Password should be at least 4 characters long")
    private String password;

    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "person_role", joinColumns = @JoinColumn(name = "person_id"))
    @Column(name = "role")
    private Set<Role> roles;

    @OneToMany(mappedBy = "person")
    private List<Vote> votes;
}
