package com.mall.common.service;

import com.mall.common.model.RocMqMessage;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;

import java.util.List;

/**
 * @ClassName RocMqService
 * @Description TODO
 * @Author liupanda
 * @Date 2021/9/6 22:02
 * @Version 1.0
 **/
public interface RocMqProducerService <T>{

    public void synSend(RocMqMessage message) throws MQClientException;

    public void asynSend(RocMqMessage message);

    public void orderSend(RocMqMessage message);

    public void delaySend(RocMqMessage message, Integer delayLevel);
}
