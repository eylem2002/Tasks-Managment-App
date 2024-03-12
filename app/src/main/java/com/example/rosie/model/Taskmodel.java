package com.example.rosie.model;

public class Taskmodel {

    private int id;
    private String taskName;
    private String description;
    private String timeToDo;
    private String timeCreate;


    public Taskmodel(int id, String taskName, String description, String timeToDo, String timeCreate) {
        this.id = id;
        this.taskName = taskName;
        this.description = description;
        this.timeToDo = timeToDo;
        this.timeCreate = timeCreate;
    }

    public int getId() {
        return id;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getDescription() {
        return description;
    }

    public String getTimeToDo() {
        return timeToDo;
    }

    public String getTimeCreate() {
        return timeCreate;
    }



}
