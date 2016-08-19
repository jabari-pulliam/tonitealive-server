package com.tonitealive.server.web.testutil;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import java.io.IOException;

import static com.tonitealive.server.web.testutil.TestConstants.TEST_PORT;

@TestConfiguration
public class TestConfig {

    @Autowired
    private AccessTokenStore tokenStore;

    @Bean
    public TestApi testApi() {
        String baseUrl = "http://localhost:" + TEST_PORT + "/";
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            private final Logger log = LoggerFactory.getLogger(getClass());

            @Override
            public void log(String message) {
                log.debug(message);
            }
        });

        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new HeadersInterceptor(tokenStore))
                .addInterceptor(loggingInterceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(baseUrl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        return retrofit.create(TestApi.class);
    }

    private static class HeadersInterceptor implements Interceptor {

        private final AccessTokenStore tokenStore;

        HeadersInterceptor(AccessTokenStore tokenStore) {
            this.tokenStore = tokenStore;
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();

            Request.Builder builder = request.newBuilder()
                    .addHeader("Accept", "application/json");

            String token = tokenStore.getAccessToken();
            String tokenType = tokenStore.getTokenType();
            if (token != null && tokenType != null)
                builder.addHeader("Authorization", tokenType + " " + token);

            return chain.proceed(builder.build());
        }
    }

}
