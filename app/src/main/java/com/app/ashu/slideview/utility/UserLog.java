package com.app.ashu.slideview.utility;

import java.util.ArrayList;

public class UserLog {

    String dName;
    ArrayList<Message> messageList=new ArrayList<Message>();

    public UserLog(String dName) {
        this.dName = dName;
    }

    public UserLog(String dName, ArrayList<Message> messageList) {
        this.dName = dName;
        this.messageList = messageList;
    }

    public UserLog() {

    }

    public String getdName() {
        return dName;
    }

    public void setdName(String dName) {
        this.dName = dName;
    }

    public ArrayList<Message> getMessageList() {
        return messageList;
    }

    public void setMessageList(ArrayList<Message> messageList) {
        this.messageList = messageList;
    }
}
