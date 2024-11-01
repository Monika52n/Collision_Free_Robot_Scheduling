package com.task.robotcol.Controller;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class RobotStage {
    private final RobotColController robotColController = new RobotColController();
    private final Stage stage;
    public RobotStage(Stage stage) {
        this.stage = stage;
    }

    private Scene createInputForm() {
        HBox formLayout = new HBox(10);
        formLayout.setAlignment(Pos.CENTER);

        Label verticesLabel = new Label("Vertices:");
        TextField verticesInput = new TextField();
        verticesInput.setPrefWidth(50);

        Label tasksLabel = new Label("Tasks:");
        TextField tasksInput = new TextField();
        tasksInput.setPrefWidth(50);

        Label robotsLabel = new Label("Robots:");
        TextField robotsInput = new TextField();
        robotsInput.setPrefWidth(50);

        Button generateButton = new Button("Generate");
        generateButton.setOnAction(event -> {
                stage.setScene(robotColController.initializeGraph(
                    Integer.parseInt(verticesInput.getText()),
                    Integer.parseInt(tasksInput.getText()),
                    Integer.parseInt(robotsInput.getText()),
                    false));
                stage.show();
            }
        );

        formLayout.getChildren().addAll(verticesLabel, verticesInput, tasksLabel, tasksInput, robotsLabel, robotsInput, generateButton);

        return new Scene(formLayout, 600, 400);
    }

    public Stage getStage() {
        return stage;
    }
}
