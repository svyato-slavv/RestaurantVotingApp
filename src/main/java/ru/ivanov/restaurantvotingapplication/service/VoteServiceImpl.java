package ru.ivanov.restaurantvotingapplication.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ivanov.restaurantvotingapplication.error.VoteException;
import ru.ivanov.restaurantvotingapplication.model.Restaurant;
import ru.ivanov.restaurantvotingapplication.model.User;
import ru.ivanov.restaurantvotingapplication.model.Vote;
import ru.ivanov.restaurantvotingapplication.repository.VoteRepository;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VoteServiceImpl implements VoteService {
    private static final LocalTime CHANGE_MIND = LocalTime.of(11, 0);
    private final VoteRepository repository;

    @Transactional
    @Override
    @CacheEvict(value = "restaurants", allEntries = true)
    public void vote(Restaurant restaurant, User user) {
        Optional<Vote> voteOfDay = repository.findAllByUser_Id(Objects.requireNonNull(user.getId()))
                .stream()
                .filter(vote -> DateUtils.isSameDay(vote.getVoteDate(), new Date())).findAny();
        if (voteOfDay.isPresent()) {
            Instant instant = CHANGE_MIND.atDate(LocalDate.now()).
                    atZone(ZoneId.systemDefault()).toInstant();
            Date changeMind = Date.from(instant);

            if (voteOfDay.get().getVoteDate().before(changeMind)) {
                repository.updateVote(restaurant.getId(), new Date(), voteOfDay.get().getId());
            } else throw new VoteException("You have already voted. You can re-vote until: " + CHANGE_MIND);
        } else {
            repository.save(new Vote(new Date(), restaurant, user));
        }
    }

}
