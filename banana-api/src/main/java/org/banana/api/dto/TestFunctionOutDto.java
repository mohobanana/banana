package org.banana.api.dto;

import java.util.List;

public class TestFunctionOutDto {
    String msg;

    List<String> notifyPersonIdList;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<String> getNotifyPersonIdList() {
        return notifyPersonIdList;
    }

    public void setNotifyPersonIdList(List<String> notifyPersonIdList) {
        this.notifyPersonIdList = notifyPersonIdList;
    }
}
