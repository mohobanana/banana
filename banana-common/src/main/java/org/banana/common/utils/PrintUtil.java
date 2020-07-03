package org.banana.common.utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class PrintUtil {
    private static Logger logger = LoggerFactory.getLogger(PrintUtil.class);
    public static String printFields(Object obj) {
        StringBuilder stringBuilder = new StringBuilder();
        Class clazz = obj.getClass();
        stringBuilder.append("["+obj.toString()+":{");
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            try {
                PropertyDescriptor pd = null;
                pd = new PropertyDescriptor(field.getName(),clazz);
                Method method = pd.getReadMethod();
                String value = method.invoke(obj).toString();
                stringBuilder.append(field.getName()+":"+value+";");
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
        stringBuilder.append("}]");
        return stringBuilder.toString();
    }
    public static void printInfoLog(String msg) {
        logger.info("{}",msg);
    }
}
