package com.mall.common.service;

import com.mall.common.RocketMqMessage;
import org.apache.rocketmq.client.exception.MQClientException;

import java.util.List;

/**
 * @ClassName RocMqService
 * @Description TODO
 * @Author liupanda
 * @Date 2021/9/6 22:02
 * @Version 1.0
 **/
public interface RocMqService {

    public void synSend(List<RocketMqMessage> messages, String server, String group, Integer retryCount) throws MQClientException;

    public void asynSend(List<RocketMqMessage> messages, String server, String group, Integer retryCount);
}
