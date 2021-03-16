package com.frixty.frixtyhotel.APIs;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServerInstance {
    private static Retrofit instance;

    public static Retrofit getInstance(){
        return  instance == null?  new Retrofit.Builder()
                .baseUrl("https://shielded-retreat-47698.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build() : instance;
    }
}
