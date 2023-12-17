package com.domain;

public class Module {

    private String moduleTitle;
    private String version;
    private String moduleDescription;
    private String contactPersonName;
    private String contactPersonEmail;
    private int courseNumber;

    public Module(String moduleTitle, String version, String moduleDescription, String contactPersonName,
            String contactPersonEmail, int courseNumber) {
        this.moduleTitle = moduleTitle;
        this.version = version;
        this.moduleDescription = moduleDescription;
        this.contactPersonName = contactPersonName;
        this.contactPersonEmail = contactPersonEmail;
        this.courseNumber = courseNumber;
    }

    //Logica

    //Getters en Setters
    public String getModuleTitle() {
        return this.moduleTitle;
    }

    public void setModuleTitle(String moduleTitle) {
        this.moduleTitle = moduleTitle;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getModuleDescription() {
        return this.moduleDescription;
    }

    public void setModuleDescription(String moduleDescription) {
        this.moduleDescription = moduleDescription;
    }

    public String getContactPersonName() {
        return this.contactPersonName;
    }

    public void setContactPersonName(String contactPersonName) {
        this.contactPersonName = contactPersonName;
    }

    public String getContactPersonEmail() {
        return this.contactPersonEmail;
    }

    public void setContactPersonEmail(String contactPersonEmail) {
        this.contactPersonEmail = contactPersonEmail;
    }

    public int getCourseNumber() {
        return this.courseNumber;
    }

    public void setCourseNumber(int courseNumber) {
        this.courseNumber = courseNumber;
    }


}
