package org.banana.demo.service.impl;

import org.activiti.engine.*;
import org.banana.demo.service.ActivitiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
