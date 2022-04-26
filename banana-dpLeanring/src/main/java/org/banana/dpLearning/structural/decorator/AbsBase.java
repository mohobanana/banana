package org.banana.dpLearning.structural.decorator;

public abstract class AbsBase {

    private int num;
    private String str;

    public AbsBase(int num, String str) {
        this.num = num;
        this.str = str;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public abstract int getTot();
}
