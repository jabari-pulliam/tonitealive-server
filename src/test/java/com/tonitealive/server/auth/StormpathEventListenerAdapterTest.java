package com.tonitealive.server.auth;

import com.stormpath.sdk.account.Account;
import com.stormpath.sdk.servlet.account.event.RegisteredAccountRequestEvent;
import com.tonitealive.server.domain.models.UserProfile;
import com.tonitealive.server.services.UserProfilesService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StormpathEventListenerAdapterTest {

    @Autowired
    private StormpathEventListenerAdapter adapter;

    @MockBean
    private UserProfilesService userProfilesService;

    @Test
    public void onEvent_shouldCreateUserProfile() {
        // Given
        String username = "username";
        String email = "email";
        UserProfile profile = UserProfile.create(username, email, null);
        Account mockAccount = mock(Account.class);
        RegisteredAccountRequestEvent mockEvent = mock(RegisteredAccountRequestEvent.class);

        given(mockAccount.getUsername()).willReturn(username);
        given(mockAccount.getEmail()).willReturn(email);
        given(mockEvent.getAccount()).willReturn(mockAccount);

        // When
        adapter.on(mockEvent);

        // Then
        Mockito.verify(userProfilesService).createProfile(eq(profile));
    }
}
