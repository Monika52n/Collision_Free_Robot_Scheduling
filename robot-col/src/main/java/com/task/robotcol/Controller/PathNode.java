package com.task.robotcol.Controller;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import static javafx.scene.paint.Color.LIGHTBLUE;

public class PathNode extends StackPane {
    private Integer robotNum;
    private final Integer taskNum;
    private Integer taskLength;
    private final Circle circle;

    public PathNode(Integer robotNum, Integer taskNum, Integer taskLength) {
        circle = new Circle(50, LIGHTBLUE);
        circle.setStroke(Color.BLACK);
        circle.setStrokeWidth(5);
        this.robotNum = robotNum;
        this.taskNum = taskNum;
        this.taskLength = taskLength;
        refreshText();
    }

    public void refreshText() {
        Text text = new Text();
        String strForNode = "";
        if (robotNum!=null) {
            strForNode = "R" + robotNum;
        }
        if(taskLength!=null) {
            if(!strForNode.isEmpty()) strForNode += ", ";
            strForNode += ("T" + taskNum + ": " + taskLength + " length");
        }
        text.setText(strForNode);
        this.getChildren().setAll(circle, text);
    }

    public void setTaskLength(Integer taskLength) {
        this.taskLength = taskLength;
    }

    public void setRobotNum(Integer robotNum) {
        this.robotNum = robotNum;
    }
    public Integer getRobotNum() {
        return robotNum;
    }

    public Circle getCircle() {
        return circle;
    }
}
