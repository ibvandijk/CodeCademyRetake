package com.presentation.GUI;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class GUIController implements Initializable {
    
    @FXML
    private Button btnParticipants;

    @FXML
    private Button btnCourses;

    @FXML
    private Button btnRegistrations;

    @FXML
    private Button btnCertificate;
    
    @FXML
    void handleButtonAction(ActionEvent event) throws IOException {
        Stage stage = null;
        Parent root = null;

        if (event.getSource() == btnParticipants) {
            stage = (Stage) btnParticipants.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("../Participant/layoutParticipant.fxml"));
        } 
        
        if (event.getSource() == btnRegistrations) {
            stage = (Stage) btnRegistrations.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("../Registration/layoutRegistration.fxml"));

        }       
        if (event.getSource() == btnCertificate) {
            stage = (Stage) btnCertificate.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("../Certificate/layoutCertificate.fxml"));
        } 
        else if (event.getSource() == btnCourses) {

        }

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void initialize(URL url, ResourceBundle rb) {
    }
}
