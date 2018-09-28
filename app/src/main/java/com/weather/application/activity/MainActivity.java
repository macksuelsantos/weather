package com.weather.application.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

    private EditText mEditTextCity;
    private View mContainer;

    private WeatherRestClient mWeatherRestClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContainer = findViewById(R.id.activity_main_container);

        mTextViewCity = findViewById(R.id.activity_main_text_view_city);

        mTextViewTemp = findViewById(R.id.activity_main_text_view_temp);
        mTextViewTempMin = findViewById(R.id.activity_main_text_view_temp_min);
        mTextViewTempMax = findViewById(R.id.activity_main_text_view_temp_max);

        mTextViewWeather = findViewById(R.id.activity_main_text_view_weather);

        mTextViewHumidity = findViewById(R.id.activity_main_text_view_humidity);

        mTextViewSunrise = findViewById(R.id.activity_main_text_view_sunrise);
        mTextViewSunset = findViewById(R.id.activity_main_text_view_sunset);

        mImageViewIcon = findViewById(R.id.activity_main_image_view_icon);

        mEditTextCity = findViewById(R.id.activity_main_edit_text_city);

        Button buttonFind = findViewById(R.id.activity_main_button_find);
        buttonFind.setOnClickListener(onClickButtonFindListener);

        mWeatherRestClient = new WeatherRestClient(this);
    }

    private void getWeather(String city) {
        mContainer.setVisibility(View.GONE);

        mWeatherRestClient.getWeather(new RequestHandler<WeatherFieldHolder>(this) {
            @Override
            public void onSuccess(WeatherFieldHolder response) {
                super.onSuccess(response);

                setupView(response);
            }

            @Override
            public void onFail(Throwable error) {
                super.onFail(error);

                Toast.makeText(MainActivity.this, "Ooops, algo deu errado.", Toast.LENGTH_SHORT).show();
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

        mContainer.setVisibility(View.VISIBLE);
    }

    private final View.OnClickListener onClickButtonFindListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String city = mEditTextCity.getText().toString();

            if (!city.isEmpty()) {
                hideKeyboard(MainActivity.this);
                getWeather(city);
            } else {
                Toast.makeText(MainActivity.this, "Digite o nome da cidade.", Toast.LENGTH_SHORT).show();
            }
        }
    };

    private static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
