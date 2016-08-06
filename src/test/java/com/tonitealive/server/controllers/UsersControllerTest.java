package com.tonitealive.server.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tonitealive.server.data.UserProfilesRepository;
import com.tonitealive.server.data.entities.UserProfileEntity;
import com.tonitealive.server.domain.models.UserProfileModel;
import com.tonitealive.server.services.UserProfilesService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.GsonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UsersController.class)
public class UsersControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private GsonTester<UserProfileModel> json;

    @MockBean
    private UserProfilesService userProfilesService;

    @Before
    public void setup() {
        Gson gson = new GsonBuilder().create();
        GsonTester.initFields(this, gson);
    }

    @Test
    @WithMockUser
    public void getProfileByUsername_withExistingUsername_shouldReturnUserProfile() throws Exception {
        // Given
        String username = "user";
        String email = "email";
        UserProfileEntity profile = new UserProfileEntity(username, email, null);
        UserProfileModel model = UserProfileModel.create(username, email, null);
        given(userProfilesService.getProfileByUsername(username)).willAnswer(new Answer<Future<UserProfileModel>>() {
            @Override
            public Future<UserProfileModel> answer(InvocationOnMock invocation) throws Throwable {
                Future<UserProfileModel> future = mock(FutureTask.class);
                Mockito.when(future.get()).thenReturn(model);
                return future;
            }
        });

        // When
        mockMvc.perform(get("/users/" + username))
                .andExpect(status().isOk())
                .andExpect(content().json(json.write(model).getJson()));
    }


}
