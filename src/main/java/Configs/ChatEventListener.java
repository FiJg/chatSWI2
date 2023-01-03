package Configs;

import Models.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

//webs
public class ChatEventListener {

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @EventListener
    public void handleWSConnectListener(SessionConnectedEvent event){
        System.out.println("Have a new web socket conn");
    }

    @EventListener
    public void handleWSDisconnectListener(SessionDisconnectEvent event){

        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        String username = (String) headerAccessor.getSessionAttributes().get("username");

        if (username != null){
            Message chatMessage = new Message();

            chatMessage.setType(Message.MessageType.LEAVE);

            chatMessage.setSender(username);

            messagingTemplate.convertAndSend(chatMessage);
        }

    }
}
