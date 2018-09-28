package com.weather.infraestructure.retrofit.service;

import com.weather.model.Weather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherService {

    @GET("weather")
    Call<Weather> getWeather(@Query("q") String city, @Query("appid") String appId);
}
