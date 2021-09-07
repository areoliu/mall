package com.mall.common.config;


import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class RocMqProducerConfig{

    @Value("${rocketmq.name-server}")
    private String server;

    @Value("${rocketmq.producer.group}")
    private String group;

    @Value("${rocketmq.producer.retryTimesWhenSendFailed}")
    private Integer retryCount;

    @Bean("producer")
    public DefaultMQProducer build(){
        DefaultMQProducer producer = new DefaultMQProducer(group);
        producer.setNamesrvAddr(server);
        producer.setRetryTimesWhenSendFailed(retryCount);
        return producer;
    }

}
