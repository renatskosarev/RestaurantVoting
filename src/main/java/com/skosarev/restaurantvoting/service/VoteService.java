package com.skosarev.restaurantvoting.service;

import com.skosarev.restaurantvoting.dto.VoteDTO;
import com.skosarev.restaurantvoting.model.Person;
import com.skosarev.restaurantvoting.model.Restaurant;
import com.skosarev.restaurantvoting.model.Vote;
import com.skosarev.restaurantvoting.repository.VoteRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class VoteService {
    private final VoteRepository voteRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public VoteService(VoteRepository voteRepository, ModelMapper modelMapper) {
        this.voteRepository = voteRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public VoteDTO create(VoteDTO voteDTO) {
        Vote vote = convertToVote(voteDTO);
        vote.setDate(new Date());

        // check if user already voted
        if (voteRepository.findVoteByPersonAndDate(vote.getPerson(), vote.getDate()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Person has already voted");
        }
        checkTimeIsBefore11am();

        return convertToDTO(voteRepository.save(vote));
    }

    @Transactional
    public VoteDTO update(VoteDTO updatedVoteDTO, Restaurant restaurant) {
        Vote updatedVote = convertToVote(updatedVoteDTO);

        Vote voteToBeUpdated = voteRepository.findVoteByPersonAndDate(updatedVote.getPerson(), new Date()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Person hasn't voted yet")
        );
        checkTimeIsBefore11am();


        voteToBeUpdated.setRestaurant(restaurant);
        return convertToDTO(voteToBeUpdated);
    }

    public List<VoteDTO> getAll(Person person) {
        return voteRepository.findAllByPerson(person)
                .stream().map(this::convertToDTO).toList();
    }

    public Vote convertToVote(VoteDTO voteDTO) {
        return modelMapper.map(voteDTO, Vote.class);
    }

    public VoteDTO convertToDTO(Vote vote) {
        return modelMapper.map(vote, VoteDTO.class);
    }

    private void checkTimeIsBefore11am() {
        if (LocalTime.now().isAfter(LocalTime.of(11, 0))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can't change vote after 11am");
        }
    }
}
