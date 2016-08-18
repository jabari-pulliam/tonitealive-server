package com.tonitealive.server.web.controllers;

import com.tonitealive.server.Profiles;
import com.tonitealive.server.domain.models.UserProfile;
import com.tonitealive.server.domain.repositories.UserProfilesRepository;
import com.tonitealive.server.web.testutil.IntegrationTestBase;
import okhttp3.RequestBody;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import retrofit2.Response;

import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@ActiveProfiles(Profiles.TEST)
public class UsersControllerIntegrationTest extends IntegrationTestBase {

    @Autowired
    private UserProfilesRepository userProfilesRepository;

    @Autowired
    private ApplicationContext context;

    private String[] usernames = {"user1", "user2", "user3", "user4"};
    private String[] emails = {"foo1@bar.coom", "foo2@bar.com", "foo3@bar.com", "foo4@bar.com"};

    @Before
    public void setup() throws IOException {
        for (int i = 0; i < usernames.length; i++) {
            userProfilesRepository.save(UserProfile.create(usernames[i], emails[i]));
        }
    }

    @After
    public void tearDown() throws IOException {

    }

    @Test
    public void getUserByUsername_shouldReturnProfile() throws Exception {
        // Given
        String username = usernames[0];

        // When
        Response<UserProfile> response = getTestApi().getUserByUsername(username).execute();

        // Then
        assertThat(response.code()).isEqualTo(200);
        UserProfile profile = response.body();
        assertThat(profile.username()).isEqualTo(username);
    }

    @Test
    public void getUserByUsername_shouldReturn404() throws IOException {
        // Given
        String username = "nothing";

        // When
        Response<UserProfile> response = getTestApi().getUserByUsername(username).execute();

        // Then
        assertThat(response.code()).isEqualTo(404);
    }

    @Test
    public void saveProfilePhoto_shouldSavePhoto() throws IOException {
        // Given
        String usernamme = usernames[0];
        Resource testImageResource = context.getResource("classpath:images/test_image.jpg");
        File imageFile = testImageResource.getFile();
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("image/*"), imageFile);

        // When
        Response<Void> response = getTestApi().uploadProfilePhoto(usernamme, body).execute();

        // Then
        assertThat(response.code()).isEqualTo(201);
    }

}
