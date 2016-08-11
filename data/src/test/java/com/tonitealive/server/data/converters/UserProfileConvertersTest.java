package com.tonitealive.server.data.converters;

import com.tonitealive.server.Profiles;
import com.tonitealive.server.data.entities.UserProfileNode;
import com.tonitealive.server.domain.models.UserProfile;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@ActiveProfiles(Profiles.TEST)
public class UserProfileConvertersTest {

    @TestConfiguration
    public static class TestConfig {

        @Bean
        public ConversionService conversionService() {
            GenericConversionService service = new GenericConversionService();
            service.addConverter(new UserProfileNodeConverter());
            service.addConverter(new UserProfileModelConverter());
            return service;
        }

    }

    @Autowired
    private ConversionService conversionService;

    @Test
    public void convertToUserProfileEntity() {
        // Given
        String username = "username";
        String email = "email";
        UserProfile model = UserProfile.create(username, email);

        // When
        UserProfileNode entity = conversionService.convert(model, UserProfileNode.class);

        // Then
        assertThat(entity.getUsername()).isEqualTo(username);
        assertThat(entity.getEmail()).isEqualTo(email);
    }

    @Test
    public void convertToUserProfileModel() {
        // Given
        String username = "username";
        String email = "email";
        UserProfileNode entity = new UserProfileNode(username, email);

        // When
        UserProfile model = conversionService.convert(entity, UserProfile.class);

        // Then
        assertThat(model.username()).isEqualTo(username);
        assertThat(model.email()).isEqualTo(email);
    }

}
