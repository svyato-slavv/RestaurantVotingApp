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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;


@Service
@RequiredArgsConstructor
public class VoteServiceImpl implements VoteService {
    private static final LocalTime CHANGE_MIND = LocalTime.of(11, 0);
    private final VoteRepository repository;
    private final RestaurantService restaurantService;


    @Transactional
    @Override
    @CacheEvict(value = "restaurants", allEntries = true)
    public void vote(Integer restaurantId, User user) {
        Vote vote = todayVote(user);
        if (vote != null) {
            throw new RuntimeException("You have already voted.");
        } else {
            Restaurant restaurant = restaurantService.get(restaurantId);
            repository.save(new Vote(LocalDateTime.now(), restaurant, user));
        }
    }

    @Transactional
    @Override
    @CacheEvict(value = "restaurants", allEntries = true)
    public void update(Integer restaurantId, User user) {
        Vote vote = todayVote(user);
        if (vote != null) {
            LocalDateTime changeMindDeadLine = LocalDateTime.of(LocalDate.now(), CHANGE_MIND);
            if (vote.getVoteDateTime().isBefore(changeMindDeadLine)) {
                repository.update(restaurantId, LocalDateTime.now(), vote.getId());
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
    public Vote todayVote(User user) {
        LocalDateTime todayStartDay = LocalDate.now().atStartOfDay();
        LocalDateTime todayEndDay = LocalDate.now().plusDays(1).atStartOfDay();
        return repository.getTodayVoteByUserId(todayStartDay, todayEndDay, user.id());
    }


}
