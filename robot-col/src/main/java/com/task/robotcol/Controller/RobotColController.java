package com.task.robotcol.Controller;

import com.task.robotcol.Model.KRobotPathGraph;
import com.task.robotcol.Model.Robot;
import com.task.robotcol.Model.RobotEventArgs;
import com.task.robotcol.Model.RobotTask;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static javafx.scene.paint.Color.*;

public class RobotColController {
    KRobotPathGraph kRobotPathGraph;
    private final List<PathNode> pathNodes = new ArrayList<>();
    private final int columns = 10;
    private final double circleRadius = 50.0;
    private final double gaps = 120.0;

    private void setRobotsAndTasksInModel() {
        //TODO: bekerni a robotok indexet, taskok hosszat, helyet, es a graf hosszat
        //TODO: inkabb bekerni vertexek szama,taskok szama, robotok szama, es random generator
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
        return new Scene(createScrollPane(), 600, 400);
    }

    private Pane createPathNodesAndLines() {
        Pane pane = new Pane();
        for (int i = 0; i < kRobotPathGraph.getPathLength(); i++) {
            if(i!=kRobotPathGraph.getPathLength()-1) {
                pane.getChildren().add((new Edge(i, columns, gaps, circleRadius)).getLine());
            }
            pane.getChildren().add(createPathNodeOnIndex(i));
        }
        return pane;
    }

    private PathNode createPathNodeOnIndex(int i) {
        PathNode pathNode = new PathNode(
            kRobotPathGraph.getRobotNum(i),
            kRobotPathGraph.getTaskNum(i),
            kRobotPathGraph.getTaskLength(i),
            i,
            columns,
            gaps,
            circleRadius);
        pathNodes.add(pathNode);
        return pathNode;
    }
    private ScrollPane createScrollPane() {
        ScrollPane scrollPane = new ScrollPane(createVBox());
        scrollPane.setFitToWidth(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        return  scrollPane;
    }

    private VBox createVBox() {
        VBox vbox = new VBox(10, createPathNodesAndLines(), createForwardButton());
        vbox.setPadding(new Insets(15,30,15,30));
        BackgroundFill backgroundFill = new BackgroundFill(WHITE, CornerRadii.EMPTY, Insets.EMPTY);
        vbox.setBackground(new Background(backgroundFill));
        return vbox;
    }

    private Button createForwardButton() {
        Button forwardButton = new Button("Step forward");
        forwardButton.setOnAction(event -> onHelloButtonClick());
        return forwardButton;
    }

    @FXML
    private void onHelloButtonClick() {
        kRobotPathGraph.makeAMove();
    }
}