package com.task.robotcol.Controller;

import com.task.robotcol.Model.KRobotPathGraph;
import com.task.robotcol.Model.Robot;
import com.task.robotcol.Model.RobotEventArgs;
import com.task.robotcol.Model.RobotTask;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
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
    private final List<PathNode> pathNodes = new ArrayList<>();
    private final int columns = 10;
    private final double circleRadius = 50.0;

    private void setRobotsAndTasksInModel() {
        //TODO: bekerni a robotok indexet, taskok hosszat, helyet, es a graf hosszat
        ArrayList<Integer> robotIndexes = new ArrayList<Integer>();
        Map<Integer, Integer> tasksWithLength = new HashMap<>();
        robotIndexes.add(Integer.valueOf(5));
        tasksWithLength.put(Integer.valueOf(9),Integer.valueOf(1));
        tasksWithLength.put(Integer.valueOf(7),Integer.valueOf(1));
        tasksWithLength.put(Integer.valueOf(3),Integer.valueOf(1));
        kRobotPathGraph = new KRobotPathGraph(50, tasksWithLength, robotIndexes);
        kRobotPathGraph.setStepHandlers(new EventHandler<RobotEventArgs>() {
            @Override
            public void handle(RobotEventArgs robotEventArgs) {
                if(robotEventArgs.getPrevIndex()!=robotEventArgs.getNextIndex()) {
                    PathNode prevNode = pathNodes.get(robotEventArgs.getPrevIndex());
                    PathNode nextNode = pathNodes.get(robotEventArgs.getNextIndex());
                    nextNode.setRobotNum(prevNode.getRobotNum());
                    prevNode.setRobotNum(null);
                    nextNode.refreshText();
                    prevNode.refreshText();
                }
                if(robotEventArgs.getCurTaskIndex()!=null && robotEventArgs.getCurTaskLength()!=null) {
                    PathNode taskNode = pathNodes.get(robotEventArgs.getCurTaskIndex());
                    taskNode.setTaskLength(robotEventArgs.getCurTaskLength());
                    taskNode.refreshText();
                }
            }
        });
    }

    public Scene createUI() {
        setRobotsAndTasksInModel();
        Pane pane = new Pane();

        for (int i = 0; i < kRobotPathGraph.getPathLength(); i++) {
            PathNode pathNode = new PathNode(
                    kRobotPathGraph.getRobotNum(i),
                    kRobotPathGraph.getTaskNum(i),
                    kRobotPathGraph.getTaskLength(i));

            int x = (i % columns) * 120;
            int y = (i / columns) * 120;
            pathNode.setLayoutX(x);
            pathNode.setLayoutY(y);

            Line line = connectNodes(
                    x,
                    y,
                    ((i+1) % columns) * 120,
                    ((i + 1) / columns) * 120);
            pane.getChildren().add(line);
            pane.getChildren().add(pathNode);
            pathNodes.add(pathNode);
        }

        forwardButton = new Button("Step forward");
        forwardButton.setOnAction(event -> onHelloButtonClick());

        VBox vbox = new VBox(10, pane, forwardButton);
        return new Scene(vbox, 600, 400);
    }

    @FXML
    protected void onHelloButtonClick() {
        kRobotPathGraph.makeAMove();
    }

    private Line connectNodes(double startX, double startY, double endX, double endY) {
        Line line = new Line(startX+circleRadius, startY+circleRadius, endX+circleRadius, endY+circleRadius);
        line.setStroke(Color.BLACK);
        line.setStrokeWidth(2);
        return line;
    }
}