package org.banana.demo.service.impl;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowNode;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.engine.*;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.banana.common.utils.RandomUtil;
import org.banana.demo.service.ActivitiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Desc: ActivitiServiceImpl
 * Created by mskj-mohao on 2021/3/22 4:52 PM
 * Copr: © 2020 MSKJ.All rights reserved.
 * @author mohao
 */
@Service
public class ActivitiServiceImpl implements ActivitiService {

    @Autowired
    private RepositoryService repositoryService; //管理流程定义
    @Autowired
    private RuntimeService runtimeService; //执行管理，包括启动、推进、删除流程实例等操作
    @Autowired
    private TaskService taskService; //任务管理，查询任务信息
    @Autowired
    private HistoryService historyService; //历史管理(执行完的数据的管理)
    @Autowired
    private IdentityService identityService; //组织机构管理

    @Override
    public void genTask(String userId) {
    }

    @Override
    public void redirectTask(String procInstId, String targetTaskKey, String assignee, Map<String, Object> variables) {
        List<Task> currentTaskList = taskService.createTaskQuery().processInstanceId(procInstId).list();
        Task task = currentTaskList.get(0);
        String processDefinitionId = task.getProcessInstanceId();
        String sourceTaskKey = task.getTaskDefinitionKey();
        ProcessDefinition processDefinition;
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionId);
        FlowNode sourceFlowNode = (FlowNode) bpmnModel.getFlowElement(sourceTaskKey);//activitiId = taskKey
        FlowNode targetFlowNode = (FlowNode) bpmnModel.getFlowElement(targetTaskKey);
        //保存原活动方向
        List<SequenceFlow> originSequenceFlow = new ArrayList<>();
        originSequenceFlow.addAll(sourceFlowNode.getOutgoingFlows());
        //清空当前流向
        sourceFlowNode.getOutgoingFlows().clear();
        //创建新流向
        List<SequenceFlow> newSequenceFlow = new ArrayList<>();
        SequenceFlow sequenceFlow = new SequenceFlow();
        sequenceFlow.setId(RandomUtil.getUUid());
        sequenceFlow.setSourceFlowElement(sourceFlowNode);
        sequenceFlow.setTargetFlowElement(targetFlowNode);
        newSequenceFlow.add(sequenceFlow);
        sourceFlowNode.setOutgoingFlows(newSequenceFlow);
        //完成任务
        for (Task tempTask : currentTaskList) {
            if(taskService.createTaskQuery().taskId(tempTask.getId()).count()>0){
                taskService.complete(tempTask.getId(),variables);
            }
        }
        //还原原活动方向
        sourceFlowNode.setOutgoingFlows(originSequenceFlow);
    }
}
