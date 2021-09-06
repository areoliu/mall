package com.mall.common.service;


import com.mall.common.RocketMqMessage;
import com.mall.common.enums.ResultCodeEnum;
import com.mall.common.exception.BusinessException;
import com.mall.common.util.ByteUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.springframework.context.annotation.Configuration;
import org.apache.rocketmq.common.message.Message;

import java.util.Date;
import java.util.List;

@Slf4j
@Configuration
public class RocMqServiceImpl implements RocMqService{


    @Override
    public void synSend(List<RocketMqMessage> messages, String server, String group, Integer retryCount) throws MQClientException {
        DefaultMQProducer producer = new DefaultMQProducer(group);
        producer.setNamesrvAddr(server);
        producer.setRetryTimesWhenSendFailed(retryCount);
        for(RocketMqMessage message:messages){
            try{
                Message msg = new Message(message.getTopic(), message.getTags(), message.getKey(),ByteUtil.objectToBytes(message.getData()));
                SendResult sendResult = producer.send(msg);
                if(!sendResult.getSendStatus().equals(SendStatus.SEND_OK)){

                }

            }catch (Exception e){
                //log.error(new Date()+"<<<<<<Send message failed,message key：{}",message.getKey(),"{}",e.getMessage());

                throw  new BusinessException(ResultCodeEnum.FAIL.getCode(),"Send message failed,message key："+message.getKey()+" "+e.getMessage());


            }

        }
        producer.shutdown();

    }

    @Override
    public void asynSend(List<RocketMqMessage> messages, String server, String group,Integer retryCount ) {

    }
}
