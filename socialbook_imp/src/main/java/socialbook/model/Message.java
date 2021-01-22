package socialbook.model;

import java.sql.Timestamp;

public class Message {

    private int id_message;
    private Boolean sender; // 0 = customer 1 = admin
    private int id_ticket;
    private Timestamp timestamp;
    private String message_body;

    public Message(){}
    public Message( boolean sender, int id, Timestamp time, String msg){
        this.sender = sender;
        id_ticket = id;
        timestamp = time;
        message_body = msg;
    }

    public int getId_message() {
        return id_message;
    }

    public void setId_message(int id_message) {
        this.id_message = id_message;
    }

    public Boolean getSender() {
        return sender;
    }

    public void setSender(boolean sender) {
        this.sender = sender;
    }

    public int getId_ticket() {
        return id_ticket;
    }

    public void setId_ticket(int id_ticket) {
        this.id_ticket = id_ticket;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage_body() {
        return message_body;
    }

    public void setMessage_body(String message_body) {
        this.message_body = message_body;
    }
}
