package org.banana.dpLearning.structural.proxyDecor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyFactory {

    public ProxyInterface getProxyObject(ProxyInterface base) {
        ProxyInterface proxy_execute = (ProxyInterface)Proxy.newProxyInstance(
                base.getClass().getClassLoader(),
                base.getClass().getInterfaces(),
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        System.out.println("Proxy execute");
                        method.invoke(base, args);
                        return null;
                    }
                }
        );
        return proxy_execute;
    }
}
