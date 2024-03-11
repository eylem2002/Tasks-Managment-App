package com.example.rosie.activities;

import com.google.gson.annotations.SerializedName;

public class Task {



    @SerializedName("taskn")
    String taskn;
    @SerializedName("desc")
    String desc;
    @SerializedName("timeT")//timeC
    String timeT;
    @SerializedName("timeC")
    String timeC;

    public Task(String taskn, String desc, String timeT,String timeC ) {
        this.taskn = taskn;
        this.desc = desc;
        this.timeT = timeT;
        this.timeC=timeC;
    }

    public String getTaskn() {
        return taskn;
    }

    public void setTaskn(String taskn) {
        this.taskn = taskn;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTimeT() {
        return timeT;
    }

    public void setTimeT(String timeT) {
        this.timeT = timeT;
    }

    public String getTimeC() {
        return timeC;
    }

    public void setTimeC(String timeC) {
        this.timeC = timeC;
    }


}
