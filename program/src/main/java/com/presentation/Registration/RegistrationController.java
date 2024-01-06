package com.presentation.Registration;

import com.domain.Registration;
import com.presentation.Validation.InputValidation;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import com.datastorage.CourseDAO;
import com.datastorage.ParticipantDAO;
import com.datastorage.RegistrationDAO;
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
            if (validateInput()) {
                insertButton();
            }
        }   
        else if (event.getSource() == btnDelete) {
            deleteButton();
        } 
        if (event.getSource() == btnClear) {
            isClicked = true;
            clear();    
        } 
        if(event.getSource() == btnBack) {
            backToHome();
        }
        if(event.getSource() == btnUpdate && !isClicked){
            isClicked = true;
            setText();
        } else {
            if (validateInput()) {
                updateButton();
                isClicked = false;
            }
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

    public void showRegistration() {
        ObservableList<Registration> registrationsList = RegistrationDAO.getRegistrations();

        colEmail.setCellValueFactory(new PropertyValueFactory<Registration, String>("Email"));
        colCourseName.setCellValueFactory(new PropertyValueFactory<Registration, String>("CourseName"));
        colDate.setCellValueFactory(new PropertyValueFactory<Registration, String>("Date"));

        tvRegistrations.setItems(registrationsList);
    }

    private void insertButton() {
        String selectedEmail = tfEmails.getValue();
        String selectedCourse = tfCourses.getValue();
        String date = String.valueOf(tfDateYear.getText()) + "-" + String.valueOf(tfDateMonth.getText()) + "-" + String.valueOf(tfDateDay.getText());
        RegistrationDAO.insertRegistration(selectedEmail, selectedCourse, date);
        clear();
        showRegistration();
    }
    
    public void deleteButton() {
        Registration selectedRegistration = tvRegistrations.getSelectionModel().getSelectedItem();
        System.out.println("Delete Participant Method Called");

        String selectedEmail = selectedRegistration.getEmail();
        String selectedCourse = selectedRegistration.getCourseName();
        String selectedDate = selectedRegistration.getDate();

        RegistrationDAO.deleteRegistration(selectedEmail, selectedCourse, selectedDate);

        showRegistration();
    }
    
    public void updateButton() {
        String selectedEmail = tfEmails.getValue();
        String selectedCourse = tfCourses.getValue();
        String date = String.valueOf(tfDateYear.getText()) + "-" + String.valueOf(tfDateMonth.getText()) + "-" + String.valueOf(tfDateDay.getText());
        RegistrationDAO.updateRegistration(selectedEmail, selectedCourse, date);
        clear();
        showRegistration();
    }
    
    private void initializeComboBox() {
        ObservableList<String> emailsList = ParticipantDAO.getEmails();
        tfEmails.setItems(emailsList);

        ObservableList<String> coursesList = CourseDAO.getCourseNames();
        tfCourses.setItems(coursesList);
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
    
    public void clear() {
        tfEmails.setValue(null);
        tfCourses.setValue(null);
        tfDateYear.clear();
        tfDateMonth.clear();
        tfDateDay.clear();
    }

    private boolean validateInput() {

        // Check if the date is correct
        int day = Integer.parseInt(tfDateDay.getText());
        int month = Integer.parseInt(tfDateMonth.getText());
        int year = Integer.parseInt(tfDateYear.getText());
        
        if (!InputValidation.isValidDate(day, month, year)) {
            InputValidation.showError("Invalid date. \nPlease enter a valid date.");
                return false;
        }

        return true;
    }
}
