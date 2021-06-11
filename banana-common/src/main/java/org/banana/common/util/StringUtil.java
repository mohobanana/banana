package org.banana.common.util;

import org.springframework.util.StringUtils;

/**
 * Desc: StringUtil
 * Created by mskj-mohao on 2021/6/2 5:39 PM
 * Copr: © 2020 MSKJ.All rights reserved.
 **/
public class StringUtil {

    public static String formatJson(String jsonStr){
        StringBuilder sb = new StringBuilder();
        int deep = 1;
        while(!StringUtils.isEmpty(jsonStr)){
            sb.append(jsonStr.substring(0,jsonStr.indexOf(123)));
            sb.append("{"+"\r\n");
            for(int i = 0;i<deep;i++){
                sb.append("\t");
            }
            deep++;
            jsonStr = jsonStr.substring(jsonStr.indexOf(123)+1);
        }
    }
}
