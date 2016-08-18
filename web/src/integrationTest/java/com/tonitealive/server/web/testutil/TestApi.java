package com.tonitealive.server.web.testutil;

import com.tonitealive.server.domain.models.UserProfile;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.*;

public interface TestApi {

    @POST("oauth/token")
    @FormUrlEncoded
    Call<LoginResponse> login(@Field("grant_type") String grantType,
                              @Field("username") String username,
                              @Field("password") String password);

    @GET("logout")
    Call<Void> logout();

    @GET("users/{username}")
    Call<UserProfile> getUserByUsername(@Path("username") String username);

    @Multipart
    @POST("users/{username}/profilePhoto")
    Call<Void> uploadProfilePhoto(@Path("username") String username,
                                  @Part("file") RequestBody profilePhoto);

}
