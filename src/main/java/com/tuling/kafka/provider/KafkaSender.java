package com.tuling.kafka.provider;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tuling.kafka.bean.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

/**
 * 　　　┏┓　　　┏┓
 * 　　┏┛┻━━━┛┻┓
 * 　　┃　　　━　　　┃
 * 　　┃　┳┛　┗┳　┃
 * 　　┃　　　┻　　　┃
 * 　　┗━┓　　　┏━┛
 * 　　　　┃　　　┗━━━┓
 * 　　　　┃　　永无BUG 　┣┓
 * 　　　　┃　　如来保佑　┏┛
 * 　　　　┗┓┓┏━┳┓┏┛
 *
 * @author slc
 * @date 2019/8/5
 */
@Component
@Slf4j
public class KafkaSender {


    @Value("${kafka.topic}")
    private String kafkaTopic;

    @Value("${kafka.threshold}")
    private int kafka_threshold;

    @Value("${kafkaFlag}")
    private boolean kafkaFlag;
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    private Gson gson = new GsonBuilder().create();

    //发送消息
    public void send() {
        Message message = new Message();
        message.setId(System.currentTimeMillis());
        message.setMsg(UUID.randomUUID().toString());
        message.setSendTime(new Date());
        log.info("+++++++++++ message = {}", gson.toJson(message));
        kafkaTemplate.send(kafkaTopic, gson.toJson(message));
    }
}
