package com.example.hanlufeng.retrofitdemo;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Url;

public class MainActivity extends Activity {

    OkHttpClient client;
    Retrofit retrofit;
    ApiEndpoints apiService;
    String url ="http://javascript.info/tutorial/hello-world";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();


        this.client = new OkHttpClient.Builder().addInterceptor(logging)
                .connectTimeout(25, TimeUnit.SECONDS)
                .readTimeout(25, TimeUnit.SECONDS)
                .build();

        // retrofit with custom client


        this.retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                .build();
        this.apiService = retrofit.create(ApiEndpoints.class);

        Thread mThread = new Thread(new Runnable() {
            @Override
            public void run() {
                Call<String>  downCall =     downloadImageCall(url);
                try {
                    Response<String> mresponse =  downCall.execute();
                    String info  =mresponse.body();
                    Log.i("Separate","*********************************************************************");
                    Log.i("final info",info);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        mThread.start();

    }

    public Call<String> downloadImageCall(String uri){
        return apiService.rxGetImageCall(uri);
    }

}
