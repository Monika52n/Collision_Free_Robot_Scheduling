package com.task.robotcol.Model;

import com.task.robotcol.Util.FileLogger;
import javafx.event.EventHandler;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import static java.lang.Math.abs;

public class KRobotPathGraph {
    private final int pathLength;
    private final ArrayList<RobotTask> tasks;
    private final ArrayList<Robot> robots;
    private ArrayList<RobotTask>[] robotTasks;
    private final RobotTaskManager robotTaskManager = new RobotTaskManager();
    private final RobotManager robotManager = new RobotManager();
    public EventHandler<SimulationEndedEventArgs> simulationEnded;
    private final FileLogger fileLogger = new FileLogger("log.txt");
    private boolean isEnded = false;

    private void revise(int pathLength, Map<Integer, Integer> tasksWithLength, ArrayList<Integer> robotIndexes) {
        if(pathLength<1) throw new IllegalArgumentException("Path length must be greater than 1.");
        if(tasksWithLength==null || robotIndexes==null || tasksWithLength.isEmpty() || robotIndexes.isEmpty()) {
            throw  new IllegalArgumentException("Number of tasks and robots must be greater than 1.");
        }
    }

    public KRobotPathGraph(int pathLength, Map<Integer, Integer> tasksWithLength, ArrayList<Integer> robotIndexes) {
        revise(pathLength, tasksWithLength, robotIndexes);
        this.pathLength = pathLength;
        this.robots = new ArrayList<>();
        this.tasks = new ArrayList<>();
        for(int i = 0; i< robotIndexes.size(); i++) {
            int robotIndex = robotIndexes.get(i);
            if(robotIndex<0 || robotIndex>=pathLength) throw new IllegalArgumentException("Incorrect robot index!");
            robots.add(new Robot(robotIndex, i));
        }
        int taskNum = 0;
        for (Map.Entry<Integer, Integer> task : tasksWithLength.entrySet()) {
            int taskIndex = task.getKey();
            int taskLength = task.getValue();
            if(taskIndex<0 || taskIndex>=pathLength || taskLength<1) throw new IllegalArgumentException("Incorrect task index or length!");
            tasks.add(new RobotTask(taskIndex, taskNum, taskLength));
            taskNum++;
        }
        robotTaskManager.sortRobotTaskByIndex(tasks);
        devideTasksForRobots();
        for(Robot robot : robots) {
            oneRobot(robot);
        }
    }

