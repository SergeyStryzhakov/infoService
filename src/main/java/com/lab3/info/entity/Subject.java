package com.lab3.info.entity;

public class Subject {
    private int id;
    private String title;
    private int hours;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }


    @Override
    public String toString() {
        return "Subject{" +
                " title='" + title + '\'' +
                ", hours=" + hours +
                '}';
    }
}
