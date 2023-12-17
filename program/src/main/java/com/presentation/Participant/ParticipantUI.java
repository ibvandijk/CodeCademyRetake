package com.presentation.Participant;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ParticipantUI extends Application {

    @Override
    public void start(Stage stage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("../Participant/layoutParticipant.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);

        stage.show();


    }


}