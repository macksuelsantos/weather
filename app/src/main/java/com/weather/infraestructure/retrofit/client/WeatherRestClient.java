package com.weather.infraestructure.retrofit.client;

import android.app.Activity;

import com.weather.infraestructure.RequestHandler;
import com.weather.infraestructure.retrofit.service.WeatherService;
import com.weather.fieldholders.WeatherFieldHolder;

import retrofit2.Call;

public class WeatherRestClient extends AbstractRestClient {

    private final WeatherService mWeatherService;

    public WeatherRestClient(Activity activity) {
        super(activity);

        mWeatherService = (WeatherService) createService(WeatherService.class);
    }

    public void getWeather(RequestHandler<WeatherFieldHolder> requestHandler, String city) {
        Call call = mWeatherService.getWeather(city, APPID);
        addEnqueue(call, requestHandler);
    }
}
