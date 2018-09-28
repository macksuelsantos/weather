package com.weather.model;

import com.google.gson.annotations.SerializedName;

public class Main {

    public double temp;

    @SerializedName("temp_min")
    public double tempMin;

    @SerializedName("temp_max")
    public double tempMax;

    public int humidity;

}
