package com.weather.application.activity;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.weather.R;
import com.weather.infraestructure.RequestHandler;
import com.weather.infraestructure.retrofit.client.WeatherRestClient;
import com.weather.fieldholders.WeatherFieldHolder;

public class MainActivity extends AppCompatActivity {

    private TextView mTextViewCity;
    private TextView mTextViewTemp, mTextViewTempMin, mTextViewTempMax;
    private TextView mTextViewWeather;
    private TextView mTextViewHumidity, mTextViewSunrise, mTextViewSunset;

    private WeatherRestClient mWeatherRestClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextViewCity = findViewById(R.id.activity_main_text_view_city);

        mTextViewTemp = findViewById(R.id.activity_main_text_view_temp);
        mTextViewTempMin = findViewById(R.id.activity_main_text_view_temp_min);
        mTextViewTempMax = findViewById(R.id.activity_main_text_view_temp_max);

        mTextViewWeather = findViewById(R.id.activity_main_text_view_weather);

        mTextViewHumidity = findViewById(R.id.activity_main_text_view_humidity);

        mTextViewSunrise = findViewById(R.id.activity_main_text_view_sunrise);
        mTextViewSunset = findViewById(R.id.activity_main_text_view_sunset);

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

                setupView(response);
            }

            @Override
            public void onFail(Throwable error) {
                super.onFail(error);
            }
        }, city);
    }

    private void setupView(WeatherFieldHolder weatherFieldHolder) {
        if (weatherFieldHolder == null) return;

        mTextViewCity.setText(weatherFieldHolder.getCity());

        mTextViewTemp.setText(String.valueOf(weatherFieldHolder.main.temp));
        mTextViewTempMin.setText(String.valueOf(weatherFieldHolder.main.tempMin));
        mTextViewTempMax.setText(String.valueOf(weatherFieldHolder.main.tempMax));

        mTextViewWeather.setText(weatherFieldHolder.weathers.get(0).main);

        mTextViewHumidity.setText(String.valueOf(weatherFieldHolder.main.humidity));
        
        mTextViewSunrise.setText(String.valueOf(weatherFieldHolder.sys.sunrise));
        mTextViewSunset.setText(String.valueOf(weatherFieldHolder.sys.sunset));
    }
}
