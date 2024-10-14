package com.task.robotcol;

import com.task.robotcol.Model.KRobotPathGraph;
import com.task.robotcol.Model.Robot;
import com.task.robotcol.Model.RobotTask;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.ArrayList;

public class HelloController {
    @FXML
    private Label welcomeText;

    ArrayList<RobotTask> tasks = new ArrayList<RobotTask>();
    ArrayList<Robot> robots = new ArrayList<>();
    KRobotPathGraph kRobotPathGraph;
    @FXML
    public void initialize() {
        // Populate tasks list with some RobotTask instances
        tasks.add(new RobotTask(1));
        tasks.add(new RobotTask(7));
        tasks.add(new RobotTask(3));

        // Populate robots list with some Robot instances (assuming Robot is a class)
        robots.add(new Robot(5));

        // Initialize KRobotPathGraph with the populated lists
        kRobotPathGraph = new KRobotPathGraph(10, tasks, robots);
    }

    @FXML
    protected void onHelloButtonClick() {
        String str = "Robot is on index ";
        kRobotPathGraph.makeAMove();
        Robot robot = kRobotPathGraph.getFirstRobot();
        str += robot.getIndex();
        str += ".\n Tasks:\n";
        for(RobotTask task : robot.getTasks()) {
            str = str + task.getIndex() + ": " + (task.isFinished() ? "finished\n" : "not finished\n");
        }
        welcomeText.setText(str);
    }
}