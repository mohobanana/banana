package org.banana.demo.service;

import org.activiti.engine.task.Task;

import java.util.Map;

/**
 * Desc: ActivitiService
 * Created by mskj-mohao on 2021/3/22 4:51 PM
 * Copr: © 2020 MSKJ.All rights reserved.
 **/
public interface ActivitiService {
    void genTask(String userId);
    void redirectTask(String procInstId, String targetTaskKey, String assignee, Map<String,Object> variables);

}
