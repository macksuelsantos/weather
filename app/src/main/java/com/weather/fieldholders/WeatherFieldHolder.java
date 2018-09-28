package com.weather.fieldholders;

import com.google.gson.annotations.SerializedName;
import com.weather.model.Main;
import com.weather.model.Sys;
import com.weather.model.Weather;

import java.util.ArrayList;

public class WeatherFieldHolder {

    public String name;

    public Main main;

    @SerializedName("weather")
    public ArrayList<Weather> weathers;

    public Sys sys;

    public String getCity() {
        if (sys != null && sys.country != null) {
          return name + ", " + sys.country;
        }

        return name != null ? name : "";
    }
}
