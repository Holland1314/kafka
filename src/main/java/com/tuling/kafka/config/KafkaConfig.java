package com.tuling.kafka.config;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

//@Configuration
//@EnableKafka
public class KafkaConfig {

    @Value("${kafka.servers}")
    private String services;

    public Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();

        // list server1,server2
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, services);

        // 重发次数
        props.put(ProducerConfig.RETRIES_CONFIG, 0);


        props.put(ProducerConfig.ACKS_CONFIG, "0");


        // Producer会尝试把发往同一个Partition的多个Requests合并，指明合并后Requests总大小的上限
        // 如果设置的太小，可能会导致所有的request都不进行batch
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, 0);

        // Producer默认把两次发送时间检核内收集到的所有Request进行一次聚合再发送
        // linger.ms为每次发送增加delay，聚合更多message
        props.put(ProducerConfig.LINGER_MS_CONFIG, 100);

        // 再Producer存放未发出去的message的缓冲区大小 bytes , 默认33554432
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 67108864);

        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return props;
    }

    public ProducerFactory<String, String> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}
