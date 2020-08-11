package org.banana.config.mybatis;

import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.wrapper.ObjectWrapper;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;

import java.util.Map;

public class MyWrapperFactory implements ObjectWrapperFactory {
    public boolean hasWrapperFor(Object o) {
        return o!= null && o instanceof Map;
    }

    public ObjectWrapper getWrapperFor(MetaObject metaObject, Object o) {
        return new MyMapWrapper(metaObject,(Map) o);
    }
}
