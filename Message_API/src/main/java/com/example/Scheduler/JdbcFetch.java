package com.example.Scheduler;

import com.example.Entity.Message;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import java.util.logging.Level;
import java.util.logging.Logger;

@Component

public class JdbcFetch {

    List<Message> FetchData(){
        String url = "jdbc:mysql://localhost:3306/messageApi_db?useSSL=false";
        String user = "root";
        String password = "Biswa@123";


        String query = "select * from message m where m.status_id= 0 and  date_add(now(),interval 1 minute)>sending_time";
       List<Message> messages=new ArrayList<>();
        try (Connection con = DriverManager.getConnection(url, user, password);
             PreparedStatement pst = con.prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {


            while (rs.next()) {
                Message message = new Message();
                message.setClient_id(rs.getInt("client_id"));
                message.setMsg(rs.getString(2));
                message.setSending_time(rs.getObject(3, LocalDateTime.class));
                message.setMsg_id(rs.getInt(1));
                message.setReceiver_phoneno(rs.getString(5));
                message.setStatus_id(rs.getInt(6));
                message.setSent_time(rs.getObject(7,LocalDateTime.class));
                message.setGupshup_api_id(rs.getString(8));
             messages.add(message);

            }




        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(JdbcFetch.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);

        }
        return messages;

    }

}
