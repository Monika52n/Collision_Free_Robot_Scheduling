package com.task.robotcol.Model;

import java.util.ArrayList;

public class Robot {
    private int index;
    private final int startIndex;
    private ArrayList<Integer> nodes;
    private ArrayList<RobotTask> tasks;
    private Boolean startFromFirst;
    private final RobotTaskManager robotTaskManager = new RobotTaskManager();

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

    public void makeAMove() {
        if(startFromFirst) {
            if(!tasks.getFirst().isFinished() && index!=tasks.getFirst().getIndex()) {
                //meg nem ert elore a robot
                index--;
            } else {
                //elore ert a robot
                RobotTask currTask = robotTaskManager.getTaskOnIndex(index, tasks);
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
                RobotTask currTask = robotTaskManager.getTaskOnIndex(index, tasks);
                if(currTask!=null && currTask.getRemainingLength()>0) {
                    //nem fejezte be meg taskot amin all
                    currTask.decrementRemainingLength();
                } else {
                    index--;
                }
            }
        }
    }
}
