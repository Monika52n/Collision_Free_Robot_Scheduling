package com.task.robotcol;

import com.task.robotcol.Controller.RobotColController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    private RobotColController controller;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        RobotColController controller = new RobotColController();
        loader.setController(controller);

        VBox vbox = controller.createUI();
        Scene scene = new Scene(vbox, 600, 300);
        stage.setScene(scene);
        stage.setTitle("Draw Circles with Button");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}