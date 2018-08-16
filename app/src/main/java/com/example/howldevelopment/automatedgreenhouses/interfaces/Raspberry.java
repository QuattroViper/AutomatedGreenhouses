package com.example.howldevelopment.automatedgreenhouses.interfaces;

import com.example.howldevelopment.automatedgreenhouses.models.History;
import com.example.howldevelopment.automatedgreenhouses.models.Result;
import com.example.howldevelopment.automatedgreenhouses.models.Status;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Marno on 2018/04/11.
 */

public interface Raspberry {

    @GET("/raspberry/results")
    Call<Result> results();

    @GET("/raspberry/status")
    Call<Status> status();

    @GET("/raspberry/history")
    Call<History> history();


}
