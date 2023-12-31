package com.presentation.CourseDetail;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

import com.datastorage.SQLServerDatabase;
import com.domain.Course; // Your Course class
import com.domain.Module; // Your Module class
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CourseDetailController {

    @FXML
    private Label lblCourseName, lblCourseNumber, lblSubject, lblIntroductionText, lblDifficulty;
    @FXML
    private TableView<Module> tvModules;
    @FXML
    private TableColumn<Module, String> colModuleName, colModuleDescription;

    private Course selectedCourse; // Set this from CourseController

    public void initialize() {
        configureTableView();
    }

    public void setSelectedCourse(Course course) {
        this.selectedCourse = course;
        displayCourseDetails();
        loadModulesForCourse();
    }

    private void configureTableView() {
        colModuleName.setCellValueFactory(new PropertyValueFactory<>("moduleTitle"));
        colModuleDescription.setCellValueFactory(new PropertyValueFactory<>("moduleDescription"));
    }

    private void displayCourseDetails() {
        lblCourseName.setText(selectedCourse.getCourseName());
        lblCourseNumber.setText(String.valueOf(selectedCourse.getCourseNumber()));
        lblSubject.setText(selectedCourse.getSubject());
        lblIntroductionText.setText(selectedCourse.getIntroductionText());
        lblDifficulty.setText(selectedCourse.getDifficulty());
    }

    private void loadModulesForCourse() {
    ObservableList<Module> modules = FXCollections.observableArrayList();
    
    // Replace with your method to establish a database connection
    Connection conn = SQLServerDatabase.getDatabase().getConnection();
    
    String query = "SELECT * FROM Module WHERE courseName = ?";

    try (PreparedStatement pstmt = conn.prepareStatement(query)) {
        pstmt.setString(1, selectedCourse.getCourseName());

        System.out.println(selectedCourse.getCourseName());

        try (ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Module module = new Module(
                    rs.getString("ModuleTitle"),
                    rs.getString("ModuleVersion"),
                    rs.getString("ModuleDescription"),
                    rs.getString("ContactPersonName"),
                    rs.getString("ContactPersonEmail"),
                    rs.getString("courseName")
                );
                modules.add(module);
            }
            
            
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    System.out.println(modules);
    tvModules.setItems(modules);
}


    @FXML
    private void handleBackAction() {
        // Logic to go back
    }
}
