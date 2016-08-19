package com.tonitealive.server.web.testutil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class LoginUtil {

    private final AccessTokenStore tokenStore;
    private final TestApi testApi;

    @Value("${TONITEALIVE_TEST_USERNAME}")
    private String username;

    @Value("${TONITEALIVE_TEST_PASSWORD}")
    private String password;

    @Autowired
    public LoginUtil(AccessTokenStore tokenStore, TestApi testApi) {
        this.tokenStore = tokenStore;
        this.testApi = testApi;
    }

    public boolean login() throws IOException {
        retrofit2.Response<LoginResponse> response = testApi.login("password", username, password).execute();

        if (response.isSuccessful()) {
            LoginResponse loginResponse = response.body();
            tokenStore.setAccessToken(loginResponse.getAccessToken(), loginResponse.getTokenType());
            return true;
        }

        return false;
    }

    public void logout() throws IOException {
        testApi.logout().execute();
        tokenStore.clear();
    }

}
