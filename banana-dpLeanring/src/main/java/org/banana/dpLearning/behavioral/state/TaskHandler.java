package org.banana.dpLearning.behavioral.state;

import java.util.HashMap;
import java.util.Map;

public class TaskHandler implements TaskState{

    private TaskState taskState;
    private Map<String,TaskState> stateMap;

    public TaskHandler() {
        stateMap = new HashMap<>();
        TaskClosed taskClosed = new TaskClosed();
        TaskWaiting taskWaiting = new TaskWaiting();
        stateMap.put(taskClosed.getState(),taskClosed);
        stateMap.put(taskWaiting.getState(),taskWaiting);
    }

    public void setTaskState(String state) {
        TaskState taskState = stateMap.get(state);
        if(taskState==null){
            System.out.println("state not found");
        } else {
            this.taskState = taskState;
            System.out.println("task state set:"+state);
        }
    }

    @Override
    public void complete() {
        taskState.complete();
    }

    @Override
    public void close() {
        taskState.close();
    }

    @Override
    public String getState() {
        return null;
    }
}
