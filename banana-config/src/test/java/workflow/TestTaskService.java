package workflow;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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

    /**
     * 任务查询
     */
    @Test
    public void queryTaskTest() {
        Task task = taskService.createTaskQuery().singleResult();
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

}
