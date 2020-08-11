package org.banana.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

//@WebServlet("/*")
public class testServlet extends HttpServlet implements ApplicationContextAware {
    private static Logger logger = LoggerFactory.getLogger(testServlet.class);

    private static ApplicationContext applicationContext;

    @Override
    protected void  doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("================testServlet running================");
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
        String method = req.getMethod();
        resp.getWriter().write("================request parameters================<br>");
        Map<String,String[]> requestMap = req.getParameterMap();
        for(String name : requestMap.keySet()){
            String[] values = requestMap.get(name);
            resp.getWriter().write(name+":");
            for(String value : values){
                resp.getWriter().write(value+";");
            }
            resp.getWriter().write("<br>");
        }
        resp.getWriter().write("================request parameters================");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("================testServlet running================");
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
        resp.getWriter().write("================request================<br>");
        String contextPath = req.getContextPath();
        resp.getWriter().write("contextPath:"+contextPath+"<br>");
        StringBuffer requestURL = req.getRequestURL();
        resp.getWriter().write("requestURL:"+requestURL.toString()+"<br>");
        String pathInfo = req.getPathInfo();
        resp.getWriter().write("pathInfo:"+pathInfo+"<br>");
        resp.getWriter().write("================request parameters================<br>");
        Map<String,String[]> requestMap = req.getParameterMap();
        for(String name : requestMap.keySet()){
            String[] values = requestMap.get(name);
            resp.getWriter().write(name+":");
            for(String value : values){
                resp.getWriter().write(value+";");
            }
            resp.getWriter().write("<br>");
        }
        resp.getWriter().write("================request parameters================<br>");
        applicationContext.getBean(pathInfo.substring(1));
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
