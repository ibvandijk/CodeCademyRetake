package com.presentation.DetailsParticipant;

import java.net.URL;
import java.util.ResourceBundle;

import com.datastorage.CertificateDAO;
import com.datastorage.ParticipantDAO;
import com.domain.Certificate;
import com.domain.Participant;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;


public class DetailParticipantController implements Initializable{

    @FXML
    private Text efEmail;
    
    @FXML
    private Text efBirthday;
    
    @FXML
    private Text efGender;

    @FXML
    private Text efAddress;

    @FXML
    private Text efPostalCode;

    @FXML
    private Text efCity;

    @FXML
    private Text efCountry;
    
    @FXML
    private TableView<Certificate> tvCertificates;

    @FXML
    private TableColumn<Certificate, String> colCourses;

    private String participantEmail;

    public void initialize(URL url, ResourceBundle rb) {
        setParticipantEmail(participantEmail);
        loadParticipantDetails();
    }

    public void setParticipantEmail(String email) {
        this.participantEmail = email;
        efEmail.setText(email);
    }

    public void loadParticipantDetails() {
        System.out.println("Load participantDetails is called");

        ObservableList<Participant> details = ParticipantDAO.getParticipantByEmail(participantEmail);

        if (!details.isEmpty()) {
            Participant participant = details.get(0);
            
            efGender.setText(participant.getGender());
            efBirthday.setText(participant.getBirthdate());
            efAddress.setText(participant.getAddress());
            efPostalCode.setText(participant.getPostalCode());
            efCity.setText(participant.getCity());
            efCountry.setText(participant.getCountry());

            initializeCertificatesTable();
        } else {
            System.err.println("Error: No participant found with the email " + participantEmail);
        }
    }

    private void initializeCertificatesTable() {
        System.out.println("Fill up certificates table");

        colCourses.setCellValueFactory(new PropertyValueFactory<>("CourseName"));

        ObservableList<Certificate> certificatesList = CertificateDAO.getCertificatesForEmail(participantEmail);

        tvCertificates.setItems(certificatesList);
    }
    
    
}
