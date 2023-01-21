package Configs;

import Models.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

//webs
public class ChatEventListener {

    private static final Logger logger = LoggerFactory.getLogger(ChatEventListener.class);

   // @Autowired
    private SimpMessageSendingOperations messagingTemplate;
    /*
    public ChatEventListener(SimpMessagingTemplate messagingTemplate, SimpMessageSendingOperations sendingOperations) {
        this.messagingTemplate = messagingTemplate;
    }
    */

    @EventListener
    public void handleWSConnectListener(SessionConnectedEvent event){
        logger.info("new socket connection");
        System.out.println("Have a new web socket conn");
    }

    @EventListener
    public void handleWSDisconnectListener(SessionDisconnectEvent event){
        logger.info("user disconnected");
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        String username = (String) headerAccessor.getSessionAttributes().get("username");

        if (username != null){
            Message chatMessage = new Message();

            chatMessage.setType(Message.MessageType.LEAVE);

            chatMessage.setSender(username);

            messagingTemplate.convertAndSend("/topic", chatMessage);
        }

    }
}
