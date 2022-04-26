package org.banana.dpLearning.structural.decorator;

public class Client {
    public static void main(String[] args) {
        AbsBase base = new Base();
        System.out.println(base.getStr()+":"+base.getStr());
        System.out.println("=======");
        base = new ADecorator(base);
        System.out.println(base.getStr()+":"+base.getStr());
        System.out.println("=======");
        base = new BDecorator(base);
        System.out.println(base.getStr()+":"+base.getStr());
    }
}
