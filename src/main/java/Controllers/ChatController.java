package Controllers;

import Models.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@RestController
public class ChatController {

// messageMapping vs postMappign
    private static final Logger LOGGER = LoggerFactory.getLogger(ChatController.class);
    private static Map<String, Object> clients = new ConcurrentHashMap<String, Object>();

    //move?
    @Autowired
    private RabbitTemplate rabbitTemplate;


    @MessageMapping("/chat.sendMessage")
    public void sendMessage(@Payload Message message, Principal principal) {
        try {
            String nameOfSender = principal.getName();
            message.setSender(nameOfSender);
           // rabbitTemplate.convertAndSend("topicExchange", "topic.public", JsonUtil.parseObjToJson(message));
            rabbitTemplate.convertAndSend("topicExchange", "topic.public", message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @MessageMapping("/chat.sendMessageToUser")
    public void sendMessageToUser (@Payload Message message, Principal principal){
        try {
            String nameOfSender = principal.getName();
            message.setSender(nameOfSender);
            rabbitTemplate.convertAndSend("topicExchange", "topic.user", message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @MessageMapping("/chat.deleteUser")
    public void deleteUser (@Payload Message message){
        LOGGER.info(message.getSender());
        try {
            clients.remove(message.getSender());
            message.setConnectedUsers(clients);
            rabbitTemplate.convertAndSend("topicExchange", "topic.public", message);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    @MessageMapping("/chat.addUser")
    public void addUser (@Payload Message message){
        LOGGER.info(message.getSender());
        try {
            clients.put(message.getSender(), message.getSender());
            message.setConnectedUsers(clients);
            rabbitTemplate.convertAndSend("topicExchange", "topic.public", message);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }


}