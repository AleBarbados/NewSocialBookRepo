package socialbook.model;

import java.sql.Timestamp;

public class Message {
    /*    id_message   int AUTO_INCREMENT PRIMARY KEY,
    sender       bool         NOT NULL,
    id_ticket    int          NOT NULL,
    time_stamp   timestamp    NOT NULL,
    message_body varchar(100) NOT NULL,*/

    private int id_message;
    private boolean sender; // 0 = customer 1 = admin
    private int id_ticket;
    private Timestamp timestamp;
    private String message_body;

}
