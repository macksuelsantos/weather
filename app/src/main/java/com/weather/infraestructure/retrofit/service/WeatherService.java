package com.weather.infraestructure.retrofit.service;

import com.weather.fieldholders.WeatherFieldHolder;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherService {

    @GET("weather")
    Call<WeatherFieldHolder> getWeather(@Query("q") String city, @Query("appid") String appId);
}
