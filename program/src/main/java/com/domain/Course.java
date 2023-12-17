package com.domain;

import java.sql.*;
import java.util.*;

import org.verdictdb.commons.DBTablePrinter;

public class Course {

    private String courseName;
    private int courseNumber;
    private String subject;
    private String introductionText;
    private Difficulty difficulty;
    private ArrayList<Module> modules;

    public Course(String courseName, int courseNumber, String subject, String introductionText, Difficulty difficulty,
            ArrayList<Module> modules) {
        this.courseName = courseName;
        this.courseNumber = courseNumber;
        this.subject = subject;
        this.introductionText = introductionText;
        this.difficulty = difficulty;
        this.modules = modules;
    }

    // Logica
    public static void main(String[] Args) {
        printCourse();
        // addCourse();
        // removeCourse(null);
        // updateCourse(null, null);
    }

    public static void printCourse() {
        String url = "jdbc:sqlserver://aei-sql2.avans.nl:1443;databaseName=CodeAcademyOffice;";
        String user = "boomkip";
        String password = "KipBoom123";
        try (Connection db = DriverManager.getConnection(url, user, password)) {
            // Prints full table of Participant
            PreparedStatement statement = db.prepareStatement("SELECT * FROM Course;");
            ResultSet result = statement.executeQuery();
            DBTablePrinter.printResultSet(result);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("\n Course table printed!");
        }
    }

    // (Module module)
    public static void addCourse() {
        String url = "jdbc:sqlserver://aei-sql2.avans.nl:1443;databaseName=CodeAcademyOffice;";
        String user = "boomkip";
        String password = "KipBoom123";
        try (Connection db = DriverManager.getConnection(url, user, password)) {
            // Prints full table of Participant
            PreparedStatement statement = db.prepareStatement(
                    "INSERT INTO Course VALUES ('Databases 3', 5, 'Databases', 'This course is about saving data', 'EXPERT');");
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("\n Course Added!");
        }
    }

    public static void removeCourse(String name) {
        String url = "jdbc:sqlserver://aei-sql2.avans.nl:1443;databaseName=CodeAcademyOffice;";
        String user = "boomkip";
        String password = "KipBoom123";
        try (Connection db = DriverManager.getConnection(url, user, password)) {
            // Removes a record by the name parameter
            PreparedStatement statement = db.prepareStatement("DELETE FROM Course where CourseName = ?");
            String selectSQL = "DELETE FROM Course WHERE name = ?";
            db.prepareStatement(selectSQL);
            statement.setString(1, name);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("\n Course Removed!");
        }
    }

    public static void updateCourse(String courseName, String introductionText) {
        String url = "jdbc:sqlserver://aei-sql2.avans.nl:1443;databaseName=CodeAcademyOffice;";
        String user = "boomkip";
        String password = "KipBoom123";
        try (Connection db = DriverManager.getConnection(url, user, password)) {
            //Updates records
            PreparedStatement statement = db.prepareStatement("UPDATE Course SET IntroductionText = ? WHERE CourseName = ?");
            String selectSQL = "UPDATE Course SET IntroductionText = ? WHERE CourseName = ?";
            db.prepareStatement(selectSQL);
            statement.setString(1, introductionText);
            statement.setString(2, courseName);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println(" \n Course updated!");
        }
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

    public Difficulty getDifficulty() {
        return this.difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public ArrayList<Module> getModules() {
        return this.modules;
    }

    public void setModules(ArrayList<Module> modules) {
        this.modules = modules;
    }

}
