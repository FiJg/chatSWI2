import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.websocket.Session;
import java.util.LinkedList;



@Component
public class GetHeaderParamInterceptor extends ChannelInterceptorAdapter {

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            Object raw = message.getHeaders().get(SimpMessageHeaderAccessor.NATIVE_HEADERS);
            if (raw instanceof Map) {
                Object name = ((Map) raw).get("username");
                if (name instanceof LinkedList) {

                    accessor.setUser(new UserPrincipal(((LinkedList) name).get(0).toString()));
                }
            }
        }
        return message;
    }
}