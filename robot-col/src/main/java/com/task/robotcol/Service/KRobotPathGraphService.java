package com.task.robotcol.Service;

import com.task.robotcol.Model.KRobotPathGraph;
import com.task.robotcol.Model.Robot;
import com.task.robotcol.Model.RobotTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class KRobotPathGraphService {
    private KRobotPathGraph kRobotPathGraph;
    private ArrayList<Integer> robotIndexes = new ArrayList<Integer>();
    private Map<Integer, Integer> tasksWithLength = new HashMap<>();

    public KRobotPathGraphService() {
        ArrayList<RobotTask> tasks = new ArrayList<>();
        ArrayList<Robot> robots = new ArrayList<>();
        // Populate tasks list with some RobotTask instances
        tasks.add(new RobotTask(1));
        tasks.add(new RobotTask(7));
        tasks.add(new RobotTask(3));

        // Populate robots list with some Robot instances (assuming Robot is a class)
        robots.add(new Robot(5));

        // Initialize KRobotPathGraph with the populated lists
        kRobotPathGraph = new KRobotPathGraph(10, tasks, robots);
    }
}
