package com.example.superman_application.Controller;

import com.example.superman_application.DTO.LoginDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ICustomerController {
    @POST("/Login")
    Call<Integer> loginUser(@Body LoginDTO loginDTO);
}
