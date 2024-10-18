package com.task.robotcol.Model;

import javafx.event.Event;

public class RobotEventArgs extends Event {
    private final int nextIndex;
    private final int prevIndex;
    private Integer curTaskIndex = null;
    private Integer curTaskLength = null;

    public RobotEventArgs(int nextIndex, int prevIndex, int curTaskIndex, int curTaskLength) {
        super(Event.ANY);
        this.nextIndex = nextIndex;
        this.prevIndex = prevIndex;
        this.curTaskIndex = Integer.valueOf(curTaskIndex);
        this.curTaskLength = Integer.valueOf(curTaskLength);
    }

    public RobotEventArgs(int nextIndex, int prevIndex) {
        super(Event.ANY);
        this.nextIndex = nextIndex;
        this.prevIndex = prevIndex;
    }

    public Integer getCurTaskIndex() {
        return curTaskIndex;
    }

    public Integer getCurTaskLength() {
        return curTaskLength;
    }

    public int getNextIndex() {
        return nextIndex;
    }

    public int getPrevIndex() {
        return prevIndex;
    }
}