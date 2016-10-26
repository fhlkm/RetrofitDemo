package com.example.hanlufeng.retrofitdemo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by hanlu.feng on 10/26/2016.
 */
public interface ApiEndpoints {
    @GET
    Call<String> rxGetImageCall(@Url String imageUrl);
}
