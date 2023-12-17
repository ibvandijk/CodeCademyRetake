package com.datastorage;

import java.sql.*;
import java.time.LocalDate;
//import java.util.ArrayList;
import org.verdictdb.commons.DBTablePrinter;
//import com.domain.Participant;

public class ParticipantDAO {

    //private ArrayList<Participant> participants;
    
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

         printParticipant();
         
    }

    public static void printParticipant() {
        try { Connection db = SQLServerDatabase.getDatabase().getConnection();
            // Prints full table of Participant
            PreparedStatement statement = db.prepareStatement("SELECT * FROM Participant;");
            ResultSet result = statement.executeQuery();
            DBTablePrinter.printResultSet(result);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("\n Participant table printed!");
        }
    }

    public static void addParticipant(String email, String name, LocalDate  birthdate, String gender, String address, String city, String country) {
        try { Connection db = SQLServerDatabase.getDatabase().getConnection();
            // Prints full table of Participant
            String sql = "INSERT INTO PARTICIPANT" + " VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = db.prepareStatement(sql);
            statement.setString(1, email);
            statement.setString(2, name);
            statement.setString(3, String.valueOf(birthdate));
            statement.setString(4, gender);
            statement.setString(5, address);
            statement.setString(6, city);
            statement.setString(7, country);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("\n Participant Added!");
        }
    }

    public static void updateParticipant(String emailAddress, String name, LocalDate birthdate, String gender, String address, String postalCode, String city, String country) {
        try {
            Connection db = SQLServerDatabase.getDatabase().getConnection();
            // Updates records
            String sql = "UPDATE Participant SET Name = ?, Birthdate = ?, Gender = ?, Address = ?, PostalCode = ?, City = ?, Country = ? WHERE EmailAddress = ?";
            PreparedStatement statement = db.prepareStatement(sql);
    
            statement.setString(1, name);
            statement.setString(2, String.valueOf(birthdate));
            statement.setString(3, gender);
            statement.setString(4, address);
            statement.setString(5, postalCode);
            statement.setString(6, city);
            statement.setString(7, country);
            statement.setString(8, emailAddress);
    
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("\n Participant updated!");
        }
    }
    

    public static void GetNameParticipant() {
        try { Connection db = SQLServerDatabase.getDatabase().getConnection();
            // Prints full table of Participant
            PreparedStatement statement = db.prepareStatement("SELECT Name FROM Participant;");
            ResultSet result = statement.executeQuery();
            DBTablePrinter.printResultSet(result);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("\n Participant name printed!");
        }
    }
}