    private void writeAssignmentsToFile(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            // Log initial task positions
            writer.write("Initial Task Positions:\n");
            for (RobotTask task : tasks) {
                writer.write("Task " + task.getTaskNum() + " is at index " + task.getIndex() + " with length " + task.getLength() + "\n");
            }
            writer.write("\nTask Assignments:\n");

            // Log task assignments
            for (int i = 0; i < robots.size(); i++) {
                Robot robot = robots.get(i);
                writer.write("Robot " + robot.getRobotNum() +  " Start position: " + robot.getStartIndex() +" has been assigned the following tasks:\n");
                for (RobotTask assignedTask : robot.getTasks()) {
                    writer.write("- Task " + assignedTask.getTaskNum() + " at index " + assignedTask.getIndex() + "\n");
                }
                writer.write("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
    private void devideTasksForRobots() {
        robots.sort(Comparator.comparingInt(Robot::getStartIndex));
        tasks.sort(Comparator.comparingInt(RobotTask::getIndex)); // Rendezés pozíció szerint
        ArrayList<RobotTask>[] robotTasks = new ArrayList[robots.size()];
        for (int i = 0; i < robots.size(); i++) {
            robotTasks[i] = new ArrayList<>();
        }

        // Intervallumok létrehozása: feladatokat egyenletesen osztjuk el
        int tasksPerRobot = (int) Math.ceil((double) tasks.size() / robots.size());
        for (int i = 0; i < tasks.size(); i++) {
            int robotIndex = i / tasksPerRobot;
            if (robotIndex < robots.size()) {
                robotTasks[robotIndex].add(tasks.get(i));
            }
        }

        // Átlagos feladathossz kiszámítása
        double avgTaskLength = tasks.stream().mapToInt(RobotTask::getLength).average().orElse(0);
        double egyensulyKuszob = (avgTaskLength * 0.2); // Egyensúlyi küszöb kiszámítása

        // Feladatok újraelosztása a küszöb alapján
        for (int i = 0; i < robots.size() - 1; i++) {
            int currentRobotLoad = robotTasks[i].stream().mapToInt(RobotTask::getLength).sum();
            int nextRobotLoad = robotTasks[i + 1].stream().mapToInt(RobotTask::getLength).sum();
            int loadDifference = Math.abs(currentRobotLoad - nextRobotLoad);

            // Ellenőrizzük, hogy a terhelés különbsége meghaladja-e a küszöböt
            if (loadDifference > egyensulyKuszob) {
                // Mozgassunk feladatot a nagyobb terhelésű robotról a kisebbre
                if (currentRobotLoad > nextRobotLoad) {
                    // Keresünk egy feladatot a jelenlegi robot legutolsó feladatából
                    RobotTask taskToMove = robotTasks[i].remove(robotTasks[i].size() - 1); // Legutolsó feladat
                    insertTaskInOrder(robotTasks[i + 1], taskToMove); // Beszúrás rendezett listába
                } else {
                    RobotTask taskToMove = robotTasks[i + 1].remove(0); // Legelső feladat
                    insertTaskInOrder(robotTasks[i], taskToMove); // Beszúrás rendezett listába
                }
            }
        }

        // Megpróbáljuk optimalizálni a feladatok elosztását úgy, hogy a terhelés kiegyenlítése mellett a szomszéd robotok közötti feladatátadást is támogassuk
        for (int i = 0; i < robots.size(); i++) {
            int currentRobotTotalTime1 = calculateTotalTime(robotTasks[i], robots.get(i).getStartIndex());

            if (i > 0) { // Bal oldali robot ellenőrzése
                int leftRobotTotalTime1 = calculateTotalTime(robotTasks[i - 1], robots.get(i-1).getStartIndex());

                // Utolsó feladat ideiglenes eltávolítása a bal oldali robotról és hozzáadása a jelenlegihez
                RobotTask taskToMove = robotTasks[i - 1].remove(robotTasks[i - 1].size() - 1);
                insertTaskInOrder(robotTasks[i], taskToMove);

                int currentRobotTotalTime2 = calculateTotalTime(robotTasks[i], robots.get(i).getStartIndex());
                int leftRobotTotalTime2 = calculateTotalTime(robotTasks[i - 1], robots.get(i-1).getStartIndex());

                // Csak akkor véglegesítjük a mozgatást, ha javít az időegyensúlyon
                if (!(currentRobotTotalTime1 + leftRobotTotalTime1 > currentRobotTotalTime2 + leftRobotTotalTime2)) {
                    // Ha nem javított, visszahelyezés az eredeti robotra
                    robotTasks[i].remove(taskToMove);
                    robotTasks[i - 1].add(taskToMove);
                }
            }

            currentRobotTotalTime1 = calculateTotalTime(robotTasks[i], robots.get(i).getStartIndex());
            if (i < robots.size() - 1) { // Jobb oldali robot ellenőrzése
                int rightRobotTotalTime1 = calculateTotalTime(robotTasks[i + 1], robots.get(i+1).getStartIndex());

                // Első feladat ideiglenes eltávolítása a jelenlegi robotról és hozzáadása a jobb oldalihoz
                RobotTask taskToMove = robotTasks[i].remove(0);
                insertTaskInOrder(robotTasks[i + 1], taskToMove);

                int currentRobotTotalTime2 = calculateTotalTime(robotTasks[i], robots.get(i).getStartIndex());
                int rightRobotTotalTime2 = calculateTotalTime(robotTasks[i + 1], robots.get(i+1).getStartIndex());

                // Csak akkor véglegesítjük a mozgatást, ha javít az időegyensúlyon
                if (!(currentRobotTotalTime1 + rightRobotTotalTime1 > currentRobotTotalTime2 + rightRobotTotalTime2)) {
                    // Ha nem javított, visszahelyezés az eredeti robotra
                    robotTasks[i + 1].remove(taskToMove);
                    robotTasks[i].add(0, taskToMove);
                }
            }
        }

        // Feladatok kiosztása a robotokhoz
        for (int i = 0; i < robots.size(); i++) {
            robots.get(i).setTasks(robotTasks[i]);
        }

        writeAssignmentsToFile("task_assignments.txt");
    }

        private void insertTaskInOrder(ArrayList<RobotTask> tasks, RobotTask task) {
        int index = 0;
        while (index < tasks.size() && tasks.get(index).getIndex() < task.getIndex()) {
            index++;
        }
        tasks.add(index, task);
    }
*/


    private void devideTasksForRobots() {
        robots.sort(Comparator.comparingInt(Robot::getStartIndex));
        tasks.sort(Comparator.comparingInt(RobotTask::getIndex)); // Rendezés pozíció szerint

        ArrayList<RobotTask>[] robotTasks = new ArrayList[robots.size()];
        for (int i = 0; i < robots.size(); i++) {
            robotTasks[i] = new ArrayList<>();
        }

        // Intervallumok létrehozása: feladatokat egyenletesen osztjuk el
        int tasksPerRobot = (int) Math.ceil((double) tasks.size() / robots.size());
        int robotIndex = 0;
        for (int i = 0; i < tasks.size(); i++) {
            if (robotTasks[robotIndex].size() < tasksPerRobot) {
                robotTasks[robotIndex].add(tasks.get(i));
            }
            else
            {
                robotIndex++;
                robotTasks[robotIndex].add(tasks.get(i));
            }
        }
        for (int i = 0; i < robots.size(); i++) {
            robots.get(i).setTasks(robotTasks[i]);
        }

        writeAssignmentsToFile("task_assignments_before.txt");
        for (int j = 0; j < tasks.size(); j++) {
            for (int i = 0; i < robots.size(); i++) {

                int currentRobotTotalTime1 = calculateTotalTime(robotTasks[i], robots.get(i).getStartIndex());

                if (i > 0) if(!robotTasks[i - 1].isEmpty()){ // Bal oldali robot ellenőrzése

                    int leftRobotTotalTime1 = calculateTotalTime(robotTasks[i - 1], robots.get(i-1).getStartIndex());

                    // Utolsó feladat ideiglenes eltávolítása a bal oldali robotról és hozzáadása a jelenlegihez
                    RobotTask taskToMove = robotTasks[i - 1].remove(robotTasks[i - 1].size() - 1);
                    //insertTaskInOrder(robotTasks[i], taskToMove);
                    robotTasks[i].add(0, taskToMove);
                    System.out.println("Task removed: " + taskToMove.getIndex());
                    System.out.println("Robot: " + robots.get(i).getRobotNum());
                    int currentRobotTotalTime2 = calculateTotalTime(robotTasks[i], robots.get(i).getStartIndex());
                    System.out.println("Robot: " + robots.get(i-1).getRobotNum());
                    int leftRobotTotalTime2 = calculateTotalTime(robotTasks[i - 1], robots.get(i-1).getStartIndex());

                    // Csak akkor véglegesítjük a mozgatást, ha javít az időegyensúlyon
                    if ( Math.max(currentRobotTotalTime1 , leftRobotTotalTime1) < Math.max(currentRobotTotalTime2, leftRobotTotalTime2) ) {
                        // Ha nem javított, visszahelyezés az eredeti robotra
                        robotTasks[i].remove(taskToMove);
                       // insertTaskInOrder(robotTasks[i-1], taskToMove);
                        robotTasks[i-1].add(taskToMove);
                    }
                }

                currentRobotTotalTime1 = calculateTotalTime(robotTasks[i], robots.get(i).getStartIndex());
                if (i < robots.size() - 1 ) if(!robotTasks[i + 1].isEmpty()){ // Jobb oldali robot ellenőrzése

                    int rightRobotTotalTime1 = calculateTotalTime(robotTasks[i + 1], robots.get(i+1).getStartIndex());

                    // Első feladat ideiglenes eltávolítása a jelenlegi robotról és hozzáadása a jobb oldalihoz
                    RobotTask taskToMove = robotTasks[i+1].remove(0);
                    System.out.println("Task removed: " + taskToMove.getIndex());
                   // insertTaskInOrder(robotTasks[i], taskToMove);
                    robotTasks[i].add(taskToMove);
                    System.out.println("Robot: " + robots.get(i).getRobotNum());
                    int currentRobotTotalTime2 = calculateTotalTime(robotTasks[i], robots.get(i).getStartIndex());
                    System.out.println("Robot: " + robots.get(i+1).getRobotNum());
                    int rightRobotTotalTime2 = calculateTotalTime(robotTasks[i + 1], robots.get(i+1).getStartIndex());

                    // Csak akkor véglegesítjük a mozgatást, ha javít az időegyensúlyon
                    if (Math.max(currentRobotTotalTime1, rightRobotTotalTime1) < Math.max(currentRobotTotalTime2, rightRobotTotalTime2)) {
                        // Ha nem javított, visszahelyezés az eredeti robotra
                        robotTasks[i].remove(taskToMove);
                       // insertTaskInOrder(robotTasks[i+1], taskToMove);
                        robotTasks[i+1].add(0, taskToMove);
                    }
                }

              /*for (int a = 0; a < robots.size(); a++) {
                    robots.get(a).setTasks(robotTasks[a]);
                }
                writeAssignmentsToFile("task_assignments"+j + "_" + i+".txt"); */
            }

        }

        // Feladatok kiosztása a robotokhoz
        for (int i = 0; i < robots.size(); i++) {
            robots.get(i).setTasks(robotTasks[i]);
        }

        writeAssignmentsToFile("task_assignments.txt");
    }


    private int calculateTotalTime(List<RobotTask> tasks, int startIndex) {
        int totalTime = 0;
        int currentPosition = startIndex;

        // Copy tasks to avoid modifying the original list
        List<RobotTask> sortedTasks = new ArrayList<>(tasks);
        // Sort tasks based on their index
        sortedTasks.sort(Comparator.comparingInt(RobotTask::getIndex));

        if (!sortedTasks.isEmpty()) {
            // Find the closest task
            RobotTask closestTask = sortedTasks.get(0);
            int leftDistance = Math.abs(currentPosition - closestTask.getIndex());

            // Check the rightmost task if available
            RobotTask rightMostTask = sortedTasks.get(sortedTasks.size() - 1);
            int rightDistance = Math.abs(currentPosition - rightMostTask.getIndex());

            if (leftDistance <= rightDistance) {
                // Move towards the closest task on the left
                totalTime = totalTime + leftDistance + closestTask.getLength() + Math.abs(closestTask.getIndex() - rightMostTask.getIndex());
                sortedTasks.remove(0); // Remove the task from the list
            } else {
                // Move towards the closest task on the right
                totalTime = totalTime + rightDistance + rightMostTask.getLength() + Math.abs(closestTask.getIndex() - rightMostTask.getIndex());
                sortedTasks.remove(sortedTasks.size() - 1); // Remove the task from the list
            }
        }
        for (int i=0; i<sortedTasks.size(); i++){
            totalTime += sortedTasks.get(i).getLength();
        }
    //    System.out.println("Minimális idő a taskok elvégzéséhez: " + totalTime);
        return totalTime;
    }

    //writeAssignmentsToFile("task_assignments.txt");


    public void makeAMove() {
        if(isEnded) return;
        boolean isFinished = true;
        int steps = 0;
        for(Robot robot : robots) {
            robot.makeAMove();
            if(!robot.isFinished()) isFinished = false;
            if(steps<robot.getStepsAndTasksTime()) {
                steps = robot.getStepsAndTasksTime();
            }
        }
        if(isFinished) {
            isEnded = true;
            fileLogger.log(pathLength, robots.size(), tasks.size(), steps, robotTaskManager.sumTaskLength(tasks));
            simulationEnded.handle(new SimulationEndedEventArgs(steps));
        }
    }

    private void oneRobot(Robot robot) {
        if(robot.getTasks()!=null && !robot.getTasks().isEmpty())
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
