package com.example.Formats;

import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

public class Message_Request {

    private  String msg_id;
    private String msg;
    private String sending_time;
    private String client_id;
    private String receiver_phoneno;

    public Message_Request(String msg_id, String msg, String sending_time, String client_id, String receiver_phoneno) {
        this.msg_id = msg_id;
        this.msg = msg;
        this.sending_time = sending_time;
        this.client_id = client_id;
        this.receiver_phoneno = receiver_phoneno;

    }

    public Message_Request() {
    }


}
