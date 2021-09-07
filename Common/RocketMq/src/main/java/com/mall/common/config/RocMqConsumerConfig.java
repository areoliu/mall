package com.mall.common.config;

import com.mall.common.listener.RocMqConsumerListener;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Configuration
public class RocMqConsumerConfig {

    @Value("${rocketmq.name-server}")
    private String server;

    @Value("${rocketmq.consumer.group}")
    private String group;

    @Resource
    private RocMqConsumerListener rocketMQMessageListener;

    @Bean("consumer")
    public DefaultMQPushConsumer getConsumer() throws MQClientException {

        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(group);
        consumer.setNamesrvAddr(server);
        consumer.setMessageModel(MessageModel.CLUSTERING);
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        consumer.registerMessageListener(rocketMQMessageListener);
        consumer.start();
        return  consumer;
    }


}
