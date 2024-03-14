package com.example.server.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Entity
public class ClassTime {
    @Id
    @GeneratedValue
    private int id;
    private DayOfWeek day;
    private LocalTime startTime;
    private LocalTime endTime;
    private String teacherName;

    public ClassTime() {}

    public ClassTime(DayOfWeek day, LocalTime startTime, LocalTime endTime, String teacherName) {
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
        this.teacherName = teacherName;
    }

    public DayOfWeek getDay() {
        return day;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }
}
