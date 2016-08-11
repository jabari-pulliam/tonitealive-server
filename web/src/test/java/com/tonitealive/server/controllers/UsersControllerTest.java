package com.tonitealive.server.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tonitealive.server.data.entities.UserProfileNode;
import com.tonitealive.server.domain.models.UserProfile;
import com.tonitealive.server.web.controllers.UsersController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UsersController.class)
public class UsersControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private JacksonTester<UserProfile> json;

    @Before
    public void setup() {
        ObjectMapper mapper = new ObjectMapper();
        JacksonTester.initFields(this, mapper);
    }

    @Test
    @WithMockUser
    public void getProfileByUsername_withExistingUsername_shouldReturnUserProfile() throws Exception {
        // Given
        String username = "user";
        String email = "email";
        UserProfileNode profile = new UserProfileNode(username, email);
        UserProfile model = UserProfile.create(username, email);

        // When
        mockMvc.perform(get("/users/" + username))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is(username)))
                .andExpect(jsonPath("$.email", is(email)));
    }

    @Test
    @WithMockUser
    public void getProfileByUsername_withUsernameDoesNotExist_shouldReturn404() throws Exception {
        // Given
        String username = "user";

        // When
        mockMvc.perform(get("/users/" + username))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    public void updateProfilePhoto_shouldSavePhoto() throws Exception {
        // Given
        String username = "user";
        MockMultipartFile imageFile = new MockMultipartFile("photo", "my file data".getBytes());

        // When
        mockMvc.perform(fileUpload("/users/" + username + "/profilePhoto").file(imageFile))
                .andExpect(status().isOk());

        // Then
    }

}
