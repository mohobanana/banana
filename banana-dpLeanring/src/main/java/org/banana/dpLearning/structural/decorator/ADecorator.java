package org.banana.dpLearning.structural.decorator;

public class ADecorator extends Decorator{

     public ADecorator(AbsBase absBase) {
        super(absBase, 1, "A");
    }

    @Override
    public int getTot() {
        return getParent().getNum()+ getNum();
    }

    @Override
    public String getStr() {
        return super.getStr()+getParent().getStr();
    }
}
