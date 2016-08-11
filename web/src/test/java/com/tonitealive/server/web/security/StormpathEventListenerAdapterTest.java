package com.tonitealive.server.web.security;

import com.stormpath.sdk.account.Account;
import com.stormpath.sdk.servlet.account.event.RegisteredAccountRequestEvent;
import com.tonitealive.server.domain.interactors.CreateNewUserAccountUseCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import rx.Observable;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StormpathEventListenerAdapterTest {

    private StormpathEventListenerAdapter adapter;

    @MockBean
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
        Account mockAccount = mock(Account.class);
        RegisteredAccountRequestEvent mockEvent = mock(RegisteredAccountRequestEvent.class);
        Observable<Void> testObservable = Observable.create(subscriber -> subscriber.onCompleted());

        given(mockAccount.getUsername()).willReturn(username);
        given(mockAccount.getEmail()).willReturn(email);
        given(mockEvent.getAccount()).willReturn(mockAccount);
        given(createNewUserAccountUseCase.execute(username, email)).willReturn(testObservable);

        // When
        adapter.on(mockEvent);

        // Then
        verify(createNewUserAccountUseCase).execute(username, email);
    }
}
