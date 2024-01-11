package com.logic;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.ObservableList;
import com.datastorage.CourseDAO;
import com.domain.Course;
import com.domain.Module; 

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

    public void setSelectedCourse(Course course) {
        this.selectedCourse = course;
        displayCourseDetails();
    
        ObservableList<Module> modules = CourseDAO.getModulesForCourse(selectedCourse.getCourseName());
        tvModules.setItems(modules);
    
        int studentsCompleted = CourseDAO.getStudentsCompletedForCourse(selectedCourse.getCourseName());
        lblStudentsCompleted.setText(String.valueOf(studentsCompleted));
    }
    
    private void configureTableView() {
        System.out.println("Fill up Course Details table");

        colModuleName.setCellValueFactory(new PropertyValueFactory<>("moduleTitle"));
        colModuleDescription.setCellValueFactory(new PropertyValueFactory<>("moduleDescription"));
    }

    private void displayCourseDetails() {
        System.out.println("Fill up Course Details side");

        lblCourseName.setText(selectedCourse.getCourseName());
        lblCourseNumber.setText(String.valueOf(selectedCourse.getCourseNumber()));
        lblSubject.setText(selectedCourse.getSubject());
        lblIntroductionText.setText(selectedCourse.getIntroductionText());
        lblDifficulty.setText(selectedCourse.getDifficulty());
    }

    private void updateModuleDetails(Module module) {
        System.out.println("Fill up Module Details table");

        lblModuleTitle.setText(module.getModuleTitle());
        lblModuleVersion.setText(module.getVersion());
        lblModuleDescription.setText(module.getModuleDescription());
        lblContactPersonName.setText(module.getContactPersonName());
        lblContactPersonEmail.setText(module.getContactPersonEmail());
        lblAverageProgress.setText(String.format("%.2f%%", module.getAverageProgress()));
    }
}