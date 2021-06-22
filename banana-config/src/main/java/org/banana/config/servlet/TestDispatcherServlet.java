package org.banana.config.servlet;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.commons.beanutils.BeanUtils;
import org.banana.common.annotation.Function;
import org.banana.common.annotation.Functions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@WebServlet(name = "banana", urlPatterns = "/*")
public class TestDispatcherServlet extends HttpServlet {
    private static Logger logger = LoggerFactory.getLogger(TestDispatcherServlet.class);

    // springmvc 容器对象 key:请求地址 ,value 类
    private ConcurrentHashMap<String, String> urlClass = new ConcurrentHashMap<String, String>();

    // springmvc 容器对象 key:方法 ,value 类
    private ConcurrentHashMap<String, Object> methodFunctionMap = new ConcurrentHashMap<String, Object>();

    // springmvc 容器对象 key:方法 ,value 方法类
    private ConcurrentHashMap<String, Method> methodMap = new ConcurrentHashMap<String, Method>();

    // spring 上下文
    ApplicationContext ac = null;


    @Override
    public void init() throws ServletException {
        ServletContext sc = this.getServletContext();
        ac = WebApplicationContextUtils.getRequiredWebApplicationContext(sc);
        Map<String, Object> functions = ac.getBeansWithAnnotation(Functions.class);
        for (Object function : functions.values()) {
            Class functionClass = function.getClass();
            Method[] methods = functionClass.getDeclaredMethods();
            for(Method method:methods){
                if(method.getDeclaredAnnotation(Function.class)!=null){
                    methodMap.put(method.getName(),method);
                    methodFunctionMap.put(method.getName(),function);
                }
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        String contextPath = req.getContextPath();
        String methodName = uri.substring(uri.indexOf(contextPath)+contextPath.length()+1);
        PrintWriter writer = null;
        Method method = methodMap.get(methodName);
        if(method == null){
            logger.error("method not found:{}",methodName);
            resp.getWriter().println("method not found:"+methodName);
            return;
        };
        BufferedReader br = new BufferedReader(new InputStreamReader(req.getInputStream(), "UTF-8"));
        String line = null;
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();
        JSONObject jsonObject = JSONObject.parseObject(sb.toString());
        Map<String, String[]> reqMap = req.getParameterMap();
        Object result  = null;
        Class[] classes = method.getParameterTypes();
        Object reqParam = null;
        try {
            reqParam = classes[0].newInstance();
            BeanUtils.populate(reqParam,jsonObject);
            BeanUtils.populate(reqParam,reqMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            result = method.invoke(methodFunctionMap.get(methodName),reqParam);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
        Map<String,Object> reply = new HashMap<>();
        reply.put("reply",result);
        resp.getWriter().println(JSON.toJSONString(reply, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteDateUseDateFormat));
//        try{
//            writer = resp.getWriter();
//            writer.write();
//        }
    }
}
