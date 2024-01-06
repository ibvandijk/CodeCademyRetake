package com.presentation.GUI;

import javafx.application.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GUI extends Application {

    @Override
    public void start(Stage stage) throws Exception{

      Parent root = FXMLLoader.load(getClass().getResource("../GUI/LayoutGUI.fxml"));

      Scene scene = new Scene(root);

      stage.setScene(scene);

      stage.setTitle("Emma van den Broek (2200567), Ivan van Dijk (0000000), Davit Dashyan (0000000)");

      stage.show();
    }
}
