package org.banana.dpLearning.structural.proxyDecor;

public class Client {

    public static void main(String[] args) {
        ProxyInterface baseOne = new BaseOne();
        ProxyInterface baseTwo = new BaseTwo();
        baseOne.print();
        baseTwo.print();
        ProxyFactory proxyFactory = new ProxyFactory();
        ProxyInterface proxyOne = proxyFactory.getProxyObject(baseOne);
        ProxyInterface proxyOneOne = proxyFactory.getProxyObject(proxyOne);
        proxyOneOne.print();

    }
}
