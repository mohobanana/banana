package org.banana.dpLearning.structural.decorator;

public class BDecorator extends Decorator{

    public BDecorator(AbsBase absBase){
        super(absBase,2,"B");
    }

    @Override
    public int getTot() {
        return getParent().getNum()+getNum();
    }

    @Override
    public String getStr() {
        return super.getStr()+getParent().getStr();
    }
}
