package com.task.robotcol.Model;

import java.util.ArrayList;

public class KRobotPathGraph {
    Integer pathLength;
    ArrayList<Integer> taskPlacements;
    ArrayList<Integer> robotPlacements;

    KRobotPathGraph(Integer pathLength, ArrayList<Integer> taskPlacements, ArrayList<Integer> robotPlacements) {
        this.pathLength = pathLength;
        this.taskPlacements = taskPlacements;
        this.robotPlacements = robotPlacements;
    }

}
