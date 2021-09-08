package com.mall.common.listener;

import com.mall.common.model.RocMqMessage;
import com.mall.common.service.RocMqConsumerService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.annotation.SelectorType;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQPushConsumerLifecycleListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


/**
 * @author liu
 */
@Slf4j
@Component("rocketCancelTagConsumer")
@RocketMQMessageListener(nameServer = "${rocketmq.name-server}", topic = "${rocketmq.consumer.topic}",
        consumeMode = ConsumeMode.CONCURRENTLY,
        consumerGroup = "${rocketmq.consumer.group}")
public class RocketTagConsumer implements RocketMQListener<RocMqMessage>{

    @Autowired(required = false)
    RocMqConsumerService rocMqConsumerService;

    @Value("${rocketmq.consumer.instance}")
    private String instance;

    @Override
    public void onMessage(RocMqMessage message) {
        log.info("start consume "+message.getData());
        rocMqConsumerService.consumer(message);
        log.info("end consume");
    }

//    @Override
//    public void prepareStart(DefaultMQPushConsumer consumer){
//        consumer.setInstanceName(instance);
//    }
}
