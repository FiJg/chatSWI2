
@RestController
public class ChatController {

    /
    private static final Logger LOGGER = LoggerFactory.getLogger(ChatController.class);
    private static Map<String, Object> clients = new ConcurrentHashMap<String, Object>();

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @MessageMapping("/chat.sendMessage")
    public void sendMessage(@Payload Message message, Principal principal) {
        try {
            String nameOfSender = principal.getName();
            message.setSender(nameOfSender);
            rabbitTemplate.convertAndSend("topicExchange", "topic.public", JsonUtil.parseObjToJson(message));
        } catch (Exception e) {
            e.printStackTrace();
        }

        @MessageMapping("/chat.sendMessageToUser")
        public void sendMessageToUser (@Payload Message message, Principal principal){
            try {
                String nameOfSender = principal.getName();
                message.setSender(nameOfSender);
                rabbitTemplate.convertAndSend("topicExchange", "topic.user", JsonUtil.parseObjToJson(message));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        @MessageMapping("/chat.deleteUser")
        public void deleteUser (@Payload Message message){
            LOGGER.info(message.getSender());
            try {
                clients.remove(message.getSender());
                message.setOnlineUsers(clients);
                rabbitTemplate.convertAndSend("topicExchange", "topic.public", JsonUtil.parseObjToJson(message));

            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
            }
        }

        @MessageMapping("/chat.addUser")
        public void addUser (@Payload Message message){
            LOGGER.info(message.getSender());
            try {
                clients.put(message.getSender(), message.getSender());

                message.setOnlineUsers(clients);
                rabbitTemplate.convertAndSend("topicExchange", "topic.public", JsonUtil.parseObjToJson(message));

            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
            }
        }


    }