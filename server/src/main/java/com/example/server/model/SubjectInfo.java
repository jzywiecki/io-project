package com.example.server.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class SubjectInfo {
    @Id
    @GeneratedValue
    private int id;
    private String className;

    public SubjectInfo() {}

    public SubjectInfo(String className) {
        this.className = className;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
