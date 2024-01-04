package com.presentation.CourseDetail;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CourseDetailUI extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        // Load the FXML file for the course details layout
        Parent root = FXMLLoader.load(getClass().getResource("../CourseDetails/layoutCourseDetails.fxml"));

        // Create and set the scene
        Scene scene = new Scene(root);

        // Set the scene to the stage and display it
        stage.setScene(scene);
        stage.setTitle("Course Details"); // Optional: Set a title for the window
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
