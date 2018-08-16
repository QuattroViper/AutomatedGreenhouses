package com.example.howldevelopment.automatedgreenhouses.functions;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Marno on 2018/04/11.
 */

public class RetrofitClass {

    public static Retrofit createRetrofit() {
        return new Retrofit.Builder()
                .baseUrl("http://41.168.11.34:9000")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

}
