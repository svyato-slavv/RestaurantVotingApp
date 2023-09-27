package ru.ivanov.restaurantvotingapplication.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ivanov.restaurantvotingapplication.error.VoteException;
import ru.ivanov.restaurantvotingapplication.model.Restaurant;
import ru.ivanov.restaurantvotingapplication.model.User;
import ru.ivanov.restaurantvotingapplication.model.Vote;
import ru.ivanov.restaurantvotingapplication.repository.VoteRepository;

import java.time.*;
import java.util.Objects;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VoteServiceImpl implements VoteService {
    private static final LocalTime CHANGE_MIND = LocalTime.of(16, 57);
    private final VoteRepository repository;
    private final RestaurantService restaurantService;


    @Transactional
    @Override
    @CacheEvict(value = "restaurants", allEntries = true)
    public void vote(Integer restaurantId, User user) {
        Vote vote = todayVote(user);
        if (vote!=null) {
            throw new RuntimeException("You have already voted.");
        } else {
            Restaurant restaurant=restaurantService.get(restaurantId);
            repository.save(new Vote(LocalDateTime.now(), restaurant, user));
        }
    }

    @Transactional
    @Override
    @CacheEvict(value = "restaurants", allEntries = true)
    public void update(Integer restaurantId, User user) {
        Vote vote = todayVote(user);
        if (vote != null) {
            LocalDateTime changeMind = LocalDateTime.of(LocalDate.now(), CHANGE_MIND);
            if (vote.getVoteDateTime().isBefore(changeMind)) {
                repository.updateVote(restaurantId, LocalDateTime.now(), vote.getId());
            } else throw new VoteException("You can re-vote until: " + CHANGE_MIND);
        }else{
            Restaurant restaurant=restaurantService.get(restaurantId);
            repository.save(new Vote(LocalDateTime.now(), restaurant, user));
        }

    }

    @Override
    public Vote todayVote(User user) {
        LocalDateTime startDay = LocalDate.now().atStartOfDay();
        LocalDateTime endDay = LocalDate.now().plusDays(1).atStartOfDay();
        return repository.getTodayVoteByUserId(startDay, endDay, Objects.requireNonNull(user.getId()));
    }

}
