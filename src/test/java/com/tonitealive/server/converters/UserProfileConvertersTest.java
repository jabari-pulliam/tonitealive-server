package com.tonitealive.server.converters;

import com.tonitealive.server.data.entities.UserProfileEntity;
import com.tonitealive.server.domain.models.UserProfile;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.convert.ConversionService;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class UserProfileConvertersTest {

    @Autowired
    private ConversionService conversionService;

    @Test
    public void convertToUserProfileEntity() {
        // Given
        String username = "username";
        String email = "email";
        String photoUrl = "url";
        UserProfile model = UserProfile.create(username, email, photoUrl);

        // When
        UserProfileEntity entity = conversionService.convert(model, UserProfileEntity.class);

        // Then
        assertThat(entity.getUsername()).isEqualTo(username);
        assertThat(entity.getEmail()).isEqualTo(email);
        assertThat(entity.getProfilePhotoUrl()).isEqualTo(photoUrl);
    }

    @Test
    public void convertToUserProfileModel() {
        // Given
        String username = "username";
        String email = "email";
        String photoUrl = "url";
        UserProfileEntity entity = new UserProfileEntity(username, email, photoUrl);

        // When
        UserProfile model = conversionService.convert(entity, UserProfile.class);

        // Then
        assertThat(model.username()).isEqualTo(username);
        assertThat(model.email()).isEqualTo(email);
        assertThat(model.profilePhotoUrl()).isEqualTo(photoUrl);
    }

}
