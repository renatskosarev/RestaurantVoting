package com.skosarev.restaurantvoting.service;

import com.skosarev.restaurantvoting.dto.PersonDTO;
import com.skosarev.restaurantvoting.model.Person;
import com.skosarev.restaurantvoting.model.Role;
import com.skosarev.restaurantvoting.repository.PersonRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional(readOnly = true)
public class PersonService {
    private final PersonRepository personRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public PersonService(PersonRepository personRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.personRepository = personRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public List<PersonDTO> getAll() {
        return personRepository.findAll()
                .stream().map(this::convertToDTO).toList();
    }

    public Person getByEmail(String email) {
        return personRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        String.format("User with email=%s not exists", email)));
    }

    public int getIdByEmail(String email) {
        return getByEmail(email).getId();
    }

    public PersonDTO getDTOByEmail(String email) {
        return convertToDTO(getByEmail(email));
    }

    @Transactional
    public PersonDTO signup(PersonDTO personDTO) {
        Person person = convertToPerson(personDTO);

        if (personRepository.findByEmail(person.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User with this email is already exists");
        }

        person.setRoles(new HashSet<>(Set.of(Role.USER)));
        if (personRepository.count() == 0) {
            person.getRoles().add(Role.ADMIN);
        }
        person.setPassword(passwordEncoder.encode(person.getPassword()));

        personRepository.save(person);

        return convertToDTO(person);
    }

    @Transactional
    public PersonDTO update(int id, PersonDTO updatedPersonDTO) {
        Person updatedPerson = convertToPerson(updatedPersonDTO);
        Person person = personRepository.findById(id).get();

        person.setName(updatedPerson.getName());
        person.setEmail(updatedPerson.getEmail());
        person.setPassword(passwordEncoder.encode(updatedPerson.getPassword()));

        return convertToDTO(person);
    }

    @Transactional
    public void deleteByEmail(String email) {
        personRepository.deleteByEmail(email);
    }

    public Person convertToPerson(PersonDTO personDTO) {
        return modelMapper.map(personDTO, Person.class);
    }

    public PersonDTO convertToDTO(Person person) {
        return modelMapper.map(person, PersonDTO.class);
    }

}
