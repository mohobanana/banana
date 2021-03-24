package workflow;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import java.io.InputStream;
import java.util.List;

/**
 * Desc: TestRepository
 * Created by mskj-mohao on 2021/3/22 5:56 PM
 * Copr: © 2020 MSKJ.All rights reserved.
 **/

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/banana/applicationContext.xml")
public class TestRepositoryService {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    @Autowired
    private RepositoryService repositoryService; //管理流程定义
    @Autowired
    private RuntimeService runtimeService;

    /**
     * 流程部署
     */
    @Test
    public void classPathDeploymentTest() {
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
        long count = processDefinitionQuery.processDefinitionKey("bananaDiagram").count();
        //定义资源文件的classPath
        String bpmnClassPath = "META-INF/banana/diagram/bananaDiagram.bpmn";
        //创建部署构建器
        DeploymentBuilder deploymentBuilder = repositoryService.createDeployment();
        //添加资源
        deploymentBuilder.addClasspathResource(bpmnClassPath);
        //执行部署
        deploymentBuilder.deploy();
        //验证部署是否成功
        processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
        count = processDefinitionQuery.processDefinitionKey("bananaDiagram").count() - count;
        Assert.isTrue(count == 1);
    }

    @Test
    public void inputStreamFromAbsolutePathTest() {
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
        long count = processDefinitionQuery.processDefinitionKey("bananaDiagram").count();
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("META-INF/banana/diagram/bananaDiagram.bpmn");
        //读取filePath文件作为一个输入流
        repositoryService.createDeployment().addInputStream("bananaDiagram.bpmn", in).deploy();
        //验证部署是否成功
        processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
        count = processDefinitionQuery.processDefinitionKey("bananaDiagram").count() - count;
        Assert.isTrue(count == 1);
    }

    /**
     * 流程查询
     */
    @Test
    public void queryProcessDefinitionTest() {
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
        List<ProcessDefinition> list = processDefinitionQuery.list();
        long count = processDefinitionQuery.count();
    }

    /**
     * 流程删除
     */
    @Test
    public void deleteProcessDefinitionTest() {
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
        List<ProcessDefinition> list = processDefinitionQuery.processDefinitionKey("bananaDiagram").list();
        long count = list.size();
        if (count > 0) {
            repositoryService.deleteDeployment(list.get(0).getDeploymentId());
            count = count - processDefinitionQuery.count();
            Assert.isTrue(count == 1);
        } else {
            Assert.isTrue(count == 0);
        }
    }

    /**
     * 流程挂起与激活
     */
    @Test
    public void suspensionProcessDefinitionTest() {
        boolean thrown = false;
        try {
            repositoryService.suspendProcessDefinitionByKey("bananaDiagram");
        } catch (ActivitiException e){
            logger.info(e.getMessage());
        }
        try {
            runtimeService.startProcessInstanceByKey("bananaDiagram");
        } catch (ActivitiException e){
            thrown = true;
        } finally {
            Assert.isTrue(thrown);
        }
        repositoryService.activateProcessDefinitionByKey("bananaDiagram");
        ProcessInstanceQuery processInstanceQuery = runtimeService.createProcessInstanceQuery();
        long count = processInstanceQuery.active().count();
        ProcessInstance bananaDiagram = runtimeService.startProcessInstanceByKey("bananaDiagram");
        count = processInstanceQuery.active().count() - count;
        Assert.isTrue(count == 1);
        runtimeService.deleteProcessInstance(bananaDiagram.getId(),"删除测试");
    }
}
