package com.presentation.Participant;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import com.datastorage.SQLServerDatabase;
import com.domain.Participant;
import javafx.collections.FXCollections;
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
        if (event.getSource() == btnDelete) {
            deleteButton();
        } 
        if (event.getSource() == btnClear) {
            isClicked = true;
            clear();    
        } 
        if(event.getSource() == btnBack) {
            backToHome();
        }
        if (event.getSource() == btnUpdate && !isClicked) {
            isClicked = true;
            setText();
        } else if (event.getSource() == btnUpdate && isClicked) {
            updateButton();
            isClicked = false;
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

    public ObservableList<Participant> getParticpants() {
        participants = FXCollections.observableArrayList();
        Connection conn = SQLServerDatabase.getDatabase().getConnection();
        String query = "SELECT * FROM Participant;";
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {

                Participant participant = new Participant(
                        rs.getString("EmailAddress"),
                        rs.getString("Name"),
                        rs.getDate("BirthDate").toString(),
                        rs.getString("Gender"),
                        rs.getString("Address"),
                        rs.getString("PostalCode"),
                        rs.getString("City"),
                        rs.getString("Country"));

                participants.add(participant);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("return");
        System.out.println(participants.size());

        return participants;
    }

    public void showParticipant() {
        ObservableList<Participant> participantList = getParticpants();

        colEmail.setCellValueFactory(new PropertyValueFactory<Participant, String>("Email"));
        colName.setCellValueFactory(new PropertyValueFactory<Participant, String>("Name"));

        colDate.setCellValueFactory(new PropertyValueFactory<Participant, String>("Birthdate"));

        colGender.setCellValueFactory(new PropertyValueFactory<Participant, String>("Gender"));
        colAddress.setCellValueFactory(new PropertyValueFactory<Participant, String>("Address"));
        colPostalCode.setCellValueFactory(new PropertyValueFactory<Participant, String>("PostalCode"));
        colCity.setCellValueFactory(new PropertyValueFactory<Participant, String>("City"));
        colCountry.setCellValueFactory(new PropertyValueFactory<Participant, String>("Country"));

        tvParticipants.setItems(participantList);

    }

    private void insertButton() {
        System.out.println("Insert Button Clicked");

        Connection conn = SQLServerDatabase.getDatabase().getConnection();
        String query = "INSERT INTO PARTICIPANT VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    
        try {
            String Birthdate = String.valueOf(tfDateYear.getText()) + "-" + String.valueOf(tfDateMonth.getText()) + "-" + String.valueOf(tfDateDay.getText());
   
            PreparedStatement stm = conn.prepareStatement(query);
    
            stm.setString(1, tfEmail.getText());
            stm.setString(2, tfName.getText());
            stm.setDate(3, Date.valueOf(Birthdate));
            stm.setString(4, tfGender.getText());
            stm.setString(5, tfAddress.getText());
            stm.setString(6, tfPostalCode.getText());
            stm.setString(7, tfCity.getText());
            stm.setString(8, tfCountry.getText());
    
            stm.execute();
    
            clear();
    
            showParticipant();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    

    public void deleteButton() {
        Connection conn = SQLServerDatabase.getDatabase().getConnection();
        String query = "DELETE FROM Participant where EmailAddress = ?";

        try {
            PreparedStatement stm = conn.prepareStatement(query);
            stm.setString(1, tvParticipants.getSelectionModel().getSelectedItem().getEmail());

            stm.execute();
            showParticipant();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void setText() {
        Participant selectedParticipant = tvParticipants.getSelectionModel().getSelectedItem();

        tfEmail.setText(tvParticipants.getSelectionModel().getSelectedItem().getEmail());
        tfName.setText(tvParticipants.getSelectionModel().getSelectedItem().getName());

        tfDateYear.setText(tvParticipants.getSelectionModel().getSelectedItem().getBirthdate());
        tfDateMonth.setText(tvParticipants.getSelectionModel().getSelectedItem().getBirthdate());
        tfDateDay.setText(tvParticipants.getSelectionModel().getSelectedItem().getBirthdate());

        // Parse the date string and set each part separately
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


    


    public void updateButton() {

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

            String query = "UPDATE Participant SET EmailAddress= '" + value1 + "',Name= '" + value2 + "',Birthdate= '" +
                    value3 + "',Gender= '" + value4 + "',Address= '" + value5 + "',PostalCode= '" + value6 + "',City= '"
                    + value7 + "',Country= '" + value8 + "' where EmailAddress='" + value1 + "' ";
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

    public void clear() {
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
