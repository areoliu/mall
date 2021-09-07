package com.mall.common.service;

import com.mall.common.model.RocMqMessage;
import org.apache.rocketmq.client.exception.MQClientException;

import java.util.List;

/**
 * @ClassName RocMqService
 * @Description TODO
 * @Author liupanda
 * @Date 2021/9/6 22:02
 * @Version 1.0
 **/
public interface RocMqProducerService {

    public void synSend(List<RocMqMessage> messages) throws MQClientException;

    public void asynSend(List<RocMqMessage> messages);

    public void orderSend(List<RocMqMessage> messages);

    public void delaySend(List<RocMqMessage> messages,Integer delayLevel);
}
