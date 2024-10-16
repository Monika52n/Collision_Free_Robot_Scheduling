package com.task.robotcol;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) {
        HBox hbox = new HBox(10); // HBox with 10 pixels spacing between circles

        int numberOfCircles = 10; // number of circles to draw

        for (int i = 0; i < numberOfCircles; i++) {
            StackPane circleWithText = new StackPane();

            // Create a circle
            Circle circle = new Circle(50, Color.LIGHTBLUE); // Circle with radius 50

            // Add text inside the circle
            Text text = new Text();
            if (i == 0) {
                text.setText("R");
            } else if (i == 2) {
                text.setText("T" + i);
            }

            // Add the circle and text to the StackPane
            circleWithText.getChildren().addAll(circle, text);

            // Add StackPane (circle + text) to HBox
            hbox.getChildren().add(circleWithText);
        }

        Scene scene = new Scene(hbox, 600, 200);
        stage.setTitle("Draw Circles with Text");


        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}