package com.example.volunteer.model;

public class ReceiveMsg {

    private String msg;
    private String sourceUid;
    private String DateTime;
    private String destinationUid;
    private String UserId;
    private String Uid;

    public ReceiveMsg() {

    }

    public ReceiveMsg(String msg, String sourceUid, String userId, String destinationUid) {
        this.msg = msg;
        this.sourceUid = sourceUid;
        this.UserId = userId;
        this.destinationUid = destinationUid;
    }

    public ReceiveMsg(String msg, String sourceUid, String dateTime, String destinationUid, String userId) {
        this.msg = msg;
        this.sourceUid = sourceUid;
        DateTime = dateTime;
        this.destinationUid = destinationUid;
        UserId = userId;
    }
    public ReceiveMsg(String msg, String sourceUid, String dateTime, String destinationUid, String userId,String uid) {
        this.msg = msg;
        this.sourceUid = sourceUid;
        DateTime = dateTime;
        this.destinationUid = destinationUid;
        UserId = userId;
        Uid = uid;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public String getDestinationUid() {
        return destinationUid;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public void setSourceUid(String sourceUid) {
        this.sourceUid = sourceUid;
    }

    public void setDestinationUid(String destinationUid) {
        this.destinationUid = destinationUid;
    }

    public String getMsg() {
        return msg;
    }

    public String getSourceUid() {
        return sourceUid;
    }

    public String getDateTime() {
        return DateTime;
    }

    public void setDateTime(String dateTime) {
        this.DateTime = dateTime;
    }
}
