package org.banana.dpLearning.behavioral.state;

public class TaskClosed implements TaskState{
    @Override
    public void complete() {
        System.out.println("task has been closed");
    }

    @Override
    public void close() {
        System.out.println("task has been closed");
    }

    @Override
    public String getState() {
        return "closed";
    }
}
