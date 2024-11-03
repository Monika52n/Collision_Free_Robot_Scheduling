package com.task.robotcol.Model;

import javafx.event.EventHandler;

import java.util.ArrayList;
import java.util.Map;

import static java.lang.Math.abs;

public class KRobotPathGraph {
    private final int pathLength;
    private final ArrayList<RobotTask> tasks;
    private final ArrayList<Robot> robots;
    private final RobotTaskManager robotTaskManager = new RobotTaskManager();
    private final RobotManager robotManager = new RobotManager();
    public EventHandler<SimulationEndedEventArgs> simulationEnded;
    public KRobotPathGraph(int pathLength, ArrayList<RobotTask> tasks, ArrayList<Robot> robots) {
        this.pathLength = pathLength;
        this.tasks = tasks;
        this.robots = robots;
        robotTaskManager.sortRobotTaskByIndex(tasks);
        devideTasksForRobots();
    }

    public KRobotPathGraph(int pathLength, Map<Integer, Integer> tasksWithLength, ArrayList<Integer> robotIndexes) {
        //TODO: ellenőrizni, hogy a pathLength-hez megfelelnak a robot és task indexek, 0-tól indexelek
        this.pathLength = pathLength;
        this.robots = new ArrayList<>();
        this.tasks = new ArrayList<>();
        for(int i = 0; i< robotIndexes.size(); i++) {
            robots.add(new Robot(robotIndexes.get(i), i));
        }
        int taskNum = 0;
        for (Map.Entry<Integer, Integer> task : tasksWithLength.entrySet()) {
            tasks.add(new RobotTask(task.getKey(), taskNum, task.getValue()));
            taskNum++;
        }
        robotTaskManager.sortRobotTaskByIndex(tasks);
        devideTasksForRobots();
    }

    private void devideTasksForRobots() {
        //TODO: ez ugye meg nem jo, mert elo robotnak odadja az osszes taskot
        robots.getFirst().setTasks(tasks);
        oneRobot(robots.getFirst());
    }

    public void makeAMove() {
        boolean isFinished = false;
        int steps = 0;
        for(Robot robot : robots) {
            robot.makeAMove();
            if(robot.isFinished()) isFinished = true;
            steps += robot.getStepsAndTasksTime();
        }
        if(isFinished) {
            simulationEnded.handle(new SimulationEndedEventArgs(steps));
        }
    }

    private void oneRobot(Robot robot) {
        robot.setStartFromFirst(abs(robot.getStartIndex() - robot.getTasks().getLast().getIndex()) > abs(robot.getStartIndex() - robot.getTasks().getFirst().getIndex()));
    }

    public void setStepHandlers(EventHandler<RobotEventArgs> stepHandler) {
        for(Robot robot : robots) {
            robot.gameAdvanced = stepHandler;
        }
    }

    public int getPathLength() {
        return pathLength;
    }

    public Integer getRobotNum(int index) {
        Robot robot = robotManager.getRobotOnIndex(index, robots);
        return robot!=null ? Integer.valueOf(robot.getRobotNum()) : null;
    }

    public Integer getTaskNum(int index) {
        RobotTask task = robotTaskManager.getTaskOnIndex(index, tasks);
        return task!=null ? Integer.valueOf(task.getTaskNum()) : null;
    }

    public Integer getTaskLength(int index) {
        RobotTask task = robotTaskManager.getTaskOnIndex(index, tasks);
        return task!=null ? Integer.valueOf(task.getRemainingLength()) : null;
    }
}
