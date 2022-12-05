@Configuration
public class RabbitConfig {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    public final static String ROUTING_A = "routing.A";

    public final static String ROUTING_B = "routing.B";
    public static final String ROUTING_WEBSOCKET = "routing.websocket";

    public final static String QUEUE_A = "queue.A";
    public final static String QUEUE_B = "queue.B";
    public static final String QUEUE_WEBSOCKET = "queue.websocket";
    public final static String QUEUE_ALL = "queue.All";

    public final static String EXCHANGE_DIRECT = "exchange.direct";
    public final static String EXCHANGE_FANOUT = "exchange.fanout";
    public final static String TOPIC_EXCHANGE_NAME = "exchange.topic";
    public static final String EXCHANGE_WEBSOCKET = "exchange.websocket";

    /*
    private static final boolean NON_DURABLE = false;

    public static final String FANOUT_QUEUE_1_NAME = "fanout.queue.1";
    public static final String FANOUT_QUEUE_2_NAME = "fanout.queue.2";
    public static final String FANOUT_EXCHANGE_NAME = "fanout.exchange";

    public static final String TOPIC_QUEUE_1_NAME = "topic.queue.1";
    public static final String TOPIC_QUEUE_2_NAME = "topic.queue.2";
    public static final String TOPIC_EXCHANGE_NAME = "topic.exchange";
    */


    public static final String BINGING_PATTERN_IMPORTANT = "*.important.*";
    public static final String BINDING_PATTERN_ERROR = "#.error";



    @Bean
    DirectExchange exchange(){
        return new DirectExchange(EXCHANGE_DIRECT);
    }

    @Bean
    FanoutExchange fanoutExchange(){return new FanoutExchange(EXCHANGE_FANOUT);}

    @Bean
    TopicExchange topicExchange(){return new TopicExchange(TOPIC_EXCHANGE_NAME);}

    @Bean
    Queue queueA(){
         return new Queue(QUEUE_A,false);
    }

    @Bean
    public Binding bindingForA(@Qualifier("queueA") Queue queue, DirectExchange exchange){
        return BindingBuilder.bind(queue)
                .to(exchange)
                .with(ROUTING_A);

    }

    @Bean
    public Binding bindingForAFanOut(@Qualifier("queueA") Queue queue, FanoutExchange exchange){
        return BindingBuilder.bind(queue)
                .to(exchange);
    }

    @Bean
    Binding bindingForATopic(@Qualifier("queueA") Queue queue, TopicExchange exchange){
        return BindingBuilder.bind(queue)
                .to(exchange)
                .with(ROUTING_A);
    }


    @Bean
    Queue queueB(){
        return new Queue(QUEUE_B,false);
    }

    @Bean
    Binding bindingForB(@Qualifier("queueB") Queue queue, DirectExchange exchange){
        return BindingBuilder.bind(queue)
                .to(exchange)
                .with(ROUTING_B);

    }

    @Bean
    Binding bindingForBFanout(@Qualifier("queueB") Queue queue, FanoutExchange exchange){
        return BindingBuilder.bind(queue)
                .to(exchange);
    }

    @Bean
    Binding bindingForBTopic(@Qualifier("queueB") Queue queue, TopicExchange exchange){
        return BindingBuilder.bind(queue)
                .to(exchange)
                .with(ROUTING_B);

    }

    @Bean
    Queue queueAll(){
        return new Queue(QUEUE_ALL,false);
    }

    @Bean
    Binding bindingForAll(@Qualifier("queueAll") Queue queue, TopicExchange exchange){
        return BindingBuilder.bind(queue)
                .to(exchange)
                .with("#");

    }

    @Bean
    public Binding bindingWEBSOCKET() {
        return BindingBuilder.bind(queueWEBSOCKET()).to(websocketExchange()).with(ROUTING_WEBSOCKET);

    }

    public Queue queueWEBSOCKET() {
        return new Queue(QUEUE_WEBSOCKET, true);
    }
    @Bean
    MessageConverter converter(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    RabbitTemplate rabbitTemplate(ConnectionFactory factory){
       RabbitTemplate rabbitTemplate = new RabbitTemplate(factory);
       rabbitTemplate.setMessageConverter(converter());
       return rabbitTemplate;
    }

/*
    @Bean
    public Declarables topicBindings(){
        Queue topicQueue1 = new Queue(TOPIC_QUEUE_1_NAME, NON_DURABLE);
        Queue topicQueue2 = new Queue(TOPIC_QUEUE_2_NAME, NON_DURABLE);

        TopicExchange topicExchange = new TopicExchange(TOPIC_EXCHANGE_NAME, NON_DURABLE, false);

        return new Declarables(topicQueue1, topicQueue2, topicExchange,
                BindingBuilder
                        .bind(topicQueue1)
                        .to(topicExchange)
                        .with(BINGING_PATTERN_IMPORTANT),
                BindingBuilder
                        .bind(topicQueue2)
                        .to(topicExchange)
                        .with(BINDING_PATTERN_ERROR)
        );
    }

    @Bean
    public Declarables fanoutBindings() {
        Queue fanoutQueue1 = new Queue(FANOUT_QUEUE_1_NAME, NON_DURABLE);
        Queue fanoutQueue2 = new Queue(FANOUT_QUEUE_2_NAME, NON_DURABLE);

        FanoutExchange fanoutExchange = new FanoutExchange(FANOUT_EXCHANGE_NAME, NON_DURABLE, false);

        return new Declarables(fanoutQueue1, fanoutQueue2, fanoutExchange,
                BindingBuilder
                        .bind(fanoutQueue1).to(fanoutExchange),
                BindingBuilder
                        .bind(fanoutQueue2).to(fanoutExchange)

        );
    }
    */

}
