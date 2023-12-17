package com.domain;

public class Registration {

    private String email;
    private String courseName;
    private String date;

    public Registration(String email, String courseName, String date) {
        this.email = email;
        this.courseName = courseName;
        this.date = date;
    }

    // Logica
    public static void main(String[] Args) {
        
    }

    // Getters en Setters
    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCourseName() {
        return this.courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }


}
