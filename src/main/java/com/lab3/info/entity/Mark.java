package com.lab3.info.entity;

public class Mark {
    private int id;
    private Student student;
    private Subject subject;
    private Teacher teacher;
    private String created;
    private int value;

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Mark{" +
                "student=" + student.getId() +
                ", subject=" + subject.getId() +
                ", teacher=" + teacher.getId() +
                ", created='" + created + '\'' +
                ", value=" + value +
                '}';
    }
}
