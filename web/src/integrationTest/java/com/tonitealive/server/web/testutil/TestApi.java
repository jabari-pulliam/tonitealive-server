package com.tonitealive.server.web.testutil;

import com.tonitealive.server.domain.models.UserProfile;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.*;

public interface TestApi {

    @POST("oauth/token")
    @FormUrlEncoded
    Call<LoginResponse> login(@Field("grant_type") String grantType,
                              @Field("username") String username,
                              @Field("password") String password);

    @POST("logout")
    Call<Void> logout();

    @GET("users/{username}")
    Call<UserProfile> getUserByUsername(@Path("username") String username);

    @Multipart
    @POST("users/{username}/profilePhoto")
    Call<Void> uploadProfilePhoto(@Path("username") String username,
                                  @Part MultipartBody.Part profilePhoto);

    @GET("images")
    Call<String> getUrlForImage(@Query("fileId") String fileId,
                                @Query("width") int width,
                                @Query("height") int height);

}
