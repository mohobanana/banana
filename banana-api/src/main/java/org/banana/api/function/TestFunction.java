package org.banana.api.function;

import com.alibaba.fastjson.JSON;
import com.mysql.cj.xdevapi.JsonValue;
import org.banana.demo.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class TestFunction {

    @Autowired
    private TestService testService;

    @RequestMapping("/test")
    @ResponseBody
    public Object testFunction(String msg){
        testService.testMethod(msg);
        return JSON.toJSON(msg);
    }

}
