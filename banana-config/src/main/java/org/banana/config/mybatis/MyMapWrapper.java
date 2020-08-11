package org.banana.config.mybatis;

import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.wrapper.MapWrapper;
import org.banana.common.utils.StringUtil;

import java.util.Map;

public class MyMapWrapper extends MapWrapper {
    public MyMapWrapper(MetaObject metaObject, Map<String, Object> map) {
        super(metaObject, map);
    }

    @Override
    public String findProperty(String name, boolean useCamelCaseMapping) {
        if (useCamelCaseMapping) {
            return StringUtil.toCamel(name);
        }
        else{
            return name;
        }
    }
}
