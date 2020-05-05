package com.hoverhackathon;

public class Message {
    public String messageNumber,messageAddress;

    boolean isChecked ;

    public String getMessageNumber() {
        return messageNumber;
    }

    public void setMessageNumber(String messageNumber) {
        this.messageNumber = messageNumber;
    }

    public String getMessageAddress() {
        return messageAddress;
    }

    public void setMessageAddress(String messageAddress) {
        this.messageAddress = messageAddress;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}