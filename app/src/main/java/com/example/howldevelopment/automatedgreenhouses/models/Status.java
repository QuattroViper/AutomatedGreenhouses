package com.example.howldevelopment.automatedgreenhouses.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Marno on 2018/04/11.
 */

public class Status {
    @SerializedName("temperature")
    @Expose
    private String temperature;
    @SerializedName("humidity")
    @Expose
    private String humidity;
    @SerializedName("something")
    @Expose
    private String something;

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getSomething() {
        return something;
    }

    public void setSomething(String something) {
        this.something = something;
    }
}
