package com.example.Entity;

import javax.persistence.*;

@Entity
@Table(name = "status")
public class Status {

    @Id
    @Column(name = "status_id")
    private Integer status_id;
    private  String description;


    public Status() {
    }

    public Status(Integer status_id, String description) {
        this.status_id = status_id;
        this.description = description;
    }

    public Integer getStatus_id() {
        return status_id;
    }

    public void setStatus_id(Integer status_id) {
        this.status_id = status_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
