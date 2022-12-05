

import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@RestController
@RequestMapping("pro")
@Log4j2
public class ProviderController {

    @Autowired
    private RabbitTemplate template;

    /**
     * 直连
     * @param msg
     */
    @GetMapping("direct")
    public void sendDirect(@PathParam("msg") String msg){
        String msgId = String.valueOf(UUID.randomUUID());
        String s = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String, Object> map = new HashMap<>(16);
        map.put("messageId", msgId);
        map.put("messageData", msg);
        map.put("createTime", s);
        template.convertAndSend("TestDirectExchange", "TestDirectRouting", map);
        log.info("消息已发送：{}", map.toString());
    }

    /**
     * 主题
     */
    @GetMapping("topic1")
    public void sendTopic1() {
        String msgId = String.valueOf(UUID.randomUUID());
        String msg = "topic1";
        String s = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String, Object> map = new HashMap<>(16);
        map.put("messageId", msgId);
        map.put("messageData", msg);
        map.put("createTime", s);
        template.convertAndSend("topicExchange", "topic.man", map);
        log.info("topic1消息已发送：{}", map.toString());
    }

    /**
     * 主题
     */
    @GetMapping("topic2")
    public void sendTopic2() {
        String msgId = String.valueOf(UUID.randomUUID());
        String msg = "topic2";
        String s = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String, Object> map = new HashMap<>(16);
        map.put("messageId", msgId);
        map.put("messageData", msg);
        map.put("createTime", s);
        template.convertAndSend("topicExchange", "topic.woman", map);
        log.info("topic2消息已发送：{}", map.toString());
    }

    /**
     * 扇形
     */
    @GetMapping("fanOutA")
    public void sendFanOutA() {
        String msgId = String.valueOf(UUID.randomUUID());
        String msg = "fanOutA";
        String s = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String, Object> map = new HashMap<>(16);
        map.put("messageId", msgId);
        map.put("messageData", msg);
        map.put("createTime", s);
        template.convertAndSend("fanOutExchange",null,map);
        log.info("fanOutA消息已发送：{}", map.toString());
    }

    /**
     * 扇形
     */
    @GetMapping("fanOutB")
    public void sendFanOutB() {
        String msgId = String.valueOf(UUID.randomUUID());
        String msg = "fanOutB";
        String s = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String, Object> map = new HashMap<>(16);
        map.put("messageId", msgId);
        map.put("messageData", msg);
        map.put("createTime", s);
        template.convertAndSend("fanOutExchange",null,map);
        log.info("fanOutB消息已发送：{}", map.toString());
    }

    /**
     * 扇形
     */
    @GetMapping("fanOutC")
    public void sendFanOutC() {
        String msgId = String.valueOf(UUID.randomUUID());
        String msg = "fanOutC";
        String s = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String, Object> map = new HashMap<>(16);
        map.put("messageId", msgId);
        map.put("messageData", msg);
        map.put("createTime", s);
        template.convertAndSend("fanOutExchange",null,map);
        log.info("fanOutC消息已发送：{}", map.toString());
    }
}