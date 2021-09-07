package com.mall.common.listener;

import com.mall.common.config.RocMqConsumerConfig;
import com.mall.common.service.RocMqConsumerService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.List;

@Slf4j
@Component
public class RocMqConsumerListener implements MessageListenerConcurrently {

    @Autowired(required = false)
    RocMqConsumerService rocMqConsumerService;

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
        for(int i=0; i<list.size(); i++){
            MessageExt messageExt = list.get(i);
            try {
                String body = new String(messageExt.getBody(),"utf-8");
                rocMqConsumerService.consumer(body);
                log.info("消息消费内容={}",body);
            } catch (Exception e) {
                log.error("消息消费异常{}",e);
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }
        }
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }
}
