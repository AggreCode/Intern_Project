package com.example.Repository;

import com.example.Entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;


@Repository
public interface MessageRepository extends JpaRepository<Message,Integer> {

    @Modifying(clearAutomatically = true)
    @Query("update Message m set m.status_id = ?2,m.sent_time = ?3,m.gupshup_api_id=?4 where m.msg_id= ?1 ")
    public void updateStatus(Integer id, Integer status_id, LocalDateTime sent_time,String messageId);

}
