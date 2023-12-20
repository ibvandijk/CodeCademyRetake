package com.domain;

public class Course {

    private String courseName;
    private int courseNumber;
    private String subject;
    private String introductionText;
    private String difficulty;

    public Course(String courseName, int courseNumber, String subject, String introductionText, String string) {
        this.courseName = courseName;
        this.courseNumber = courseNumber;
        this.subject = subject;
        this.introductionText = introductionText;
        this.difficulty = string;
    }

    public static void main(String[] Args) {
    }

    // Getters en Setters
    public String getCourseName() {
        return this.courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getCourseNumber() {
        return this.courseNumber;
    }

    public void setCourseNumber(int courseNumber) {
        this.courseNumber = courseNumber;
    }

    public String getSubject() {
        return this.subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getIntroductionText() {
        return this.introductionText;
    }

    public void setIntroductionText(String introductionText) {
        this.introductionText = introductionText;
    }

    public String getDifficulty() {
        return this.difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }
}
