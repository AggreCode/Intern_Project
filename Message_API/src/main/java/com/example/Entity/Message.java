package com.example.Entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

@Entity
@Table(name = "message")
public class Message {

    @Id
    @NotNull(message = "id is absent")
    private  Integer msg_id;

    @NotBlank(message = "please write the message")
    private String msg;

    @NotNull(message = "please mention the time at which you want to send the message")
    private LocalDateTime sending_time;

    @NotNull(message = "please mention your client_id")
    private Integer client_id;



    @NotBlank(message = "please mention the receiver phone no")
    @Pattern(regexp = "^[6789]\\d{9}$",message = "invalid phoneno")
    private String receiver_phoneno;

      private Integer status_id;
    private String gupshup_api_id;

    private LocalDateTime sent_time;


    public Message() {
    }

    public Message(Integer msg_id, String msg, LocalDateTime sending_time, Integer client_id, String receiver_phoneno, Integer status_id, String gupshup_api_id, LocalDateTime sent_time) {
        this.msg_id = msg_id;
        this.msg = msg;
        this.sending_time = sending_time;
        this.client_id = client_id;
        this.receiver_phoneno = receiver_phoneno;
        this.status_id = status_id;
        this.gupshup_api_id = gupshup_api_id;
        this.sent_time = sent_time;
    }

    public Integer getMsg_id() {
        return msg_id;
    }

    public void setMsg_id(Integer msg_id) {
        this.msg_id = msg_id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public LocalDateTime getSending_time() {
        return sending_time;
    }

    public void setSending_time(LocalDateTime sending_time) {
        this.sending_time = sending_time;
    }

    public Integer getClient_id() {
        return client_id;
    }

    public void setClient_id(Integer client_id) {
        this.client_id = client_id;
    }

    public String getReceiver_phoneno() {
        return receiver_phoneno;
    }

    public void setReceiver_phoneno(String receiver_phoneno) {
        this.receiver_phoneno = receiver_phoneno;
    }

    public Integer getStatus_id() {
        return status_id;
    }

    public void setStatus_id(Integer status_id) {
        this.status_id = status_id;
    }

    public String getGupshup_api_id() {
        return gupshup_api_id;
    }

    public void setGupshup_api_id(String gupshup_api_id) {
        this.gupshup_api_id = gupshup_api_id;
    }

    public LocalDateTime getSent_time() {
        return sent_time;
    }

    public void setSent_time(LocalDateTime sent_time) {
        this.sent_time = sent_time;
    }

    @Override
    public String toString() {
        return "Message{" +
                "msg_id=" + msg_id +
                ", msg='" + msg + '\'' +
                ", sending_time=" + sending_time +
                ", client_id=" + client_id +
                ", receiver_phoneno='" + receiver_phoneno + '\'' +
                ", status_id=" + status_id +
                ", gupshup_api_id='" + gupshup_api_id + '\'' +
                ", sent_time=" + sent_time +
                '}';
    }
}
