package com.task.robotcol.Model;
import javafx.event.Event;
import javafx.event.EventHandler;

import java.util.ArrayList;

public class Robot {
    private int index;
    private final int startIndex;
    private ArrayList<Integer> nodes;
    private ArrayList<RobotTask> tasks;
    private boolean startFromFirst;
    private final RobotTaskManager robotTaskManager = new RobotTaskManager();
    private boolean isFinished = false;
    private int stepsAndTasksTime = 0;
    public EventHandler<RobotEventArgs> gameAdvanced;
    public Robot(int startIndex) {
        this.index = startIndex;
        this.startIndex = startIndex;
    }

    public int getIndex() {
        return  index;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public void setTasks(ArrayList<RobotTask> tasks) {
        this.tasks = tasks;
    }

    public void setStartFromFirst(Boolean startFromFirst) {
        this.startFromFirst = startFromFirst;
    }

    public ArrayList<RobotTask> getTasks() {
        return tasks;
    }

    public boolean makeAMove() {
        if(isFinished || tasks.isEmpty()) return false;
        RobotTask currTask = null;
        if(startFromFirst) {
            if(!tasks.getFirst().isFinished() && index!=tasks.getFirst().getIndex()) {
                //meg nem ert elore a robot
                index--;
            } else {
                //elore ert a robot
                currTask = robotTaskManager.getTaskOnIndex(index, tasks);
                if(currTask!=null && currTask.getRemainingLength()>0) {
                    //nem fejezte be meg taskot amin all
                    currTask.decrementRemainingLength();
                } else {
                    index++;
                }
            }
        } else {
            if(!tasks.getLast().isFinished() && index != (tasks.getLast().getIndex())) {
                index++;
            } else {
                currTask = robotTaskManager.getTaskOnIndex(index, tasks);
                if(currTask!=null && currTask.getRemainingLength()>0) {
                    //nem fejezte be meg taskot amin all
                    currTask.decrementRemainingLength();
                } else {
                    index--;
                }
            }
        }
        stepsAndTasksTime++;
        setFinished();
        if(currTask==null) {
            gameAdvanced.handle(new RobotEventArgs(index));
        } else {
            gameAdvanced.handle(new RobotEventArgs(
                    index,
                    Integer.valueOf(currTask.getIndex()),
                    Integer.valueOf(currTask.getRemainingLength())));
        }
        return true;
    }

    private void setFinished() {
        isFinished = tasks.stream().allMatch(RobotTask::isFinished);
    }

    public boolean isFinished() {
        return isFinished;
    }

    public int getStepsAndTasksTime() {
        return stepsAndTasksTime;
    }
}
