package org.banana.dpLearning.builder.prototype;

public class Proto implements Cloneable{

    private String name;
    private int num;
    private State state;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    @Override
    protected Proto clone() throws CloneNotSupportedException {
        return (Proto)super.clone();
    }
}
