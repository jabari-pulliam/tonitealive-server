package com.tonitealive.server.data.repositories;

import com.tonitealive.server.Profiles;
import com.tonitealive.server.data.entities.UserProfileNode;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(Profiles.TEST)
public class UserProfileNodesRepositoryTest {

    @Autowired
    private UserProfileNodesRepository userProfileNodesRepository;

    private List<String> usernames;
    private List<String> emails;
    private List<UserProfileNode> profiles;

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
        profiles.add(new UserProfileNode("user1", "email1"));
        profiles.add(new UserProfileNode("user2", "email2"));
        profiles.add(new UserProfileNode("user3", "email3"));
        profiles.add(new UserProfileNode("user4", "email4"));

        userProfileNodesRepository.save(profiles);
    }

    @Test
    public void findByUsername_shouldReturnProfile() {
        // When
        UserProfileNode p = userProfileNodesRepository.findByUsername(usernames.get(0));

        // Then
        assertThat(p.getUsername()).isEqualTo(usernames.get(0));
    }

    @Test
    public void findByEmail_shouldReturnProfile() {
        // When
        UserProfileNode p = userProfileNodesRepository.findByEmail(emails.get(0));

        // Then
        assertThat(p.getEmail()).isEqualTo(emails.get(0));
    }

    @Test
    public void findByUsername_shouldReturnNull() {
        // When
        UserProfileNode p = userProfileNodesRepository.findByUsername("foo");

        // Then
        assertThat(p).isNull();
    }



}
