package com.lab3.info.entity;

public class Report {
    private String subjectName;
    private String markValue;
    private String grade;

    public Report() {
    }

    public Report(String subjectName, String markValue, String grade) {
        this.subjectName = subjectName;
        this.markValue = markValue;
        this.grade = grade;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getMarkValue() {
        return markValue;
    }

    public void setMarkValue(String markValue) {
        this.markValue = markValue;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
}
