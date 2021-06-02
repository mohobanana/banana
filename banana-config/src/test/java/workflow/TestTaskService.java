package workflow;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.identity.Group;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Desc: TestTaskService
 * Created by mskj-mohao on 2021/3/24 2:48 PM
 * Copr: © 2020 MSKJ.All rights reserved.
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/banana/applicationContext.xml")
public class TestTaskService {
    @Autowired
    private TaskService taskService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private IdentityService identityService;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private ManagementService managementService;

    /**
     * 任务查询
     */
    @Test
    public void queryTaskTest() {
        Task task = taskService.createTaskQuery().taskId("67506").singleResult();
        taskService.addCandidateUser("67506","ab1,ab2");
        // 获取全局变量
        Map<String, Object> a = taskService.getVariables(task.getId());
        // 获取局部变量
        Map<String, Object> b = taskService.getVariablesLocal(task.getId());
        // 获取全局变量 a=c
        Map<String, Object> c = runtimeService.getVariables(task.getProcessInstanceId());
        System.currentTimeMillis();
    }
    /**
     * 任务签收
     */
    @Test
    public void chaimTaskTest() {
        Task task = taskService.createTaskQuery().taskCandidateUser("tester").singleResult();
        taskService.claim(task.getId(),"tester");
    }
    /**
     * 任务签收回退
     */
    @Test
    public void chaimTaskCallBackTest() {
        Task task = taskService.createTaskQuery().taskAssignee("tester").singleResult();
        taskService.setAssignee(task.getId(),null);
    }
    /**
     * 任务完成
     */
    @Test
    public void completeTaskTest() {
        Task task = taskService.createTaskQuery().taskAssignee("tester").singleResult();
        taskService.complete(task.getId());
    }

    @Test
    public  void qry(){
        List<IdentityLink> identityLinksForTask = taskService.getIdentityLinksForTask("62506");
        List<Group> candidate = identityService.createGroupQuery().groupType("candidate").list();
    }



    @Test
    public void taskqueryTest(){
        String pid = "75005";
        List<Task> list = taskService.createTaskQuery().processInstanceId(pid).list();
        List<HistoricTaskInstance> hisList = historyService.createHistoricTaskInstanceQuery().processInstanceId(pid).finished().orderByTaskCreateTime().desc().list();
        List<HistoricVariableInstance> varList=historyService.createHistoricVariableInstanceQuery().taskId(hisList.get(0).getId()).list();
        List<HistoricActivityInstance> actList = historyService.createHistoricActivityInstanceQuery().processInstanceId(pid).finished().orderByHistoricActivityInstanceStartTime().desc().list();
        Map<String, Object> c = runtimeService.getVariables(pid);

        String pid2 = "80001";
        List<Task> list2 = taskService.createTaskQuery().processInstanceId(pid2).list();
        List<HistoricTaskInstance> hisList2 = historyService.createHistoricTaskInstanceQuery().processInstanceId(pid2).finished().orderByTaskCreateTime().desc().list();
        List<HistoricVariableInstance> varList2=historyService.createHistoricVariableInstanceQuery().taskId(hisList2.get(0).getId()).list();
        List<HistoricActivityInstance> actList2 = historyService.createHistoricActivityInstanceQuery().processInstanceId(pid2).finished().orderByHistoricActivityInstanceStartTime().desc().list();
        Map<String, Object> c2 = runtimeService.getVariables(pid2);

        String pid3 = "97501";
        List<Task> list3 = taskService.createTaskQuery().processInstanceId(pid3).list();
        List<HistoricTaskInstance> hisList3 = historyService.createHistoricTaskInstanceQuery().processInstanceId(pid3).finished().orderByTaskCreateTime().desc().list();
        List<HistoricVariableInstance> varList3=historyService.createHistoricVariableInstanceQuery().taskId(hisList3.get(0).getId()).list();
        List<HistoricActivityInstance> actList3 = historyService.createHistoricActivityInstanceQuery().processInstanceId(pid3).finished().orderByHistoricActivityInstanceStartTime().desc().list();
        Map<String, Object> c3 = runtimeService.getVariables(pid3);



        System.out.println(list);
    }

    @Test
    public void taskRecallTest(){
        String pid3 = "97501";
//        taskService.complete("97530");
        List<Task> list1 = taskService.createTaskQuery().processInstanceId(pid3).list();
        List<HistoricTaskInstance> hisList1 = historyService.createHistoricTaskInstanceQuery().processInstanceId(pid3).finished().orderByTaskCreateTime().desc().list();
        taskService.deleteTask("97520");
        taskService.deleteTask("97525");
        List<Task> list2 = taskService.createTaskQuery().processInstanceId(pid3).list();
        List<HistoricTaskInstance> hisList2 = historyService.createHistoricTaskInstanceQuery().processInstanceId(pid3).finished().orderByTaskCreateTime().desc().list();
    }
    @Test
    public void taskCreateTest(){

//        String s = "a.b";
//        String[] a = s.split("[.]");
//        System.out.println(a);
        Map<String, Object> vars = new HashMap<>(); //参数
        vars.put("assignee","haha");
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("myProcess_2",vars);
        List<Task> taskList = taskService.createTaskQuery().processInstanceId(processInstance.getId()).list();
//        List<String> candidateList = new ArrayList<>();
//        candidateList.add("a1,a2,a3");
//        candidateList.add("b1,b2");
//        vars = new HashMap<>();
//        vars.put("candidateList",candidateList);
//        taskService.setAssignee(taskList.get(0).getId(),"haha");
        taskService.delegateTask(taskList.get(0).getId(),"xixi");
//        taskService.delegateTask(taskList.get(0).getId(),"hehe");
//        taskService.delegateTask(taskList.get(0).getId(),"heihei");
        taskService.resolveTask(taskList.get(0).getId());
//        taskService.complete(taskList.get(0).getId(),vars);
//        taskList = taskService.createTaskQuery().processInstanceId(processInstance.getId()).list();
//        vars = new HashMap<>();
//        vars.put("action","R");
//        taskService.complete(taskList.get(0).getId(),vars);
//        vars.put("candidater", "e,f,g");
//        taskService.complete(task.getId(),vars);


    }
}
