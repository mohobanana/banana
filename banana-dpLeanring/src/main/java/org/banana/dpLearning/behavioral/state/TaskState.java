package org.banana.dpLearning.behavioral.state;

public interface TaskState {
    public void complete();
    public void close();
    public String getState();
}
