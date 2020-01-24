package com.example.volunteer.model;

import com.google.firebase.firestore.auth.User;

public class SendMsg {

    private String msg;
    private String destinationUid;
    private String UserId;
    private String sourceUid;
    private String dateTime;
    private String destinationId;
    private String Uid;

    public SendMsg() {

    }



    public SendMsg(String msg, String destinationUid, String userId, String sourceUid) {
        this.msg = msg;
        this.destinationUid = destinationUid;
        this.UserId = userId;
        this.sourceUid = sourceUid;
    }

    public SendMsg(String msg, String destinationUid, String DateTime, String userId, String sourceUid) {
        this.msg = msg;
        this.destinationUid = destinationUid;
        this.UserId = userId;
        this.sourceUid = sourceUid;
        this.dateTime = DateTime;
    }

    public SendMsg(String msg, String destinationUid, String userId, String sourceUid, String dateTime, String destinationId) {
        this.msg = msg;
        this.destinationUid = destinationUid;
        UserId = userId;
        this.sourceUid = sourceUid;
        this.dateTime = dateTime;
        this.destinationId = destinationId;
    }

    public SendMsg(String msg, String destinationUid, String userId, String sourceUid, String dateTime, String destinationId,String uid) {
        this.msg = msg;
        this.destinationUid = destinationUid;
        UserId = userId;
        this.sourceUid = sourceUid;
        this.dateTime = dateTime;
        this.destinationId = destinationId;
        this.Uid = uid;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public String getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(String destinationId) {
        this.destinationId = destinationId;
    }

    public String getSourceUid() {
        return sourceUid;
    }

    public void setSourceUid(String sourceUid) {
        this.sourceUid = sourceUid;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setDestinationUid(String destinationUid) {
        this.destinationUid = destinationUid;
    }

    public String getDestinationUid() {
        return destinationUid;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

}

