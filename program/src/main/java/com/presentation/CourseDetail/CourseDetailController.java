package com.presentation.CourseDetail;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

import com.datastorage.SQLServerDatabase;
import com.domain.Course;
import com.domain.Module; 
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
    @FXML
    private Label lblModuleTitle, lblModuleVersion, lblModuleDescription, lblContactPersonName, lblContactPersonEmail, lblAverageProgress;
    @FXML
    private Label lblStudentsCompleted;

    private Course selectedCourse;

    public void initialize() {
        configureTableView();
        setupModuleSelection();
    }

    private void setupModuleSelection() {
        tvModules.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                updateModuleDetails(newSelection);
            }
        });
    }

    private void updateModuleDetails(Module module) {
        lblModuleTitle.setText(module.getModuleTitle());
        lblModuleVersion.setText(module.getVersion());
        lblModuleDescription.setText(module.getModuleDescription());
        lblContactPersonName.setText(module.getContactPersonName());
        lblContactPersonEmail.setText(module.getContactPersonEmail());
        lblAverageProgress.setText(String.format("%.2f%%", module.getAverageProgress()));
    }

    public void setSelectedCourse(Course course) {
        this.selectedCourse = course;
        displayCourseDetails();
        loadModulesForCourse();
        int studentsCompleted = getStudentsCompleted(course);
        lblStudentsCompleted.setText(String.valueOf(studentsCompleted));
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

    private int getStudentsCompleted(Course course) {
        int studentsCompleted = 0;
        String courseName = course.getCourseName();

        String query = "SELECT COUNT(DISTINCT Registration.EmailAddress) AS AantalCursisten " +
                       "FROM Registration " +
                       "WHERE Registration.CourseName = ?";

        try (Connection conn = SQLServerDatabase.getDatabase().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, courseName);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    studentsCompleted = rs.getInt("AantalCursisten");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return studentsCompleted;
    }
}
