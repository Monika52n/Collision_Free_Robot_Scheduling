package com.task.robotcol.Model;

public class RobotTask {
    private int length;
    private int remainingLength;
    private final int index;

    public RobotTask(int index, int length) {
        this.length = length;
        this.remainingLength = length;
        this.index = index;
    }

    public RobotTask(int index) {
        this.length = 1;
        this.remainingLength = length;
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public boolean isFinished() {
        return remainingLength<=0;
    }

    public int getRemainingLength() {
        return remainingLength;
    }

    public int decrementRemainingLength() {
        --remainingLength;
        return remainingLength;
    }
}
