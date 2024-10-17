package com.task.robotcol.Model;

import javafx.event.Event;

public class RobotEventArgs extends Event {
    private final int index;
    private Integer curTask = null;
    private Integer curTaskLength = null;

    public RobotEventArgs(int index, Integer curTask, Integer curTaskLength) {
        super(Event.ANY);
        this.index = index;
        this.curTask = curTask;
        this.curTaskLength = curTaskLength;
    }

    public RobotEventArgs(int index) {
        super(Event.ANY);
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public int getCurTask() {
        return curTask;
    }

    public int getCurTaskLength() {
        return curTaskLength;
    }
}