package com.weather.application.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.weather.R;
import com.weather.application.helper.SysHelper;
import com.weather.fieldholders.WeatherFieldHolder;
import com.weather.infraestructure.RequestHandler;
import com.weather.infraestructure.retrofit.client.WeatherRestClient;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView mTextViewCity;
    private TextView mTextViewTemp, mTextViewTempMin, mTextViewTempMax;
    private TextView mTextViewWeather;
    private TextView mTextViewHumidity, mTextViewSunrise, mTextViewSunset;
    private ImageView mImageViewIcon;

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

        mImageViewIcon = findViewById(R.id.activity_main_image_view_icon);

        mWeatherRestClient = new WeatherRestClient(this);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        getWeather("Maceió");
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

        mTextViewTemp.setText(String.format(Locale.getDefault(), "%d°C", weatherFieldHolder.getTempCelsius()));
        mTextViewTempMin.setText(String.format(Locale.getDefault(), "%d°", weatherFieldHolder.getTempMinCelsius()));
        mTextViewTempMax.setText(String.format(Locale.getDefault(), "%d°", weatherFieldHolder.getTempMaxCelsius()));

        mTextViewWeather.setText(weatherFieldHolder.getWeather());

        mTextViewHumidity.setText(String.format(Locale.getDefault(), "%d%%", weatherFieldHolder.getHumidity()));

        mTextViewSunrise.setText(SysHelper.convertHourMinutes(weatherFieldHolder.getSunrise()));
        mTextViewSunset.setText(SysHelper.convertHourMinutes(weatherFieldHolder.getSunset()));

        Picasso.get().load("http://openweathermap.org/img/w/" + weatherFieldHolder.getIcon() + ".png").into(mImageViewIcon);
    }
}
