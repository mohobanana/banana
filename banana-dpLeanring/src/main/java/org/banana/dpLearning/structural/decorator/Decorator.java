package org.banana.dpLearning.structural.decorator;

public abstract class Decorator extends AbsBase{

    public AbsBase parent;

    public AbsBase getParent() {
        return parent;
    }

    public void setParent(AbsBase parent) {
        this.parent = parent;
    }

    public Decorator(AbsBase absBase, int num, String str) {
        super(num,str);
        this.parent = absBase;
    }
}
