package com.example.superman_application.TokenHandling;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ITokenController {

    @POST("/authenticate")
    Call<JsonObject> createAuthenticationToken(@Body JsonObject authenticationRequest);
}


