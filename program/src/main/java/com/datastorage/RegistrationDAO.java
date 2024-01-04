package com.datastorage;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.verdictdb.commons.DBTablePrinter;
import com.domain.Registration;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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

    public static ObservableList<Registration> getRegistrations() {
        ObservableList<Registration> registrations = FXCollections.observableArrayList();
        Connection conn = SQLServerDatabase.getDatabase().getConnection();
        String query = "SELECT * FROM Registration;";
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                Registration registration = new Registration(
                    rs.getString("EmailAddress"),
                    rs.getString("CourseName"),
                    rs.getDate("Date").toString());
                registrations.add(registration);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return registrations;
    }

    public static void insertRegistration(String email, String courseName, String date) {
        Connection conn = SQLServerDatabase.getDatabase().getConnection();
        String query = "INSERT INTO Registration VALUES (?, ?, ?)";
        try {
            PreparedStatement stm = conn.prepareStatement(query);
            stm.setString(1, email);
            stm.setString(2, courseName);
            stm.setDate(3, Date.valueOf(date));
            stm.execute();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void deleteRegistration(Registration registration) {
        Connection conn = SQLServerDatabase.getDatabase().getConnection();
        String query = "DELETE FROM Registration WHERE EmailAddress = ? AND CourseName = ?";
        try {
            PreparedStatement stm = conn.prepareStatement(query);
            stm.setString(1, registration.getEmail());
            stm.setString(2, registration.getCourseName());
            stm.execute();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void updateRegistration(String email, String courseName, String date) {
        Connection conn = SQLServerDatabase.getDatabase().getConnection();
        String query = "UPDATE Registration SET CourseName = ?, Date = ? WHERE EmailAddress = ?";
        try {
            PreparedStatement stm = conn.prepareStatement(query);
            stm.setString(1, courseName);
            stm.setDate(2, Date.valueOf(date));
            stm.setString(3, email);
            stm.execute();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
