package com.mall.common.service;

import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @ClassName RocMqService
 * @Description TODO
 * @Author liupanda
 * @Date 2021/9/6 22:02
 * @Version 1.0
 **/
public interface RocMqConsumerService {

    public void consumer(String data);
}
