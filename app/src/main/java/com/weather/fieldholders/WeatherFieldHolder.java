package com.weather.fieldholders;

import com.google.gson.annotations.SerializedName;
import com.weather.model.Main;
import com.weather.model.Sys;
import com.weather.model.Weather;

import java.util.ArrayList;

public class WeatherFieldHolder {

    public String name;

    private Main main;

    @SerializedName("weather")
    private ArrayList<Weather> weathers;

    private Sys sys;

    public String getCity() {
        if (sys != null && sys.country != null) {
          return name + ", " + sys.country;
        }

        return name != null ? name : "";
    }

    public int getTempCelsius() {
        if (main == null) return 0;

        return (int) main.temp;
    }

    public int getTempMaxCelsius() {
        if (main == null) return 0;

        return (int) main.tempMax;
    }

    public int getTempMinCelsius() {
        if (main == null) return 0;

        return (int) main.tempMin;
    }

    public int getHumidity() {
        if (main == null) return 0;

        return main.humidity;
    }

    public long getSunrise() {
        if (sys == null) return 0;

        return sys.sunrise;
    }

    public long getSunset() {
        if (sys == null) return 0;

        return sys.sunset;
    }

    public String getWeather() {
        if (weathers == null || weathers.isEmpty()) return "";

        return weathers.get(0).main;
    }

    public String getIcon() {
        if (weathers == null || weathers.isEmpty()) return "50d";

        return weathers.get(0).icon;
    }
}
