package com.weather.application.activity;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.weather.R;
import com.weather.infraestructure.RequestHandler;
import com.weather.infraestructure.retrofit.client.WeatherRestClient;
import com.weather.fieldholders.WeatherFieldHolder;

public class MainActivity extends AppCompatActivity {

    private WeatherRestClient mWeatherRestClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mWeatherRestClient = new WeatherRestClient(this);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        getWeather("Maceio,br");
    }

    private void getWeather(String city) {
        mWeatherRestClient.getWeather(new RequestHandler<WeatherFieldHolder>(this) {
            @Override
            public void onSuccess(WeatherFieldHolder response) {
                super.onSuccess(response);
            }

            @Override
            public void onFail(Throwable error) {
                super.onFail(error);
            }
        }, city);
    }
}
