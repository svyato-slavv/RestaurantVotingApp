package ru.ivanov.restaurantvotingapplication.web.vote;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ivanov.restaurantvotingapplication.error.NotFoundException;
import ru.ivanov.restaurantvotingapplication.service.VoteService;
import ru.ivanov.restaurantvotingapplication.to.VoteTo;
import ru.ivanov.restaurantvotingapplication.util.VoteUtil;
import ru.ivanov.restaurantvotingapplication.web.AuthUser;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = UserVoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class UserVoteController {
    static final String REST_URL = "/api/user/votes";
    private final VoteService voteService;


    @GetMapping()
    public List<VoteTo> getVotes(@AuthenticationPrincipal AuthUser authUser) {
        log.info("find all votes for user with id = {}", authUser.id());
        return voteService.allVotes(authUser.getUser())
                .stream()
                .map(VoteUtil::getTo).toList();
    }

    @GetMapping("/{id}")
    public VoteTo get(@AuthenticationPrincipal AuthUser authUser, @PathVariable int id) {
        log.info("find vote with id = {} for user with id = {}", id, authUser.id());
        return VoteUtil.getTo(voteService.get(id, authUser.getUser().id()));
    }

    @GetMapping("/today")
    public VoteTo getTodayVote(@AuthenticationPrincipal AuthUser authUser) {
        log.info("find today vote for user with id = {}", authUser.id());
        return VoteUtil.getTo(voteService.todayVote(authUser.getUser()).orElseThrow(() -> new NotFoundException("Vote not found")));
    }
}
