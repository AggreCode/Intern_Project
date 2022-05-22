package com.example.Scheduler;

import com.example.Entity.Message;
import com.example.Scheduler.JdbcFetch;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;


class Producer extends Thread{

    public Producer() {
    }
    @Autowired
    private BlockingQueue Queue ;

    private JdbcFetch loader;


    public Producer(BlockingQueue queue,JdbcFetch loader) {
        this.Queue=queue;
        this.loader=loader;
    }

    //    Producer(BlockingQueue q){
//        this.queue=q;
//    }
    public void run(){
        while(true){
            produce();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void produce() {
        try {
           List<Message> messages= loader.FetchData();
           for(Message msg:messages) {
               Queue.put(msg);
               System.out.println(msg.getMsg() + " Produced");
           }

        }
        catch(NullPointerException e){
            System.out.println("loader");

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


}
@Component
class messageConsumer extends  Thread{
   @Autowired
    private  BlockingQueue Queue;
    @Autowired
    ObjectMapper mapper;
    @Autowired
    RestTemplate restTemplate;



  private Message msg  ;
    public void run(){
        while(true){

            try {
                msg = (Message) Queue.poll(1000, TimeUnit.DAYS);


                ObjectNode objectNode = mapper.createObjectNode();
                ObjectNode msgNode = mapper.createObjectNode();
                msgNode.put("type","text");
                msgNode.put("text",msg.getMsg());
                objectNode.put("channel", "whatsapp");
                objectNode.put("source", "917834811114");
                objectNode.put("src.name", "TestWPIntern");
                objectNode.put("destination", "919692223942");
                objectNode.put("message", msgNode);

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.valueOf(MediaType.APPLICATION_FORM_URLENCODED_VALUE));
                headers.set("apikey","cjugfqxluammylxbhxpgo3fdft1tpopx");
                HttpEntity<ObjectNode> entity = new HttpEntity<ObjectNode>(objectNode,headers);

               System.out.println(restTemplate.exchange(
                       "https://api.gupshup.io/sm/api/v1/msg", HttpMethod.POST, entity, String.class).getBody());
            }
            catch (NullPointerException | InterruptedException e) {
                throw new RuntimeException(e);
            }

            }

        }
    }







@Component
@Configuration
public class messageScheduler implements CommandLineRunner{

    @Autowired
  private BlockingQueue Queue;





    @Autowired
    private JdbcFetch loader;



    void Scheduler( ){

       new Producer(Queue,loader).start();
//       new messageConsumer().start();
   }


    @Override
    public void run(String... args) throws Exception {
        Scheduler();
    }
}
