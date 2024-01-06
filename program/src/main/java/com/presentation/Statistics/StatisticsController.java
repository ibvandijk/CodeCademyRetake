package com.presentation.Statistics;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import com.datastorage.SQLServerDatabase;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class StatisticsController implements Initializable {

    @FXML
    private Label lblStatistics;

    @FXML
    private Label lblTopWebcasts;

    @FXML
    private Button btnBack;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Load and display Statistics 1
        showStatistics(null);

        // Load and display Statistics 5
        showTopWebcasts(null);
    }

    @FXML
    void showStatistics(ActionEvent event) {
        // Implement the logic to fetch and display statistics here
        Connection conn = SQLServerDatabase.getDatabase().getConnection();

        try {
            String query = "WITH GenderCounts AS ( " +
                    "    SELECT " +
                    "        Gender, " +
                    "        COUNT(r.EmailAddress) AS TotalRegistrations " +
                    "    FROM " +
                    "        Registration r " +
                    "        JOIN Participant p ON r.EmailAddress = p.EmailAddress " +
                    "    GROUP BY " +
                    "        Gender " +
                    ") " +
                    ", CertificateCounts AS ( " +
                    "    SELECT " +
                    "        p.Gender, " +
                    "        COUNT(c.EmailAddress) AS TotalCertificates " +
                    "    FROM " +
                    "        Certificate c " +
                    "        JOIN Participant p ON c.EmailAddress = p.EmailAddress " +
                    "    GROUP BY " +
                    "        p.Gender " +
                    ") " +
                    "SELECT " +
                    "    gc.Gender, " +
                    "    COALESCE(cc.TotalCertificates, 0) AS TotalCertificates, " +
                    "    gc.TotalRegistrations, " +
                    "    CASE " +
                    "        WHEN gc.TotalRegistrations > 0 THEN " +
                    "            CONVERT(DECIMAL(5, 2), COALESCE(cc.TotalCertificates, 0) * 100.0 / gc.TotalRegistrations) "
                    +
                    "        ELSE 0 " +
                    "    END AS PercentageCertificates " +
                    "FROM " +
                    "    GenderCounts gc " +
                    "    LEFT JOIN CertificateCounts cc ON gc.Gender = cc.Gender";

            PreparedStatement stm = conn.prepareStatement(query);

            ResultSet rs = stm.executeQuery();

            StringBuilder statisticsText = new StringBuilder();
            while (rs.next()) {
                String gender = rs.getString("Gender");
                int totalCertificates = rs.getInt("TotalCertificates");
                int totalRegistrations = rs.getInt("TotalRegistrations");
                double percentageCertificates = rs.getDouble("PercentageCertificates");

                statisticsText.append("Gender: ").append(gender)
                        .append(", Total Certificates: ").append(totalCertificates)
                        .append(", Total Registrations: ").append(totalRegistrations)
                        .append(", Percentage Certificates: ").append(percentageCertificates).append("%\n");
            }

            if (statisticsText.length() > 0) {
                lblStatistics.setText(statisticsText.toString());
            } else {
                lblStatistics.setText("No data available");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void showTopWebcasts(ActionEvent event) {
        // Implement the logic to fetch and display top webcasts here
        Connection conn = SQLServerDatabase.getDatabase().getConnection();

        try {
            String query = "SELECT TOP 3 ContentItemID, COUNT(ContentItemID) AS AmountViewed " +
                    "FROM AmountSeen " +
                    "WHERE ContentItemID IN (SELECT ContentItemID FROM ContentItem WHERE WebcastTitle IS NOT NULL) " +
                    "GROUP BY ContentItemID";

            PreparedStatement stm = conn.prepareStatement(query);

            ResultSet rs1 = stm.executeQuery();

            StringBuilder statisticsText = new StringBuilder();
            while (rs1.next()) {
                System.out.println(statisticsText.toString());
                String contentItemID = rs1.getString("ContentItemID");
                String amountViewed = rs1.getString("AmountViewed");
            
                statisticsText.append("ContentItemID: ").append(contentItemID)
                        .append(", View count: ").append(amountViewed).append("\n");
            }            

            if (statisticsText.length() > 0) {
                lblTopWebcasts.setText(statisticsText.toString());
            } else {
                lblTopWebcasts.setText("Geen gegevens beschikbaar");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void backToHome(ActionEvent event) {
        // Implement the logic to navigate back to the home page
        try {
            Stage stage = (Stage) btnBack.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("../GUI/layoutGUI.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}