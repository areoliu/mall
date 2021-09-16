package com.mall.common.service;


import com.alibaba.fastjson.JSON;
import com.mall.common.enums.ResultCodeEnum;
import com.mall.common.exception.BusinessException;
import com.mall.common.model.RocMqMessage;
import com.mall.common.util.ByteUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service("rocMqProducerServiceImpl")
public class RocMqProducerServiceImpl<T> implements RocMqProducerService<T> {


    @Autowired
    RocketMQTemplate rocketMQTemplate;



    @Override
    public void synSend(RocMqMessage message) throws MQClientException {
        //DefaultMQProducer producer = build();

        try{
            Message msg = MessageBuilder.withPayload(message).setHeader("KEYS", message.getKey()).build();
                    //new Message(message.getTopic(), message.getTags(), message.getKey(),ByteUtil.objectToBytes(message.getData()));
            String destination = message.getTopic()+":"+message.getTags();
            SendResult sendResult = rocketMQTemplate.syncSend(destination,msg);
            if(!sendResult.getSendStatus().equals(SendStatus.SEND_OK)){
                log.info("redis 持久化");
                throw  new BusinessException(ResultCodeEnum.FAIL.getCode(),"Send message failed,message key："+message.getKey()+" "+sendResult.getSendStatus().name());

            }
            log.info("Send message success,key: {}",message.getKey());

        }catch (Exception e){
            //log.error(new Date()+"<<<<<<Send message failed,message key：{}",message.getKey(),"{}",e.getMessage());
            log.info("redis 持久化");
            throw  new BusinessException(ResultCodeEnum.FAIL.getCode(),"Send message failed,message key："+message.getKey()+" "+e.getMessage());


        }
        //producer.shutdown();

    }

    @Override
    public void asynSend(RocMqMessage message) {
        //DefaultMQProducer producer = build();
        try{
            Message msg = MessageBuilder.withPayload(message).setHeader("KEYS", message.getKey()).build();
            String destination = message.getTopic()+":"+message.getTags();
            rocketMQTemplate.asyncSend(destination,msg, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    log.info("Send message success,key: {}",message.getKey());
                }

                @Override
                public void onException(Throwable e) {
                    System.out.println("redis 持久化");
                    throw  new BusinessException(ResultCodeEnum.FAIL.getCode(),"Send message failed,message key："+message.getKey()+" "+e.getMessage());

                }
            });


        }catch (Exception e){
            //log.error(new Date()+"<<<<<<Send message failed,message key：{}",message.getKey(),"{}",e.getMessage());
            System.out.println("redis 持久化");
            throw  new BusinessException(ResultCodeEnum.FAIL.getCode(),"Send message failed,message key："+message.getKey()+" "+e.getMessage());


        }
        //producer.shutdown();

    }

    @Override
    public void orderSend(RocMqMessage message) {

    }

    @Transactional(propagation = Propagation.NESTED, rollbackFor = Exception.class)
    @Override
    public void delaySend(RocMqMessage message, Integer delayLevel) {
        //DefaultMQProducer producer = build();
        try{
            Message msg = MessageBuilder.withPayload(message).setHeader("KEYS", message.getKey()).build();
            //msg.setDelayTimeLevel(delayLevel);
            String destination = message.getTopic()+":"+message.getTags();
            SendResult sendResult = rocketMQTemplate.syncSend(destination,msg,1000,delayLevel);
            if(!sendResult.getSendStatus().equals(SendStatus.SEND_OK)){
                log.error("redis 持久化");
                throw  new BusinessException(ResultCodeEnum.FAIL.getCode(),"Send message failed,message key："+message.getKey()+" "+sendResult.getSendStatus().name());

            }
            log.info("Send message success,key: {}",message.getKey());

        }catch (Exception e){
            //log.error(new Date()+"<<<<<<Send message failed,message key：{}",message.getKey(),"{}",e.getMessage());
            log.error("redis 持久化");
            throw  new BusinessException(ResultCodeEnum.FAIL.getCode(),"Send message failed,message key："+message.getKey()+" "+e.getMessage());


        }
        //producer.shutdown();

    }
}
