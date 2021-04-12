package workflow;

import com.alibaba.fastjson.JSONObject;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Desc: TestRuntimeService
 * Created by mskj-mohao on 2021/3/24 10:57 AM
 * Copr: © 2020 MSKJ.All rights reserved.
 **/

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/banana/applicationContext.xml")
public class TestRuntimeService {
    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private HistoryService historyService;

    /**
     * 流程启动
     */
    @Test
    public void startProcessInstanceTest(){
        long count = runtimeService.createProcessInstanceQuery().processDefinitionKey("bananaDiagram").count();
        Map<String,Object> variables = new HashMap<>();
        //变量放置在实例全局变量中
        variables.put("CandidateUser","tester");
        variables.put("candidateGroup","group1,group2");
        runtimeService.startProcessInstanceByKey("bananaDiagram",variables);
        count = runtimeService.createProcessInstanceQuery().processDefinitionKey("bananaDiagram").count()-count;
        Assert.isTrue(count == 1);
    }
    /**
     * 流程查询
     */
    @Test
    public void queryProcessInstanceTest(){
        List<ProcessInstance> list = runtimeService.createProcessInstanceQuery().processDefinitionKey("bananaDiagram").list();
        System.out.println(JSONObject.toJSONString(list));
    }


    /**
     * 流程实例删除
     */
    @Test
    public void deleteProcessInstanceTest() {
        ProcessInstance instance = runtimeService.createProcessInstanceQuery().processDefinitionKey("bananaDiagram").singleResult();
        if (instance != null) {
            long count = historyService.createHistoricProcessInstanceQuery().count();
            runtimeService.deleteProcessInstance(instance.getId(),"删除测试"+System.currentTimeMillis());
            count = historyService.createHistoricProcessInstanceQuery().count() - count;
            Assert.isTrue(count == 1);
        } else {
            long count = historyService.createHistoricProcessInstanceQuery().count();
            Map<String,Object> variables = new HashMap<>();
            variables.put("CandidateUser","tester");
            instance = runtimeService.startProcessInstanceByKey("bananaDiagram",variables);
            runtimeService.deleteProcessInstance(instance.getId(),"删除测试"+System.currentTimeMillis());
            count = historyService.createHistoricProcessInstanceQuery().count() - count;
            Assert.isTrue(count == 1);
        }
    }

    /**
     * 流程实例挂起与激活
     */

}
