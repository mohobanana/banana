package org.banana.common.utils;


import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

public class PrintUtil {
    private static Logger logger = LoggerFactory.getLogger(PrintUtil.class);
    public static String printFields(Object obj) {
        StringBuilder stringBuilder = new StringBuilder();
        if (obj instanceof List){
            stringBuilder.append("[");
            List list = (List)obj;
            for (Object o : list) {
                stringBuilder.append("{"+toJsonString(o)+"},\n");
            }
            stringBuilder.deleteCharAt(stringBuilder.length()-1);
            stringBuilder.append("]");
        } else {
            stringBuilder.append(toJsonString(obj));
        }
        return stringBuilder.toString();
    }
    public static String toJsonString(Object obj){
        String s = JSONObject.toJSON(obj).toString();
        logger.info(s);
        return s;
    }
    public static String formatString(String format,String[] args) {
        FormattingTuple ft = MessageFormatter.arrayFormat(format, args);
        return ft.getMessage();
    }
}
