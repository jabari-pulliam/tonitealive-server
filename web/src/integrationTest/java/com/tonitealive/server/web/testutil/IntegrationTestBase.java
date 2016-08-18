package com.tonitealive.server.web.testutil;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okio.Buffer;
import org.junit.After;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class IntegrationTestBase {

    @LocalServerPort
    private int serverPort;

    @Value("${TONITEALIVE_TEST_USERNAME}")
    private String testUsername;

    @Value("${TONITEALIVE_TEST_PASSWORD}")
    private String testUserPassword;

    private TestApi testApi;
    private HeadersInterceptor headersInterceptor;

    @Before
    public void setupBase() throws IOException {
        testApi = buildTestApi("http://localhost:" + serverPort + "/");
        assertThat(login(testUsername, testUserPassword)).isTrue();
    }

    @After
    public void tearDownBase() throws IOException {
        logout();
    }

    private TestApi buildTestApi(String baseUrl) {
        headersInterceptor = new HeadersInterceptor(baseUrl);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(headersInterceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(baseUrl)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        return retrofit.create(TestApi.class);
    }

    protected TestApi getTestApi() {
        return testApi;
    }

    private boolean login(String username, String password) throws IOException {
        retrofit2.Response<LoginResponse> response = testApi.login("password", username, password).execute();

        if (response.isSuccessful()) {
            LoginResponse loginResponse = response.body();
            headersInterceptor.setToken(loginResponse.getAccessToken(), loginResponse.getTokenType());
            return true;
        }

        return false;
    }

    private void logout() throws IOException {
        testApi.logout().execute();
        headersInterceptor.clearToken();
    }

    private static class RequestLoggingInterceptor implements Interceptor {

        private final Logger log = LoggerFactory.getLogger(getClass());

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Buffer buf = new Buffer();

            if (request.body() != null) {
                request.body().writeTo(buf);
                log.debug("Request body: " + buf.readUtf8());
            }

            return chain.proceed(request);
        }
    }

    private static class HeadersInterceptor implements Interceptor {

        private final String baseUrl;
        private String token;
        private String tokenType;

        HeadersInterceptor(String baseUrl) {
            this.baseUrl = baseUrl;
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            okhttp3.Request request = chain.request();

            okhttp3.Request.Builder builder = request.newBuilder()
                    .addHeader("Accept", "application/json");
                    //.addHeader("Origin", baseUrl);

            if (token != null)
                builder.addHeader("Authorization", tokenType + " " + token);

            return chain.proceed(builder.build());
        }

        void setToken(String token, String tokenType) {
            this.token = token;
            this.tokenType = tokenType;
        }

        void clearToken() {
            token = null;
            tokenType = null;
        }
    }


}
