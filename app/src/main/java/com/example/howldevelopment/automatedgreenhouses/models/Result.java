package com.example.howldevelopment.automatedgreenhouses.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Marno on 2018/04/11.
 */

public class Result {
    @SerializedName("name")
    @Expose
    private List<String> name = null;

    @SerializedName("prediction")
    @Expose
    private List<String> prediction = null;

    @SerializedName("timeStamp")
    @Expose
    private String timeStamp;

    @SerializedName("message")
    @Expose
    private String message;

    public List<String> getName() {
        return name;
    }

    public void setName(List<String> name) {
        this.name = name;
    }

    public List<String> getPrediction() {
        return prediction;
    }

    public void setPrediction(List<String> prediction) {
        this.prediction = prediction;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getMessage() {
        return message;
    }

}
