package org.banana.dpLearning.behavioral.state;

public class Client {
    public static void main(String[] args) {
        TaskHandler taskHandler = new TaskHandler();
        taskHandler.setTaskState("waiting");
        taskHandler.complete();
        taskHandler.close();
        taskHandler.setTaskState("closed");
        taskHandler.complete();
        taskHandler.close();
    }
}
