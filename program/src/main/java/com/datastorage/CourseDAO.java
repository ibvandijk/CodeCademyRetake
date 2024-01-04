package com.datastorage;

import java.sql.*;
import org.verdictdb.commons.DBTablePrinter;

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


    public static void addCourse(String courseName, int courseNumber, String subject, String introductionText, String difficulty) {
    try {
        Connection db = SQLServerDatabase.getDatabase().getConnection();

        String sql = "INSERT INTO COURSE (courseName, courseNumber, subject, introductionText, difficulty) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement statement = db.prepareStatement(sql);
        statement.setString(1, courseName);
        statement.setInt(2, courseNumber);
        statement.setString(3, subject);
        statement.setString(4, introductionText);
        statement.setString(5, difficulty);

        statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("\n Course Added!");
        }
    }

    public static void removeCourse(String courseName) {
        try {Connection db = SQLServerDatabase.getDatabase().getConnection();
            PreparedStatement statement = db.prepareStatement("DELETE FROM Course where CourseName = ?");
            statement.setString(1, courseName);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("\n Registration Removed!");
        }
    }

    public static void updateCourse(String courseName, int courseNumber, String subject, String introductionText, String difficulty) {
        try {
            Connection db = SQLServerDatabase.getDatabase().getConnection();

        
            String sql = "UPDATE COURSE SET CourseName= ?, CourseNumber= ?, Subject= ?, IntroductionText= ?, Difficulty= ? WHERE CourseName= ?";
            PreparedStatement statement = db.prepareStatement(sql);

            statement.setString(1, courseName);
            statement.setInt(2, courseNumber);
            statement.setString(3, subject);
            statement.setString(4, introductionText);
            statement.setString(5, difficulty);


            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("\n Course Updated!");
        }
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

}
