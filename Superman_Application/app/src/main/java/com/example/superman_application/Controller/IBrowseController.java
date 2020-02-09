package com.example.superman_application.Controller;


import com.example.superman_application.DTO.BrowseDTO;
import com.example.superman_application.DTO.IssueDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IBrowseController {

    @GET("/Browse/{pageNumber}")
    Call<List<IssueDTO>> getComics( @Path("pageNumber") int pageNumber);
}
