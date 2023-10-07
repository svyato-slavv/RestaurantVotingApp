package ru.ivanov.restaurantvotingapplication.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ivanov.restaurantvotingapplication.error.NotFoundException;
import ru.ivanov.restaurantvotingapplication.error.VoteException;
import ru.ivanov.restaurantvotingapplication.model.Restaurant;
import ru.ivanov.restaurantvotingapplication.model.User;
import ru.ivanov.restaurantvotingapplication.model.Vote;
import ru.ivanov.restaurantvotingapplication.repository.VoteRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class VoteServiceImpl implements VoteService {
    public static final LocalTime CHANGE_MIND = LocalTime.of(11, 0);
    private final VoteRepository repository;
    private final RestaurantService restaurantService;

    @Override
    public Vote get(int id, Integer userId) {
        return repository.findByIdAndUserId(id, userId).orElseThrow(() -> new NotFoundException("The voice doesn't belong to you"));
    }

    @Transactional
    @Override
    @CacheEvict(value = "restaurants", allEntries = true)
    public Vote vote(Integer restaurantId, User user) {
        Optional<Vote> todayVote = todayVote(user);
        if (todayVote.isPresent()) {
            throw new VoteException("You have already voted.");
        } else {
            Restaurant restaurant = restaurantService.get(restaurantId);
            Vote vote = new Vote(LocalDateTime.now(), restaurant, user);
            return repository.save(vote);
        }
    }


    @Transactional
    @Override
    @CacheEvict(value = "restaurants", allEntries = true)
    public void update(Integer restaurantId, User user) {
        Optional<Vote> vote = todayVote(user);
        if (vote.isPresent()) {
            LocalDateTime changeMindDeadLine = LocalDateTime.of(LocalDate.now(), CHANGE_MIND);
            if (vote.get().getVoteDateTime().isBefore(changeMindDeadLine)) {
                repository.update(restaurantId, LocalDateTime.now(), vote.get().id());
            } else throw new VoteException("You can re-vote until: " + CHANGE_MIND);
        } else {
            Restaurant restaurant = restaurantService.get(restaurantId);
            repository.save(new Vote(LocalDateTime.now(), restaurant, user));
        }

    }

    @Override
    public List<Vote> allVotes(User user) {
        return repository.findAllByUserId(user.id());
    }

    @Override
    public Optional<Vote> todayVote(User user) {
        LocalDateTime todayStartDay = LocalDate.now().atStartOfDay();
        LocalDateTime todayEndDay = LocalDate.now().plusDays(1).atStartOfDay();
        return repository.getTodayVoteByUserId(todayStartDay, todayEndDay, user.id());
    }


}
