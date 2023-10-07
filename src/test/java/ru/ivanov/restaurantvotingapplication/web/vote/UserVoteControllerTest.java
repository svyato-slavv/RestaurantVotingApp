package ru.ivanov.restaurantvotingapplication.web.vote;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.ivanov.restaurantvotingapplication.service.VoteService;
import ru.ivanov.restaurantvotingapplication.web.AbstractControllerTest;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.ivanov.restaurantvotingapplication.web.user.UserTestData.*;
import static ru.ivanov.restaurantvotingapplication.web.vote.UserVoteController.REST_URL;
import static ru.ivanov.restaurantvotingapplication.web.vote.VoteTestData.SECOND_VOTE_ID;

class UserVoteControllerTest extends AbstractControllerTest {
    private static final String REST_URL_SLASH = REST_URL + '/';
    private static final String TODAY = "today";
    @Autowired
    private VoteService voteService;


    @Test
    void getUnAuth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }


    @Test
    @WithUserDetails(value = USER_MAIL)
    void getVotes() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].restaurantId").value(1));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + SECOND_VOTE_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.restaurantId").value(1));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getNotYours() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + SECOND_VOTE_ID))
                .andExpect(status().isNotFound())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.is("The voice doesn't belong to you")));
    }

    @Test
    @WithUserDetails(value = USER_2_MAIL)
    void getTodayVote() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + TODAY))
                .andExpect(status().isNotFound())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.is("Vote not found")));
    }
}