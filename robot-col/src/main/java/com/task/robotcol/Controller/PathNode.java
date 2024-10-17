package com.task.robotcol.Controller;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class PathNode extends Circle {
    private Integer robotNum;
    private Integer taskNum;
    private Integer taskLength;

    public PathNode(double v, Paint paint, Integer robotNum, Integer taskNum, Integer taskLength) {
        super(v, paint);
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
            if(!strForNode.isEmpty()) strForNode += " ";
            strForNode += ("T" + taskNum + " " + taskLength + " length");
        }
        text.setText(strForNode);
    }

    public void setTaskLength(Integer taskLength) {
        this.taskLength = taskLength;
    }

    public void setTaskNum(Integer taskNum) {
        this.taskNum = taskNum;
    }

    public void setRobotNum(Integer robotNum) {
        this.robotNum = robotNum;
    }
}
