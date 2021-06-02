package org.banana.demo.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Desc: ExecutionListener
 * Created by mskj-mohao on 2021/5/12 5:12 PM
 * Copr: © 2020 MSKJ.All rights reserved.
 **/

@Component
public class MyExecutionListener implements ExecutionListener, TaskListener {
    @Override
    public void notify(DelegateExecution execution){
        String eventName = execution.getEventName();
        //start
        if (ExecutionListener.EVENTNAME_START.equals(eventName)) {
            System.out.println("start========="+execution.getCurrentActivityId());
        } else if (ExecutionListener.EVENTNAME_END.equals(eventName)) {
            System.out.println("end==========="+execution.getCurrentActivityId());
        } else if (ExecutionListener.EVENTNAME_TAKE.equals(eventName)) {
            System.out.println("take=========="+execution.getCurrentActivityId());
        }
    }

    @Override
    //实现TaskListener中的方法
    public void notify(DelegateTask delegateTask) {
        String eventName = delegateTask.getEventName();
        if (TaskListener.EVENTNAME_CREATE.endsWith(eventName)) {
            System.out.println("create============="+delegateTask.getTaskDefinitionKey());
        } else if (TaskListener.EVENTNAME_ASSIGNMENT.endsWith(eventName)) {
            System.out.println("assignment========="+delegateTask.getTaskDefinitionKey());
        } else if (TaskListener.EVENTNAME_COMPLETE.endsWith(eventName)) {
            System.out.println("complete==========="+delegateTask.getTaskDefinitionKey());
        } else if (TaskListener.EVENTNAME_DELETE.endsWith(eventName)) {
            System.out.println("delete============="+delegateTask.getTaskDefinitionKey());
        }
    }
}