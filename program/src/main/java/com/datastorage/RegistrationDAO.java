package com.datastorage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import org.verdictdb.commons.DBTablePrinter;

public class RegistrationDAO {
    
    public static void main(String[] args) {
        try{
            SQLServerDatabase.getDatabase().connect();
             System.out.println("connected!");
         } catch (SQLException e) {
             e.printStackTrace();
         }

        printRegistration();
    }

    public static void printRegistration() {
        try { Connection db = SQLServerDatabase.getDatabase().getConnection();
            // Prints full table of Participant
            PreparedStatement statement = db.prepareStatement("SELECT * FROM Registration;");
            ResultSet result = statement.executeQuery();
            DBTablePrinter.printResultSet(result);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("\n Registration table printed!");
        }
    }

    public static void addRegistration(String email, String courseName, LocalDate registrationDate) {
        try {Connection db = SQLServerDatabase.getDatabase().getConnection();
            // Prints full table of Participant
            PreparedStatement statement = db.prepareStatement(
                    "INSERT INTO Registration VALUES (?, ?, ?);");
            statement.setString(1, email);
            statement.setString(2, courseName);
            statement.setString(3, String.valueOf(registrationDate));
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("\n Registration Added!");
        }
    }

    public static void removeRegistration(String email) {
        try {Connection db = SQLServerDatabase.getDatabase().getConnection();
            // Removes a record by the name parameter
            PreparedStatement statement = db.prepareStatement("DELETE FROM Registration where EmailAddress = ?");
            statement.setString(1, email);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("\n Registration Removed!");
        }
    }

    public static void updateRegistration(String EmailAddress, String CourseName, LocalDate registrationDate) {
        try { Connection db = SQLServerDatabase.getDatabase().getConnection();
            //Updates records
            PreparedStatement statement = db.prepareStatement("UPDATE Registration SET CourseName = ?, RegistrationDate = ? WHERE EmailAddress = ?");
            statement.setString(1, CourseName);
            statement.setString(2, EmailAddress);
            statement.setString(3, String.valueOf(registrationDate));
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println(" \n Registration updated!");
        }
    }
}
