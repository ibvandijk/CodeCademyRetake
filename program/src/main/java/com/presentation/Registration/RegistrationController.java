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
import javafx.scene.control.ComboBox;
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
    private ComboBox<String> tfEmails;

    @FXML
    private ComboBox<String> tfCourses;

    @FXML
    private TextField tfDateYear;

    @FXML
    private TextField tfDateMonth;

    @FXML
    private TextField tfDateDay;
    
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
        initializeComboBox();
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
        String query = "INSERT INTO Registration VALUES (?, ?, ?)";
        
        try {
            String selectedEmail = tfEmails.getValue();
            String selectedCourse = tfCourses.getValue();
            String date = String.valueOf(tfDateYear.getText()) + "-" + String.valueOf(tfDateMonth.getText()) + "-" + String.valueOf(tfDateDay.getText());
            PreparedStatement stm = conn.prepareStatement(query);
        
            stm.setString(1, selectedEmail);
            stm.setString(2, selectedCourse);
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
        Registration selectedRegistration = tvRegistrations.getSelectionModel().getSelectedItem();
    
        if (selectedRegistration != null) {
            String query = "DELETE FROM Registration WHERE EmailAddress = ? AND CourseName = ?";
    
            try {
                PreparedStatement stm = conn.prepareStatement(query);
                stm.setString(1, selectedRegistration.getEmail());
                stm.setString(2, selectedRegistration.getCourseName());
    
                stm.execute();
                showRegistration();
            } catch (Exception e) {
                System.out.println(e);
            }
        } else {
            System.out.println("No registration selected for deletion.");
        }
    }
    
    public void setText() {
        Registration selectedRegistration = tvRegistrations.getSelectionModel().getSelectedItem();
    
        if (selectedRegistration != null) {
            tfEmails.setValue(selectedRegistration.getEmail());
            tfCourses.setValue(selectedRegistration.getCourseName());
    
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
            String selectedEmail = tfEmails.getValue(); // Get the selected email
            String selectedCourse = tfCourses.getValue(); // Get the selected course
            Date value3 = Date.valueOf(String.valueOf(tfDateYear.getText()) + "-" + String.valueOf(tfDateMonth.getText()) + "-" + String.valueOf(tfDateDay.getText()));
    
            String query = "UPDATE Registration SET CourseName = ?, Date = ? WHERE EmailAddress = ?";
            PreparedStatement stm = conn.prepareStatement(query);
    
            // Set the new values for course name and date
            stm.setString(1, selectedCourse);
            stm.setDate(2, value3);
    
            // Specify the email address in the WHERE clause to identify the record
            stm.setString(3, selectedEmail);
    
            stm.execute();
            clear();
            showRegistration();
        } catch (Exception e) {
            System.out.println(e);
        }
    } 
    
    private void initializeComboBox() {
        ObservableList<String> emailsList = getEmails();
        tfEmails.setItems(emailsList);
    
        ObservableList<String> coursesList = getCourseNames();
        tfCourses.setItems(coursesList);
    }
    
    private ObservableList<String> getEmails() {
        ObservableList<String> emailsList = FXCollections.observableArrayList();
        Connection conn = SQLServerDatabase.getDatabase().getConnection();
        String query = "SELECT EmailAddress FROM Participant;";
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                emailsList.add(rs.getString("EmailAddress"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return emailsList;
    }

    private ObservableList<String> getCourseNames() {
        ObservableList<String> courseNamesList = FXCollections.observableArrayList();
        Connection conn = SQLServerDatabase.getDatabase().getConnection();
        String query = "SELECT CourseName FROM Course;";
        
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
    
            while (rs.next()) {
                courseNamesList.add(rs.getString("CourseName"));
            }
    
        } catch (Exception e) {
            e.printStackTrace();
        }
    
        return courseNamesList;
    }
    
    public void clear() {
        tfEmails.setValue(null);
        tfCourses.setValue(null);
        tfDateYear.clear();
        tfDateMonth.clear();
        tfDateDay.clear();
    }
}
