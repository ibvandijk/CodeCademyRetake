package com.domain;

public class Module {

    private String moduleTitle;
    private String version;
    private String moduleDescription;
    private String contactPersonName;
    private String contactPersonEmail;
    private String courseName;

    public Module(String moduleTitle, String version, String moduleDescription, String contactPersonName,
            String contactPersonEmail, String courseName) {
        this.moduleTitle = moduleTitle;
        this.version = version;
        this.moduleDescription = moduleDescription;
        this.contactPersonName = contactPersonName;
        this.contactPersonEmail = contactPersonEmail;
        this.courseName = courseName;
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
    
    public String getCourseName() {
        return this.courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }


}
