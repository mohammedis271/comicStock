package com.example.superman_application.TokenHandling;

import com.google.gson.JsonObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Token {

    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    public static Token getInstance() {
        return instance;
    }

    private static final Token instance = new Token();
    private static String token;
    public Token(){

    }
    public void getAccessTokenFromUser(String user, String password){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("username", user);
        jsonObject.addProperty("password",password);
        ITokenController tokenController = retrofit.create(ITokenController.class);
        Call<JsonObject> getToken = tokenController.createAuthenticationToken(jsonObject);
        try {
            Response<JsonObject> response = getToken.execute();
            token = response.body().get("token").getAsString();


        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public String getToken(){
        return "Bearer " + token;
    }
}
