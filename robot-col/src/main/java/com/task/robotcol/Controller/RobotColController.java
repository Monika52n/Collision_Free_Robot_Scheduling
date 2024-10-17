package com.task.robotcol.Controller;

import com.task.robotcol.Model.KRobotPathGraph;
import com.task.robotcol.Model.Robot;
import com.task.robotcol.Model.RobotEventArgs;
import com.task.robotcol.Model.RobotTask;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static javafx.scene.paint.Color.LIGHTBLUE;

public class RobotColController {
    KRobotPathGraph kRobotPathGraph;
    @FXML
    private Button forwardButton;
    private List<PathNode> circles = new ArrayList<>();

    public VBox createUI() {
        ArrayList<Integer> robotIndexes = new ArrayList<Integer>();
        Map<Integer, Integer> tasksWithLength = new HashMap<>();
        robotIndexes.add(Integer.valueOf(5));
        tasksWithLength.put(Integer.valueOf(1),Integer.valueOf(1));
        tasksWithLength.put(Integer.valueOf(7),Integer.valueOf(1));
        tasksWithLength.put(Integer.valueOf(3),Integer.valueOf(1));
        kRobotPathGraph = new KRobotPathGraph(10, tasksWithLength, robotIndexes);
        kRobotPathGraph.setStepHandlers(new EventHandler<RobotEventArgs>() {
            @Override
            public void handle(RobotEventArgs robotEventArgs) {
                circles.get()
            }
        });

        HBox hbox = new HBox(10);
        for (int i = 0; i < kRobotPathGraph.getPathLength(); i++) {
            StackPane circleWithText = new StackPane();
            PathNode circle = new PathNode(
                    50,
                    LIGHTBLUE,
                    robotIndexes.indexOf(Integer.valueOf(i))==-1 ? null : Integer.valueOf(robotIndexes.indexOf(Integer.valueOf(i))),
                    Integer.valueOf(i),
                    tasksWithLength.get(Integer.valueOf(i)));

            circleWithText.getChildren().addAll(circle, "");
            hbox.getChildren().add(circleWithText);
            circles.add(circle);
        }

        forwardButton = new Button("Step forward");
        forwardButton.setOnAction(event -> onHelloButtonClick());

        // Add components to VBox
        VBox vbox = new VBox(10, hbox, forwardButton);
        return vbox;
    }

    @FXML
    protected void onHelloButtonClick() {
        kRobotPathGraph.makeAMove();
    }
}