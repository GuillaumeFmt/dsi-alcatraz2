package model;

import core.MessageBox;

import java.io.Serializable;

public class SpreadMessageData implements Serializable {

    MessageType messageType;
    String primary;
    MessageBox messageBox;
    String messageBoxClass;
    LocalServerState localServerState;

    public String getPrimary() {
        return primary;
    }

    public void setPrimary(String primary) {
        this.primary = primary;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public MessageBox getMessageBox() {
        return messageBox;
    }

    public void setMessageBox(MessageBox messageBox) {
        this.messageBox = messageBox;
    }

    public String getMessageBoxClass() {
        return messageBoxClass;
    }

    public void setMessageBoxClass(String messageBoxClass) {
        this.messageBoxClass = messageBoxClass;
    }

    public LocalServerState getLocalServerState() {
        return localServerState;
    }

    public void setLocalServerState(LocalServerState localServerState) {
        this.localServerState = localServerState;
    }

    @Override
    public String toString() {
        return "SpreadMessageData{" +
                "messageType=" + messageType +
                ", primary='" + primary + '\'' +
                ", messageBox=" + messageBox +
                ", messageBoxClass='" + messageBoxClass + '\'' +
                ", localServerState=" + localServerState +
                '}';
    }
}
