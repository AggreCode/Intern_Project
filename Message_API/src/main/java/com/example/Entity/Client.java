package com.example.Entity;

import javax.persistence.*;
import java.util.List;


@Entity
public class Client {
    @Id

    private Integer  client_id;
    private String username;
    private String password;
    private String client_name;
    private String phone;
    private String email_id;



    public Client() {
    }

    public Client(Integer client_id, String username, String password, String client_name, String phone, String email_id) {
        this.client_id = client_id;
        this.username = username;
        this.password = password;
        this.client_name = client_name;
        this.phone = phone;
        this.email_id = email_id;
    }

    public Integer getClient_id() {
        return client_id;
    }

    public void setClient_id(Integer client_id) {
        this.client_id = client_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getClient_name() {
        return client_name;
    }

    public void setClient_name(String client_name) {
        this.client_name = client_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail_id() {
        return email_id;
    }

    public void setEmail_id(String email_id) {
        this.email_id = email_id;
    }

    @Override
    public String toString() {
        return "Client{" +
                "client_id=" + client_id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", client_name='" + client_name + '\'' +
                ", phone='" + phone + '\'' +
                ", email_id='" + email_id + '\'' +
                '}';
    }
}
