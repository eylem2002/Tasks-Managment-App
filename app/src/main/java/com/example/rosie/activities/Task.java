package com.example.rosie.activities;

import com.google.gson.annotations.SerializedName;

public class Task {




    @SerializedName("Id")
    int id;
    @SerializedName("taskn")
    String taskn;
    @SerializedName("desc")
    String desc;
    @SerializedName("timeT")//timeC
    String timeT;
    @SerializedName("timeC")
    String timeC;


    @SerializedName("statuss")
    String statuss;

    private boolean imageViewClicked;

    public boolean isImageViewClicked() {
        return imageViewClicked;
    }

    public void toggleImageViewClicked() {
        imageViewClicked = !imageViewClicked;
    }


    public Task(String taskn, String desc, String timeT,String timeC ,String statuss,int id) {
        this.taskn = taskn;
        this.desc = desc;
        this.timeT = timeT;
        this.timeC=timeC;
        this.id=id;
        this.statuss=statuss;
    }


    public String getStatus() {
        return statuss;
    }
    public String getTaskn() {
        return taskn;
    }


    public String getDesc() {
        return desc;
    }


    public String getTimeT() {
        return timeT;
    }


    public String getTimeC() {
        return timeC;
    }

    public int getId() {
        return id;
    }

}
