package com.tonitealive.server.web.testutil;

import org.springframework.stereotype.Component;

@Component
public class AccessTokenStore {

    private String accessToken;
    private String tokenType;

    public void setAccessToken(String accessToken, String tokenType) {
        this.accessToken = accessToken;
        this.tokenType = tokenType;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void clear() {
        tokenType = null;
        accessToken = null;
    }

}
