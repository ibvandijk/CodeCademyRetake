package com.presentation.Certificate;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

import com.datastorage.SQLServerDatabase;
import com.domain.Certificate;

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

public class CertificateController implements Initializable {

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
    private TableColumn<Certificate, String> colEmailAddress;

    @FXML
    private TableColumn<Certificate, String> colGrade;

    @FXML
    private TableColumn<Certificate, String> colEmployeeName;

    @FXML
    private TableColumn<Certificate, String> colCourseName;

    @FXML
    private TextField tfEmailAddress;

    @FXML
    private TextField tfGrade;

    @FXML
    private TextField tfEmployeeName;

    @FXML
    private TextField tfCourseName;

    @FXML
    private TableView<Certificate> tvCertificates;

    boolean isClicked = false;

    ObservableList<Certificate> certificates;

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
        if(event.getSource() == btnBack) {
            backToHome();
        }
        if (event.getSource() == btnUpdate && !isClicked) {
            isClicked = true;
            setText();
        } else {
            updateButton();
            isClicked = false;
        }
    }

    public void initialize(URL url, ResourceBundle rb) {
        showCertificate();
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

    public ObservableList<Certificate> getCertificates() {
        certificates = FXCollections.observableArrayList();
        Connection conn = SQLServerDatabase.getDatabase().getConnection();
        String query = "SELECT * FROM CERTIFICATE;";
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                Certificate certificate = new Certificate(
                    rs.getString("EmailAddress"), 
                    rs.getString("Grade"),
                    rs.getString("EmployeeName"), 
                    rs.getString("CourseName"));

                certificates.add(certificate);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("return");
        System.out.println(certificates.size());

        return certificates;
    }

    public void showCertificate() {
        ObservableList<Certificate> certificateList = getCertificates();

        colEmailAddress.setCellValueFactory(new PropertyValueFactory<Certificate, String>("EmailAddress"));
        colGrade.setCellValueFactory(new PropertyValueFactory<Certificate, String>("Grade"));
        colEmployeeName.setCellValueFactory(new PropertyValueFactory<Certificate, String>("EmployeeName"));
        colCourseName.setCellValueFactory(new PropertyValueFactory<Certificate, String>("CourseName"));
        
        tvCertificates.setItems(certificateList);
    }

    private void insertButton() {
        Connection conn = SQLServerDatabase.getDatabase().getConnection();
        String query = "INSERT INTO CERTIFICATE" + " VALUES (?, ?, ?, ?)";

        try {
            PreparedStatement stm = conn.prepareStatement(query);

            stm.setString(1, tfEmailAddress.getText());
            stm.setString(2, tfGrade.getText());
            stm.setString(3, tfEmployeeName.getText());
            stm.setString(4, tfCourseName.getText());

            stm.execute();

            clear();

            showCertificate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void deleteButton() {
        Connection conn = SQLServerDatabase.getDatabase().getConnection();
        String query = "DELETE FROM Certificate where EmailAddress = ?";

        try {
            PreparedStatement stm = conn.prepareStatement(query);
            stm.setString(1, tvCertificates.getSelectionModel().getSelectedItem().getEmailAddress());

            stm.execute();
            showCertificate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void setText() {

        Certificate selectedCertificates = tvCertificates.getSelectionModel().getSelectedItem();
        
        if (selectedCertificates != null) {
        tfEmailAddress.setText(selectedCertificates.getEmailAddress());
        tfGrade.setText(selectedCertificates.getGrade());
        tfEmployeeName.setText(selectedCertificates.getEmployeeName());
        tfCourseName.setText(selectedCertificates.getCourseName());
        }

        System.out.println("Set Text in");
    }

    public void updateButton() {
        Connection conn = SQLServerDatabase.getDatabase().getConnection();
        try {
            String value1 = tfEmailAddress.getText();
            String value2 = tfGrade.getText();
            String value3 = tfEmployeeName.getText();
            String value4 = tfCourseName.getText();
            System.out.println("Put Text in");

            String query = "UPDATE Certificate SET EmailAddress= '" + value1 
                        + "',Grade= '" + value2    
                        + "',EmployeeName= '" + value3
                        + "',CourseName= '"+ value4
                        + "' where EmailAddress='" + value1 + "' ";
            System.out.println("query");

            PreparedStatement stm = conn.prepareStatement(query);

            System.out.println("execute");
            stm.execute();
            clear();
            showCertificate();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void clear() {
        tfEmailAddress.clear();
        tfGrade.clear();
        tfEmployeeName.clear();
        tfCourseName.clear();
    }
}
