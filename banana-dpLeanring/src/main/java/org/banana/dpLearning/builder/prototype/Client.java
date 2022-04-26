package org.banana.dpLearning.builder.prototype;

public class Client {

    public static void main(String[] args) throws CloneNotSupportedException {
        Proto proto = new Proto();
        proto.setName("a");
        proto.setNum(1);
        State state = new State();
        state.setIndex(2);
        proto.setState(state);

        Proto clone = proto.clone();
        clone.setNum(123);
        clone.getState().setIndex(123);
        System.out.println(proto.getNum());
        System.out.println(proto.getState().getIndex());
    }
}
