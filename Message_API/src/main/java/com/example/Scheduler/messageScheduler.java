package com.example.Scheduler;

import com.example.Entity.Message;
import com.example.Service.messageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;


class Producer extends Thread{

    public Producer() {
    }

    private BlockingQueue Queue ;

    private JdbcFetch loader;


    public Producer(BlockingQueue queue,JdbcFetch loader) {
        this.Queue=queue;
        this.loader=loader;
    }


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
            System.out.println(messages.size() + " size");
           for(Message msg:messages) {
               Queue.put(msg);

               System.out.println(msg.getMsg() + " Produced");
               Thread.sleep(10000);
           }

        }
        catch(NullPointerException e){
            System.out.println("loader failed");

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


}



class messageConsumer extends  Thread{
//    public messageConsumer() {
//    }



    private  BlockingQueue Queue;
    private messageService service;

    Logger logger = LoggerFactory.getLogger(messageConsumer.class);

    private RestTemplate restTemplate;

    public static byte[] getParamsByte(Map<String, Object> params) {
        byte[] result = null;
        StringBuilder postData = new StringBuilder();
        for (Map.Entry<String, Object> param : params.entrySet()) {
            if (postData.length() != 0) {
                postData.append('&');
            }
            postData.append(encodeParam(param.getKey()));
            postData.append('=');
            postData.append(encodeParam(String.valueOf(param.getValue())));
        }
        result = postData.toString().getBytes(StandardCharsets.UTF_8);
        return result;
    }

    public static String encodeParam(String data) {
        String result = "";
        try {
            result = URLEncoder.encode(data, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }
    private Message msg  ;

    public messageConsumer(BlockingQueue queue, RestTemplate restTemplate, Message msg, messageService service) {
        Queue=queue;
        this.restTemplate= restTemplate;
        this.msg = msg;
        this.service= service;
    }

    public void run(){
        while(true){

            try {
                msg = (Message) Queue.poll(2000, TimeUnit.DAYS);

                Gson gson = new Gson();
                URL url = null;
                HttpURLConnection con = null;

                url = new URL("https://api.gupshup.io/sm/api/v1/msg");
                con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("POST");
                con.setUseCaches(false);
                con.setDoOutput(true);
                con.setDoInput(true);


                con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                con.setRequestProperty("apikey", "cjugfqxluammylxbhxpgo3fdft1tpopx");
                con.setRequestProperty("Accept", "application/json");

                OutputStream outputStream = con.getOutputStream();
                Map<String, Object> body = new HashMap<>();

                HashMap<String, String> message = new HashMap<String, String>();



                message.put("type", "text");
                message.put("text", msg.getMsg());


                String jsonString = gson.toJson(message);
                JsonObject jsonObject = JsonParser.parseString(jsonString).getAsJsonObject();

                body.put("channel", "whatsapp");
                body.put("source", "917834811114");
                body.put("destination", "91"+msg.getReceiver_phoneno());
                body.put("message", jsonObject);
                body.put("src.name", "TestWPIntern");
                outputStream.write(getParamsByte(body));
                if (con.getResponseCode() == HttpURLConnection.HTTP_ACCEPTED) {

                       logger.info("connection estalished");
                    ObjectMapper objectMapper = new ObjectMapper();
                    Map<String, String> response = objectMapper.readValue(con.getInputStream(), Map.class);
                    logger.info("response--> " + response.toString());
                    service.updateStatus(msg.getMsg_id(),1,LocalDateTime.now(),response.get("messageId"));
                }
                else{
                    logger.info("connection failed");
                    service.updateStatus(msg.getMsg_id(),-1,LocalDateTime.now(),null);
                }

     }
            catch (NullPointerException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ProtocolException e) {
                throw new RuntimeException(e);
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

        }
    }







@Component

public class messageScheduler implements CommandLineRunner{
    @Autowired
  private BlockingQueue Queue;
    @Autowired
    private JdbcFetch loader;
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private Message msg;
    @Autowired
    private messageService service;

    void Scheduler( ){

       new Producer(Queue,loader).start();
       new messageConsumer(Queue,restTemplate,msg,service).start();
   }


    @Override
    public void run(String... args) throws Exception {
        Scheduler();
    }
}
