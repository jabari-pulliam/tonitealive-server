package com.tonitealive.server.web.controllers;

import com.tonitealive.server.Profiles;
import com.tonitealive.server.domain.models.UserProfile;
import com.tonitealive.server.domain.repositories.UserProfilesRepository;
import com.tonitealive.server.web.testutil.IntegrationTestBase;
import com.tonitealive.server.web.testutil.LoginUtil;
import com.tonitealive.server.web.testutil.TestApi;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
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

    @Autowired
    private TestApi testApi;

    @Autowired
    private LoginUtil loginUtil;

    private String[] usernames = {"user1", "user2", "user3", "user4"};
    private String[] emails = {"foo1@bar.coom", "foo2@bar.com", "foo3@bar.com", "foo4@bar.com"};

    @Before
    public void setup() throws IOException {
        loginUtil.login();

        for (int i = 0; i < usernames.length; i++) {
            userProfilesRepository.save(UserProfile.create(usernames[i], emails[i]));
        }
    }

    @After
    public void tearDown() throws IOException {
        loginUtil.logout();
    }

    @Test
    public void getUserByUsername_shouldReturnProfile() throws Exception {
        // Given
        String username = usernames[0];

        // When
        Response<UserProfile> response = testApi.getUserByUsername(username).execute();

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
        Response<UserProfile> response = testApi.getUserByUsername(username).execute();

        // Then
        assertThat(response.code()).isEqualTo(404);
    }

    @Test
    public void saveProfilePhoto_shouldSavePhoto() throws IOException {
        // Given
        String usernamme = usernames[0];
        Resource testImageResource = context.getResource("classpath:images/test_image.jpg");
        File imageFile = testImageResource.getFile();
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), imageFile);
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", imageFile.getName(), requestFile);

        // When
        Response<Void> response = testApi.uploadProfilePhoto(usernamme, filePart).execute();
        Response<UserProfile> userProfileResponse = testApi.getUserByUsername(usernamme).execute();
        UserProfile profile = userProfileResponse.body();

        assertThat(profile).isNotNull();
        assertThat(profile.profilePhotoId()).isNotNull();
        Response<String> photoUrlResponse = testApi.getUrlForImage(userProfileResponse.body().profilePhotoId(),
                200,200).execute();

        // Then
        assertThat(response.code()).isEqualTo(201);
        assertThat(photoUrlResponse.code()).isEqualTo(200);
        assertThat(photoUrlResponse.body()).isNotEmpty();
    }

}
