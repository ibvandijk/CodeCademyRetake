package com.presentation.Registration;

import com.domain.Registration;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import com.datastorage.SQLServerDatabase;
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

public class RegistrationController implements Initializable{
    
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
    private TableColumn<Registration, String> colEmail;

    @FXML
    private TableColumn<Registration, String> colCourseName;

    @FXML
    private TableColumn<Registration, String> colDate;

    @FXML
    private TextField tfDateYear;

    @FXML
    private TextField tfDateMonth;

    @FXML
    private TextField tfDateDay;

    @FXML
    private TextField tfEmail;

    @FXML
    private TextField tfCourseName;
    
    @FXML
    private TableView<Registration> tvRegistrations;

    boolean isClicked = false;

    ObservableList<Registration> registrations;

    @FXML
    void handleButtonAction(ActionEvent event) throws IOException {

        
        if (event.getSource() == btnInsert) {
            insertButton();
        } else if (event.getSource() == btnDelete) {
            deleteButton();
        } 
        if (event.getSource() == btnClear) {
            isClicked = true;
            clear();    
        } 
        if(event.getSource() == btnUpdate && !isClicked){
            isClicked = true;
            setText();
        } else {
            updateButton();
            isClicked = false;
        }

        if(event.getSource() == btnBack) {
            backToHome();
        }

    }

    public void initialize(URL url, ResourceBundle rb) {
        showRegistration();
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

    public ObservableList<Registration> getRegistrations() {
        registrations = FXCollections.observableArrayList();
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
        System.out.println("return");
        System.out.println(registrations.size());

        return registrations;
    }

    public void showRegistration() {
        ObservableList<Registration> registrationsList = getRegistrations();

        colEmail.setCellValueFactory(new PropertyValueFactory<Registration, String>("Email"));
        colCourseName.setCellValueFactory(new PropertyValueFactory<Registration, String>("CourseName"));
        colDate.setCellValueFactory(new PropertyValueFactory<Registration, String>("Date"));

        tvRegistrations.setItems(registrationsList);

    }

    private void insertButton() {
        Connection conn = SQLServerDatabase.getDatabase().getConnection();
        String query = "INSERT INTO Registration" + " VALUES (?, ?, ?)";


        try {
            String date = String.valueOf(tfDateYear.getText()) + "-" + String.valueOf(tfDateMonth.getText()) + "-" + String.valueOf(tfDateDay.getText());
            PreparedStatement stm = conn.prepareStatement(query);

            stm.setString(1, tfEmail.getText());
            stm.setString(2, tfCourseName.getText());
            stm.setDate(3, Date.valueOf(date));

            stm.execute();

            clear();

            showRegistration();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void deleteButton() {
        Connection conn = SQLServerDatabase.getDatabase().getConnection();
        String query = "DELETE FROM Registration where EmailAddress = ?";

        try {
            PreparedStatement stm = conn.prepareStatement(query);
            stm.setString(1, tvRegistrations.getSelectionModel().getSelectedItem().getEmail());
        
            stm.execute();
            showRegistration();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void setText() {
        Registration selectedRegistration = tvRegistrations.getSelectionModel().getSelectedItem();
    
        if (selectedRegistration != null) {
            tfEmail.setText(selectedRegistration.getEmail());
            tfCourseName.setText(selectedRegistration.getCourseName());
    
            // Parse the date string and set each part separately
            String[] dateParts = selectedRegistration.getDate().split("-");
            if (dateParts.length == 3) {
                tfDateYear.setText(dateParts[0]);
                tfDateMonth.setText(dateParts[1]);
                tfDateDay.setText(dateParts[2]);
            }
    
            System.out.println("Set Text in");
        }
    }
    

    public void updateButton() {
        
        Connection conn = SQLServerDatabase.getDatabase().getConnection();

             try {
                String value1 = tfEmail.getText();
                String value2 = String.valueOf(tfCourseName.getText());
                Date value3 = Date.valueOf(String.valueOf(tfDateYear.getText())  + "-" + String.valueOf(tfDateMonth.getText()) + "-" + String.valueOf(tfDateDay.getText()));
                System.out.println("Put Text in");

                String query = "UPDATE Registration SET EmailAddress= '" + value1 + "' ,CourseName= '" + value2 + "' ,Date= '" +
                        value3 + "' where EmailAddress= '" + value1 + "' ";
                         System.out.println("query");
 
                PreparedStatement stm = conn.prepareStatement(query);

                
                stm.execute();
                System.out.println("execute");
                clear();
                showRegistration();

        } catch (Exception e) {

            System.out.println(e);

        }
    }

    public void clear() {
        tfEmail.clear();
        tfCourseName.clear();
        tfDateYear.clear();
        tfDateMonth.clear();
        tfDateDay.clear();
    }
}
