package Models;

import java.util.Map;

public class Message {
    private MessageType type;
    private String content;
    private String sender;
    private String recipient;

    //Not impl
    private Map<String,Object> connectedUsers;

    public enum MessageType {
        CHAT,
        JOIN,
        LEAVE,
        PRIVATE_CHAT
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public Map<String, Object> getConnectedUsers() {
        return connectedUsers;
    }

    public void setConnectedUsers(Map<String, Object> connectedUsers) {
        this.connectedUsers = connectedUsers;
    }

    @Override
    public String toString() {
        return "ChatMessage{" +
                "type=" + type +
                ", content='" + content + '\'' +
                ", sender='" + sender + '\'' +
                ", to='" + recipient + '\'' +
                ", onlineUsers=" + connectedUsers +
                '}';
    }
}