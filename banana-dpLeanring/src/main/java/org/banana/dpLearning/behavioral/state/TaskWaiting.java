package org.banana.dpLearning.behavioral.state;

public class TaskWaiting implements TaskState{
    @Override
    public void complete() {
        System.out.println("task is completed");
    }

    @Override
    public void close() {
        System.out.println("task is closed");
    }

    @Override
    public String getState() {
        return "waiting";
    }
}
