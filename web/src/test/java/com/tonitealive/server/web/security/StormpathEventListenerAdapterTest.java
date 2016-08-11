package com.tonitealive.server.web.security;

import com.stormpath.sdk.account.Account;
import com.stormpath.sdk.servlet.account.event.RegisteredAccountRequestEvent;
import com.tonitealive.server.domain.interactors.CreateNewUserAccountUseCase;
import com.tonitealive.server.domain.models.UserProfile;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StormpathEventListenerAdapterTest {

    private StormpathEventListenerAdapter adapter;

    @SpyBean
    private CreateNewUserAccountUseCase createNewUserAccountUseCase;

    @Before
    public void setup() {
        adapter = new StormpathEventListenerAdapter(createNewUserAccountUseCase);
    }

    @Test
    public void onEvent_shouldCreateUserProfile() {
        // Given
        String username = "username";
        String email = "email";
        UserProfile profile = UserProfile.create(username, email);
        Account mockAccount = mock(Account.class);
        RegisteredAccountRequestEvent mockEvent = mock(RegisteredAccountRequestEvent.class);

        given(mockAccount.getUsername()).willReturn(username);
        given(mockAccount.getEmail()).willReturn(email);
        given(mockEvent.getAccount()).willReturn(mockAccount);

        // When
        adapter.on(mockEvent);

        // Then
        verify(createNewUserAccountUseCase).execute(username, email);
    }
}
