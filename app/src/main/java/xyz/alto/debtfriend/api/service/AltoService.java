package xyz.alto.debtfriend.api.service;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.Headers;
import retrofit.http.POST;
import xyz.alto.debtfriend.api.model.RegistrationResult;
import xyz.alto.debtfriend.api.model.User;

/**
 * Created by Anton on 2015-10-10.
 */
public interface AltoService {

    /**
     * USER
     */
    @Headers({"Accept: application/json", "Content-type: application/json"})
    @POST("/api/register")
    Call<RegistrationResult> register(@Body User user);
}
