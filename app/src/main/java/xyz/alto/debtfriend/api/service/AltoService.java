package xyz.alto.debtfriend.api.service;

import java.util.List;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.Path;
import xyz.alto.debtfriend.api.model.DebtsResult;
import xyz.alto.debtfriend.api.model.FriendsResult;
import xyz.alto.debtfriend.api.model.LoginResult;
import xyz.alto.debtfriend.api.model.RegistrationResult;
import xyz.alto.debtfriend.api.model.User;
import xyz.alto.debtfriend.api.model.UserResult;

/**
 * Created by Anton on 2015-10-10.
 */
public interface AltoService {

    @Headers({"Accept: application/json", "Content-type: application/json"})
    @POST("/api/register")
    Call<RegistrationResult> register(@Body User user);

    @Headers({"Accept: application/json", "Content-type: application/json"})
    @POST("/api/login")
    Call<LoginResult> login(@Body User user);

    @Headers({"Accept: application/json", "Content-type: application/json"})
    @GET("/api/friends/all/{key}")
    Call<FriendsResult> getFriends(@Path("key") String key);

    @Headers({"Accept: application/json", "Content-type: application/json"})
    @GET("/api/friends/all/{key}")
    Call<DebtsResult> getDebts(@Path("key") String key);

    @Headers({"Accept: application/json", "Content-type: application/json"})
    @GET("/api/users/find/{username}")
    Call<UserResult> searchUser(@Path("username") String username);



}
