package com.example.volunteer;

public class MessageInfo {
    private String DestinationUid;
    private String SourceUid;
    private String MessageText;

    public MessageInfo() {
    }

    public MessageInfo(String DestinationUid, String SourceUid, String MessageText) {
        this.DestinationUid = DestinationUid;
        this.SourceUid = SourceUid;
        this.MessageText = MessageText;
    }


    public String getDestinationUid() {
        return DestinationUid;
    }

    public String getSourceUid() {
        return SourceUid;
    }

    public String getMessageText() {
        return MessageText;
    }

}
