package com.task.robotcol.Controller;

import com.task.robotcol.Model.KRobotPathGraph;
import com.task.robotcol.Model.Robot;
import com.task.robotcol.Model.RobotTask;
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
import java.util.Map;

import static javafx.scene.paint.Color.LIGHTBLUE;

public class RobotColController {
    KRobotPathGraph kRobotPathGraph;
    private ArrayList<Integer> robotIndexes = new ArrayList<Integer>();
    private Map<Integer, Integer> tasksWithLength = new HashMap<>();
    @FXML
    private Button forwardButton;

    public VBox createUI() {
        HBox hbox = new HBox(10);

        int numberOfCircles = 10;
        for (int i = 0; i < numberOfCircles; i++) {
            StackPane circleWithText = new StackPane();

            Circle circle = new Circle(50, LIGHTBLUE);

            Text text = new Text();
            if (i == 0) {
                text.setText("R");
            } else if (i == 2) {
                text.setText("T" + i);
            }

            circleWithText.getChildren().addAll(circle, text);
            hbox.getChildren().add(circleWithText);
        }

        forwardButton = new Button("Click Me");
        forwardButton.setOnAction(event -> onHelloButtonClick());

        // Add components to VBox
        VBox vbox = new VBox(10, hbox, forwardButton);
        return vbox;
    }
    public RobotColController() {
        robotIndexes.add(Integer.valueOf(5));
        tasksWithLength.put(Integer.valueOf(1),Integer.valueOf(1));
        tasksWithLength.put(Integer.valueOf(7),Integer.valueOf(1));
        tasksWithLength.put(Integer.valueOf(3),Integer.valueOf(1));
        kRobotPathGraph = new KRobotPathGraph(10, tasksWithLength, robotIndexes);
    }

    @FXML
    protected void onHelloButtonClick() {
        String str = "Robot is on index ";
        kRobotPathGraph.makeAMove();
        /*Robot robot = kRobotPathGraph.getFirstRobot();
        str += robot.getIndex();
        str += ".\n Tasks:\n";
        for(RobotTask task : robot.getTasks()) {
            str = str + task.getIndex() + ": " + (task.isFinished() ? "finished\n" : "not finished\n");
        }
        welcomeText.setText(str);*/
    }
}