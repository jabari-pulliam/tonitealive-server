package com.tonitealive.server.data;

import com.tonitealive.server.data.entities.UserProfileEntity;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserProfilesRepositoryTest {

    @Autowired
    private UserProfilesRepository userProfilesRepository;

    private List<String> usernames;
    private List<String> emails;
    private List<UserProfileEntity> profiles;

    @Before
    public void setup() {
        usernames = new ArrayList<>();
        usernames.add("user1");
        usernames.add("user2");
        usernames.add("user3");
        usernames.add("user4");

        emails = new ArrayList<>();
        emails.add("email1");
        emails.add("email2");
        emails.add("email3");
        emails.add("email4");

        profiles = new ArrayList<>();
        profiles.add(new UserProfileEntity("user1", "email1", "url"));
        profiles.add(new UserProfileEntity("user2", "email2", "url"));
        profiles.add(new UserProfileEntity("user3", "email3", "url"));
        profiles.add(new UserProfileEntity("user4", "email4", "url"));

        userProfilesRepository.save(profiles);
    }

    @Test
    public void findByUsername_shouldReturnProfile() {
        // When
        UserProfileEntity p = userProfilesRepository.findByUsername(usernames.get(0));

        // Then
        assertThat(p.getUsername()).isEqualTo(usernames.get(0));
    }

    @Test
    public void findByEmail_shouldReturnProfile() {
        // When
        UserProfileEntity p = userProfilesRepository.findByEmail(emails.get(0));

        // Then
        assertThat(p.getEmail()).isEqualTo(emails.get(0));
    }

    @Test
    public void findByUsername_shouldReturnNull() {
        // When
        UserProfileEntity p = userProfilesRepository.findByUsername("foo");

        // Then
        assertThat(p).isNull();
    }



}
