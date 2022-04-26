package org.banana.dpLearning.structural.decorator;

public class Base extends AbsBase{

    public Base() {
        super(10,"Base");
    }

    @Override
    public int getTot() {
        return getNum();
    }
}
