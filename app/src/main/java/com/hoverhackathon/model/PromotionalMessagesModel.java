package com.hoverhackathon.model;

public class PromotionalMessagesModel {
    String messageNumber, messageContent,messageAddress;

    public PromotionalMessagesModel(String messageNumber) {
        this.messageNumber = messageNumber;
    }

    public String getMessageNumber() {
        return messageNumber;
    }

    public void setMessageNumber(String messageNumber) {
        this.messageNumber = messageNumber;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public String getMessageAddress() {
        return messageAddress;
    }

    public void setMessageAddress(String messageAddress) {
        this.messageAddress = messageAddress;
    }
}
