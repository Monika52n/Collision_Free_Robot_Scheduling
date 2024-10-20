package com.task.robotcol;

import com.task.robotcol.Controller.RobotColController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class RobotColApplication extends Application {
    private RobotColController controller;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        RobotColController controller = new RobotColController();
        loader.setController(controller);
        stage.setScene(controller.createUI());
        stage.setTitle("Robots and Tasks");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}