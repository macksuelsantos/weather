package com.weather.infraestructure.retrofit.client;

import android.app.Activity;

import com.weather.infraestructure.RequestHandler;
import com.weather.infraestructure.retrofit.service.WeatherService;
import com.weather.model.Weather;

import retrofit2.Call;

public class WeatherRestClient extends AbstractRestClient {

    private final WeatherService mWeatherService;

    public WeatherRestClient(Activity activity) {
        super(activity);

        mWeatherService = (WeatherService) createService(WeatherService.class);
    }

    public void getWeather(RequestHandler<Weather> requestHandler) {
        Call call = mWeatherService.getWeather("Maceio,br", APPID);
        addEnqueue(call, requestHandler);
    }
}
