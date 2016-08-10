package com.tonitealive.server.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tonitealive.server.data.entities.UserProfileEntity;
import com.tonitealive.server.domain.exceptions.ResourceNotFoundException;
import com.tonitealive.server.domain.models.UserProfile;
import com.tonitealive.server.services.UserProfilesService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
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

    @MockBean
    private UserProfilesService userProfilesService;

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
        UserProfileEntity profile = new UserProfileEntity(username, email);
        UserProfile model = UserProfile.create(username, email);
        given(userProfilesService.getProfileByUsername(username)).willReturn(model);

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
        given(userProfilesService.getProfileByUsername(username))
                .willThrow(ResourceNotFoundException.create("UserProfile", username));

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
        verify(userProfilesService).updateProfilePhoto(anyString(), any());
    }

}
