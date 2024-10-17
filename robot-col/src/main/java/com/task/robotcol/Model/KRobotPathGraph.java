package com.task.robotcol.Model;

import java.util.ArrayList;
import java.util.Map;

import static java.lang.Math.abs;

public class KRobotPathGraph {
    private int pathLength;
    private ArrayList<RobotTask> tasks;
    private ArrayList<Robot> robots;
    RobotTaskManager robotTaskManager = new RobotTaskManager();
    public KRobotPathGraph(int pathLength, ArrayList<RobotTask> tasks, ArrayList<Robot> robots) {
        this.pathLength = pathLength;
        this.tasks = tasks;
        this.robots = robots;
        robotTaskManager.sortRobotTaskByIndex(tasks);
        devideTasksForRobots();
    }

    public KRobotPathGraph(int pathLength, Map<Integer, Integer> tasksWithLength, ArrayList<Integer> robotIndexes) {
        this.pathLength = pathLength;
        this.robots = new ArrayList<>();
        this.tasks = new ArrayList<>();
        for(Integer robotIndex : robotIndexes) {
            robots.add(new Robot(robotIndex));
        }
        for (Map.Entry<Integer, Integer> task : tasksWithLength.entrySet()) {
            tasks.add(new RobotTask(task.getKey(), task.getValue()));
        }
        robotTaskManager.sortRobotTaskByIndex(tasks);
        devideTasksForRobots();
    }

    public void devideTasksForRobots() {
        //ez ugye meg nem jo, mert elo robotnak odadja az osszes taskot
        robots.getFirst().setTasks(tasks);
        oneRobot(robots.getFirst());
    }

    public void makeAMove() {
        for(Robot robot : robots) {
            robot.makeAMove();
        }
    }

    private void oneRobot(Robot robot) {
        if(abs(robot.getStartIndex()-robot.getTasks().getLast().getIndex())<=
            abs(robot.getStartIndex()-robot.getTasks().getFirst().getIndex())) {
            //elso utemezes
            robot.setStartFromFirst(false);
        } else {
            //masodik utemezes
            robot.setStartFromFirst(true);
        }
    }

    public Robot getFirstRobot() {
        return robots.getFirst();
    }
}
