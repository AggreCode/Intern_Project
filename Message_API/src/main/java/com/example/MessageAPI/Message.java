package com.example.MessageAPI;

import javax.persistence.*;
import java.time.LocalTime;

@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Integer id;

    private String msg;

//    @Temporal(TemporalType.TIMESTAMP)
    private LocalTime delay;

    private Integer status;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Message() {
    }

    public Message(Integer id, String msg, LocalTime delay, Integer status) {
        this.id = id;
        this.msg = msg;
        this.delay = delay;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public LocalTime getDelay() {
        return delay;
    }

    public void setDelay(LocalTime delay) {
        this.delay = delay;
    }

       @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", msg='" + msg + '\'' +
                ", delay=" + delay +
                ", status='" + status + '\'' +
                '}';
    }
}
