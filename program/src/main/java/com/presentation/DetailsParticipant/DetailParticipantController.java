package com.presentation.DetailsParticipant;

import java.net.URL;
import java.util.ResourceBundle;
import com.datastorage.ParticipantDAO;
import com.domain.Participant;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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

    public void initialize(URL url, ResourceBundle rb) {
        loadParticipantDetails();
    }

    private String participantEmail;

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
        } else {
            System.err.println("Error: No participant found with the email " + participantEmail);
        }
    }
}
