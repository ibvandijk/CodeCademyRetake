package com.datastorage;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.verdictdb.commons.DBTablePrinter;
import com.domain.Course;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CourseDAO {

    String empNo;
    Connection conn;
    Statement stmt;
    ResultSet rs;

    public static void main(String[] args) {
        
        try{
            SQLServerDatabase.getDatabase().connect();
             System.out.println("connected!");
         } catch (SQLException e) {
             e.printStackTrace();
         }
         
    }

    public static void printCourse() {
        try { Connection db = SQLServerDatabase.getDatabase().getConnection();
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

    public static ObservableList<Course> getCourse() {
        System.out.println("Get Course Called");

        ObservableList<Course> courses = FXCollections.observableArrayList();
        try {
            Connection conn = SQLServerDatabase.getDatabase().getConnection();
            String query = "SELECT * FROM Course;";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                Course course = new Course(
                        rs.getString("CourseName"),
                        rs.getInt("courseNumber"),
                        rs.getString("Subject"),
                        rs.getString("IntroductionText"),
                        rs.getString("Difficulty"));

                courses.add(course);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return courses;
    }


    public static ObservableList<String> getCourseNames() {
        ObservableList<String> courseNamesList = FXCollections.observableArrayList();
        Connection conn = SQLServerDatabase.getDatabase().getConnection();
        String query = "SELECT CourseName FROM Course;";
        
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
    
            while (rs.next()) {
                courseNamesList.add(rs.getString("CourseName"));
            }
    
        } catch (Exception e) {
            e.printStackTrace();
        }
    
        return courseNamesList;
    }

    public static void insertCourse(String courseName, int courseNumber, String subject, String introductionText, String difficulty) {
        
        try {
            Connection conn = SQLServerDatabase.getDatabase().getConnection();
            String query = "INSERT INTO Course VALUES (?, ?, ?, ?, ?)";
            
            PreparedStatement stm = conn.prepareStatement(query);

            stm.setString(1, courseName);
            stm.setInt(2, courseNumber);
            stm.setString(3, subject);
            stm.setString(4, introductionText);
            stm.setString(5, difficulty);

            stm.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteCourse(String courseName) {
        try {
            Connection conn = SQLServerDatabase.getDatabase().getConnection();
            String query = "DELETE FROM Course where CourseName = ?";
            PreparedStatement stm = conn.prepareStatement(query);

            stm.setString(1, courseName);
            stm.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateCourse(String courseName, int courseNumber, String subject, String introductionText, String difficulty) {
        System.out.println("Update Course Called");
        try {
            Connection conn = SQLServerDatabase.getDatabase().getConnection();
            String query = "UPDATE COURSE SET CourseName = ?, CourseNumber= ?, Subject= ?, IntroductionText= ?, Difficulty= ? WHERE CourseName= ?";

            try (PreparedStatement stm = conn.prepareStatement(query)) {
                stm.setString(1, courseName);
                stm.setInt(2, courseNumber);
                stm.setString(3, subject);
                stm.setString(4, introductionText);
                stm.setString(5, difficulty);
                stm.setString(6, courseName);

                stm.execute();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static List<String> getModuleNames() {
        List<String> moduleNames = new ArrayList<>();
        Connection conn = SQLServerDatabase.getDatabase().getConnection();
        String query = "SELECT ModuleTitle FROM Module WHERE CourseName IS NULL";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    moduleNames.add(rs.getString("ModuleTitle"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return moduleNames;
    }

}
