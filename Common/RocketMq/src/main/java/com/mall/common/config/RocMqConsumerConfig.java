//package com.mall.common.config;
//
//import com.mall.common.listener.RocMqConsumerListener;
//import com.mall.common.listener.RocMqConsumerListener2;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
//import org.apache.rocketmq.client.exception.MQClientException;
//import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
//import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import javax.annotation.Resource;
//
//@Slf4j
//@Configuration
//public class RocMqConsumerConfig {
//
//    @Value("${rocketmq.name-server}")
//    private String server;
//
//    @Value("${rocketmq.consumer.topic}")
//    private String topic;
//
//    @Value("${rocketmq.consumer.group}")
//    private String group;
//
//    @Value("${rocketmq.consumer.tag}")
//    private String tag;
//
//    @Value("${rocketmq.consumer.instance}")
//    private String instance;
//
//    @Value("${rocketmq.consumer2.topic}")
//    private String topic2;
//
//    @Value("${rocketmq.consumer2.group}")
//    private String group2;
//
//    @Value("${rocketmq.consumer2.tag}")
//    private String tag2;
//
//    @Value("${rocketmq.consumer2.instance}")
//    private String instance2;
//
//    @Autowired
//    private RocMqConsumerListener rocketMQMessageListener;
//
//    @Autowired
//    private RocMqConsumerListener2 rocketMQMessageListener2;
//
//    @Bean("consumer")
//    public DefaultMQPushConsumer getConsumer1() throws MQClientException {
//        log.info("开始组装消费者");
//        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(group);
//        consumer.setInstanceName(instance);
//        consumer.setNamesrvAddr(server);
//        consumer.setMessageModel(MessageModel.CLUSTERING);
//        consumer.subscribe(topic,"tag1");
//        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
//        consumer.registerMessageListener(rocketMQMessageListener);
//
//        consumer.start();
//        log.info("完成组装消费者");
//        return  consumer;
//    }
//
//    @Bean("consumer2")
//    public DefaultMQPushConsumer getConsumer2() throws MQClientException {
//        log.info("开始组装消费者2");
//        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(group2);
//        consumer.setInstanceName(instance2);
//        consumer.setNamesrvAddr(server);
//        consumer.setMessageModel(MessageModel.CLUSTERING);
//        consumer.subscribe(topic2,"tag2");
//        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
//        consumer.registerMessageListener(rocketMQMessageListener2);
//
//        consumer.start();
//        log.info("完成组装消费者2");
//        return  consumer;
//    }
//
//
//}
