package com.datastorage;

import java.sql.*;

import com.domain.AmountSeen;
import com.domain.Certificate;
import com.domain.ContentItem;
import com.domain.ParticipantProgress;
import com.domain.Registration;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ParticipantProgressDAO {

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

    // Om de Registrations bij een specifieke participant op te halen
    public static ObservableList<Registration> getRegistrationsByEmail(String emailAddress) {
            ObservableList<Registration> registrationList = FXCollections.observableArrayList();
        
            try {
                Connection db = SQLServerDatabase.getDatabase().getConnection();
                PreparedStatement statement = db.prepareStatement("SELECT * FROM Registration WHERE EmailAddress = ?");
                statement.setString(1, emailAddress);
                ResultSet result = statement.executeQuery();
        
                while (result.next()) {
                        // Retrieve and add the CertificaatNaam (course name)
                        String courseName = result.getString("CourseName");
                        String date = result.getString("Date");

                    registrationList.add(new Registration(emailAddress, courseName, date));
                }
    
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            } 
            return registrationList;
    }

    // Om de certificaten bij een specifieke participant op te halen
    public static ObservableList<Certificate> getCertificatesForEmail(String emailAddress) {
        ObservableList<Certificate> certificatesList = FXCollections.observableArrayList();
    
        try {
            Connection db = SQLServerDatabase.getDatabase().getConnection();
    
            String query = "SELECT CourseName FROM Certificate WHERE EmailAddress = ?";
            PreparedStatement statement = db.prepareStatement(query);
            statement.setString(1, emailAddress);
    
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                // Retrieve and add the CertificaatNaam (course name)
                String courseName = result.getString("CourseName");
                certificatesList.add(new Certificate(courseName));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Print a message when the connection is closed
            System.out.println("Connection closed");
        }
    
        return certificatesList;
    }
    
    // To retrieve participant progress for a specific participant by email
     // To retrieve participant progress for a specific participant by email
    public static ObservableList<ParticipantProgress> getParticipantProgressByEmail(String emailAddress) {
        ObservableList<ParticipantProgress> participantProgressesList = FXCollections.observableArrayList();

        
        try {

            Connection conn = SQLServerDatabase.getDatabase().getConnection();
            PreparedStatement statement = conn.prepareStatement("SELECT R.EmailAddress, R.CourseName, CI.ModuleTitle, CI.ModuleVersion, CI.WebcastTitle, ASV.ViewPercentage " +
                        "FROM Registration R " +
                        "JOIN ContentItem CI ON R.CourseName = CI.CourseName " +
                        "LEFT JOIN AmountSeen ASV ON R.EmailAddress = ASV.EmailAddress AND CI.ContentItemID = ASV.ContentItemID " +
                        "WHERE R.EmailAddress = ? " +
                        "ORDER BY R.CourseName");

                statement.setString(1, emailAddress);
                ResultSet result = statement.executeQuery();

                while (result.next()) {
                        String registrationEmailAddress = result.getString("EmailAddress");
                        String courseName = result.getString("CourseName");
                        String moduleTitle = result.getString("ModuleTitle");
                        String moduleVersion = result.getString("ModuleVersion");
                        String webcastTitle = result.getString("WebcastTitle");
                        double viewPercentage = result.getDouble("ViewPercentage");

                        Registration registration = new Registration(registrationEmailAddress, courseName);
                        ContentItem contentItem = new ContentItem(moduleTitle, moduleVersion, webcastTitle);
                        AmountSeen amountSeen = new AmountSeen(registrationEmailAddress, viewPercentage);

                        participantProgressesList.add(new ParticipantProgress(registration, contentItem, amountSeen));
                }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return participantProgressesList;
    }

    public static void insertCertificate(String emailAddress, String courseName, String date) {
        try {
            Connection conn = SQLServerDatabase.getDatabase().getConnection();
            String insertQuery = "INSERT INTO Certificate (EmailAddress, CourseName) VALUES (?, ?)";
            String deleteQuery = "DELETE FROM Registration WHERE EmailAddress = ? AND CourseName = ? AND Date = ?";
       
            PreparedStatement stmInsert = conn.prepareStatement(insertQuery);

            stmInsert.setString(1, emailAddress);
            stmInsert.setString(2, courseName);

            PreparedStatement stmDelete = conn.prepareStatement(deleteQuery);
            stmDelete.setString(1, emailAddress);
            stmDelete.setString(2, courseName);
            stmDelete.setString(3, date);

            stmInsert.execute();
            stmDelete.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    

}
