package com.example.MessageAPI;

import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import java.util.logging.Level;
import java.util.logging.Logger;

@Component

public class JdbcFetch {


//    public JdbcFetch() {
//    }@


    List<Message> FetchData(){
        String url = "jdbc:mysql://localhost:3306/springboot?useSSL=false";
        String user = "root";
        String password = "Biswa@123";


        String query = "select * from message where status =0;";
       List<Message> messages=new ArrayList<>();
        try (Connection con = DriverManager.getConnection(url, user, password);
             PreparedStatement pst = con.prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {


            while (rs.next()) {
                Message message = new Message();
                message.setId(rs.getInt(1));
                message.setMsg(rs.getString(2));
                message.setDelay(rs.getTime(3).toLocalTime());
                message.setStatus(rs.getInt(4));
                messages.add(message);
            }




        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(JdbcFetch.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);

        }
        return messages;

    }

}
