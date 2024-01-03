package com.presentation.Participant;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.ResourceBundle;
import com.datastorage.ParticipantDAO;
import com.datastorage.SQLServerDatabase;
import com.domain.Participant;
import com.presentation.DetailsParticipant.DetailParticipantController;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ParticipantController implements Initializable {

    @FXML
    private Button btnBack;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnInsert;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnClear;

    @FXML
    private Button btnDetails;

    @FXML
    private TableColumn<Participant, String> colDate;

    @FXML
    private TableColumn<Participant, Integer> colID;

    @FXML
    private TableColumn<Participant, String> colPostalCode;

    @FXML
    private TableColumn<Participant, String> colEmail;

    @FXML
    private TableColumn<Participant, String> colName;

    @FXML
    private TableColumn<Participant, String> colGender;

    @FXML
    private TableColumn<Participant, String> colAddress;

    @FXML
    private TableColumn<Participant, String> colCity;

    @FXML
    private TableColumn<Participant, String> colCountry;

    @FXML
    private TextField tfDateYear;

    @FXML
    private TextField tfDateMonth;

    @FXML
    private TextField tfDateDay;

    @FXML
    private TextField tfAddress;

    @FXML
    private TextField tfCity;

    @FXML
    private TextField tfCountry;

    @FXML
    private TextField tfEmail;

    @FXML
    private TextField tfGender;

    @FXML
    private TextField tfName;

    @FXML
    private TextField tfPostalCode;

    @FXML
    private TableView<Participant> tvParticipants;

    boolean isClicked = false;

    ObservableList<Participant> participants;

    @FXML
    void handleButtonAction(ActionEvent event) throws IOException {
        if (event.getSource() == btnInsert) {
            insertButton();
        } 
        else if (event.getSource() == btnDelete) {
            deleteButton();
        } 
        else if (event.getSource() == btnClear) {
            isClicked = true;
            clear();    
        } 
        else if (event.getSource() == btnUpdate && isClicked) {
            updateButton();
            isClicked = false;
        }
        else if(event.getSource() == btnBack) {
            backToHome();
        }
        else if (event.getSource() == btnUpdate && !isClicked) {
            isClicked = true;
            setText();
        }
        else if (event.getSource() == btnDetails) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../DetailsParticipant/layoutDetailParticipant.fxml"));
            Parent root = loader.load();
            DetailParticipantController detailController = loader.getController();
    
            String selectedEmail = tvParticipants.getSelectionModel().getSelectedItem().getEmail();
            System.out.println(selectedEmail);
    
            detailController.setParticipantEmail(selectedEmail);

            detailController.loadParticipantDetails();
    
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        }
    }

        public void initialize(URL url, ResourceBundle rb) {
            showParticipant();
        }

        public void backToHome() throws IOException{
            Stage stage = null;
            Parent root = null;

            stage = (Stage) btnBack.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("../GUI/layoutGUI.fxml"));
            

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }

        public void showParticipant() {
            System.out.println("Show Participants Method Called");
            ObservableList<Participant> participantList = ParticipantDAO.getParticipants();
        
            colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
            colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        
            tvParticipants.setItems(participantList);
        }


        private void insertButton() {
            System.out.println("Insert Participant Method Called");

            LocalDate birthdate = LocalDate.of(
                    Integer.parseInt(tfDateYear.getText()),
                    Integer.parseInt(tfDateMonth.getText()),
                    Integer.parseInt(tfDateDay.getText())
            );

            ParticipantDAO.insertParticipant(
                    tfEmail.getText(),
                    tfName.getText(),
                    birthdate,
                    tfGender.getText(),
                    tfAddress.getText(),
                    tfPostalCode.getText(),
                    tfCity.getText(),
                    tfCountry.getText()
            );

            clear();
            showParticipant();
        }
    

        public void deleteButton() {
            System.out.println("Delete Participant Method Called");

            String selectedEmail = tvParticipants.getSelectionModel().getSelectedItem().getEmail();

            ParticipantDAO.deleteParticipantByEmail(selectedEmail);

            showParticipant();
        }

    public void updateButton() {
        System.out.println("Update Participant Method Called");

        Connection conn = SQLServerDatabase.getDatabase().getConnection();
        try {
            String value1 = tfEmail.getText();
            String value2 = tfName.getText();
            Date value3 = Date.valueOf(String.valueOf(tfDateYear.getText())  + "-" + String.valueOf(tfDateMonth.getText()) + "-" + String.valueOf(tfDateDay.getText()));
            String value4 = tfGender.getText();
            String value5 = tfAddress.getText();
            String value6 = tfPostalCode.getText();
            String value7 = tfCity.getText();
            String value8 = tfCountry.getText();
            System.out.println("Put Text in");

            String query = "UPDATE Participant SET EmailAddress= '" + value1 +
            "',Name= '" + value2 + 
            "',Birthdate= '" + value3 + 
            "',Gender= '" + value4 + 
            "',Address= '" + value5 + 
            "',PostalCode= '" + value6 + 
            "',City= '" + value7 + 
            "',Country= '" + value8 + 

            "' where EmailAddress='" + value1 + "' ";
            System.out.println("query");

            PreparedStatement stm = conn.prepareStatement(query);

            System.out.println("execute");
            stm.execute();
            clear();
            showParticipant();

        } catch (Exception e) {

            System.out.println(e);
        }
    }

    public void setText() {
        System.out.println("Set Text Method Called");

        Participant selectedParticipant = tvParticipants.getSelectionModel().getSelectedItem();

        System.out.println("Selected Participant: " + selectedParticipant);

        tfEmail.setText(tvParticipants.getSelectionModel().getSelectedItem().getEmail());
        tfName.setText(tvParticipants.getSelectionModel().getSelectedItem().getName());

        tfDateYear.setText(tvParticipants.getSelectionModel().getSelectedItem().getBirthdate());
        tfDateMonth.setText(tvParticipants.getSelectionModel().getSelectedItem().getBirthdate());
        tfDateDay.setText(tvParticipants.getSelectionModel().getSelectedItem().getBirthdate());

        String[] dateParts = selectedParticipant.getBirthdate().split("-");
            if (dateParts.length == 3) {
                tfDateYear.setText(dateParts[0]);
                tfDateMonth.setText(dateParts[1]);
                tfDateDay.setText(dateParts[2]);
            }

        tfGender.setText(tvParticipants.getSelectionModel().getSelectedItem().getGender());
        tfAddress.setText(tvParticipants.getSelectionModel().getSelectedItem().getAddress());
        tfPostalCode.setText(tvParticipants.getSelectionModel().getSelectedItem().getPostalCode());
        tfCity.setText(tvParticipants.getSelectionModel().getSelectedItem().getCity());
        tfCountry.setText(tvParticipants.getSelectionModel().getSelectedItem().getCountry());

        System.out.println("Set Text in");
    }


    public void clear() {
        System.out.println("Clear");
        
        tfEmail.clear();
        tfName.clear();
        tfDateYear.clear();
        tfDateMonth.clear();
        tfDateDay.clear();
        tfGender.clear();
        tfAddress.clear();
        tfPostalCode.clear();
        tfCity.clear();
        tfCountry.clear();
    }

}
