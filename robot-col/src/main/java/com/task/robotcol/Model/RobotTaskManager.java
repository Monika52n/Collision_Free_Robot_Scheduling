package com.task.robotcol.Model;

import java.util.ArrayList;
import java.util.List;

public class RobotTaskManager {
    public RobotTask getTaskOnIndex(int index, List<RobotTask> tasks) {
        return tasks.stream()
                .filter(task -> task.getIndex()==index)
                .findFirst()
                .orElse(null);
    }

    public List<RobotTask> sortRobotTaskByIndex(List<RobotTask> tasks) {
        tasks.sort((task1, task2) -> Integer.compare(task1.getIndex(), task2.getIndex()));
        return tasks;
    }
}
