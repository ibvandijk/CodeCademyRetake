package com.datastorage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.verdictdb.commons.DBTablePrinter;

import com.domain.Certificate;

public class CertificateDAO {

    private ArrayList<Certificate> certificates;

    public static void main(String[] args) {

        try {
            SQLServerDatabase.getDatabase().connect();
            System.out.println("connected!");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        printCertificate();
    }

    public static void printCertificate() {
        try {
            Connection db = SQLServerDatabase.getDatabase().getConnection();
            // Prints full table of Certificate
            PreparedStatement statement = db.prepareStatement("SELECT * FROM Certificate;");
            ResultSet result = statement.executeQuery();
            DBTablePrinter.printResultSet(result);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("\n Certificate table printed!");
        }
    }

    // Adds Certificate
    public static void addCertificate(double grade, String employeeName, String courseName, String emailAddress)
            throws SQLException {
        Connection db = SQLServerDatabase.getDatabase().getConnection();
        PreparedStatement statement = db.prepareStatement(
                "INSERT INTO Certificate (Grade, EmployeeName, CourseName, EmailAddress) VALUES (?, ?, ?, ?);");
        try {
            statement.setString(1, String.valueOf(grade));
            statement.setString(2, employeeName);
            statement.setString(3, courseName);
            statement.setString(4, emailAddress);
            statement.executeUpdate();
            System.out.println("\n Certificate Added!");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            statement.close();
        }
    }

    public static void removeCertificate(String id) throws SQLException {
        Connection db = SQLServerDatabase.getDatabase().getConnection();
        PreparedStatement statement = db.prepareStatement(
                "DELETE FROM Certificate WHERE CertificateID = ?;");
        try {
            statement.setString(1, id);
            statement.executeUpdate();
            System.out.println("\n Certificates Removed!");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            statement.close();
        }
    }

    public static void updateCertificate(String employeeName, double grade, int id) throws SQLException {
        Connection db = SQLServerDatabase.getDatabase().getConnection();
        PreparedStatement statement = db.prepareStatement(
                "UPDATE Certificate SET EmployeeName = ?, Grade = ? WHERE CertificateID = ?;");
        try {
            statement.setString(1, employeeName);
            statement.setString(2, String.valueOf(grade));
            statement.setString(3, String.valueOf(id));
            statement.executeUpdate();
            System.out.println("\n Certificates Updated!");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            statement.close();
        }
    }

    public ArrayList<Certificate> getAllCertificates() {
        try {
            Connection db = SQLServerDatabase.getDatabase().getConnection();
            // Prints full table of Certificate
            PreparedStatement statement = db.prepareStatement("SELECT * FROM Certificate;");
            ResultSet result = statement.executeQuery();
            DBTablePrinter.printResultSet(result);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("\n Certificate table printed!");
        }
        return certificates;
    }
}