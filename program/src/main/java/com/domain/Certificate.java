package com.domain;

public class Certificate {

    private String emailAddress;
    private String grade;
    private String employeeName;
    private String courseName;

    public Certificate(String emailAddress, String grade, String employeeName, String courseName) {
        this.emailAddress = emailAddress;
        this.grade = grade;
        this.employeeName = employeeName;
        this.courseName = courseName;
    }

    public String getEmailAddress() {
        return this.emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getGrade() {
        return this.grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getEmployeeName() {
        return this.employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getCourseName() {
        return this.courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

}
