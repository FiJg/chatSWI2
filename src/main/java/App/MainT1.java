package App;


import Configs.RabbitConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MainT1 {

    private static String ROUTING_KEY_USER_IMPORTANT_WARN = "user.important.warn";
    private static String ROUTING_KEY_USER_IMPORTANT_ERROR = "user.important.error";

    public static void main(String[] args) {

        SpringApplication.run(MainT1.class, args);
    }

    @Bean
    ApplicationRunner applicationRunner(RabbitTemplate rabbitTemplate) {
        String message = " payload is broadcast";

        return args -> {
            rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_FANOUT, "", "fanout" + message);
            rabbitTemplate.convertAndSend(RabbitConfig.TOPIC_EXCHANGE_NAME, ROUTING_KEY_USER_IMPORTANT_WARN, "topic important warn" + message);
            rabbitTemplate.convertAndSend(RabbitConfig.TOPIC_EXCHANGE_NAME, ROUTING_KEY_USER_IMPORTANT_ERROR, "topic important error" + message);
        };
    }
/*
    @RabbitListener(queues = {RabbitConfig.FANOUT_QUEUE_1_NAME})
    public void receiveMessageFromFanout1(String message) {
        System.out.println("Fanout 1: " + message);
    }

    @RabbitListener(queues = {RabbitConfig.FANOUT_QUEUE_2_NAME})
    public void receiveMessageFromFanout2(String message) {
        System.out.println("Fanout 2:" + message);
    }

    @RabbitListener(queues = {RabbitConfig.TOPIC_QUEUE_1_NAME})
    public void receiveMessageFromTopic1(String message) {
        System.out.println("Topic 1: "+ message);
    }

    @RabbitListener(queues = {Rabbit2Config.TOPIC_QUEUE_2_NAME})
    public void receiveMessageFromTopic2(String message) {
        System.out.println("Topic 2: "+ message);
    }

 */
}
