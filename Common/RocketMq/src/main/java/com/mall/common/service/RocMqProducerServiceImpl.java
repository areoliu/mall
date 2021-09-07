package com.mall.common.service;


import com.mall.common.model.RocMqMessage;
import com.mall.common.enums.ResultCodeEnum;
import com.mall.common.exception.BusinessException;
import com.mall.common.util.ByteUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.apache.rocketmq.common.message.Message;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service("rocMqProducerServiceImpl")
public class RocMqProducerServiceImpl implements RocMqProducerService {


    @Autowired
    DefaultMQProducer producer;


    @Override
    public void synSend(List<RocMqMessage> messages) throws MQClientException {
        //DefaultMQProducer producer = build();
        for(RocMqMessage message:messages){
            try{
                Message msg = new Message(message.getTopic(), message.getTags(), message.getKey(),ByteUtil.objectToBytes(message.getData()));
                SendResult sendResult = producer.send(msg);
                if(!sendResult.getSendStatus().equals(SendStatus.SEND_OK)){
                    System.out.println("redis 持久化");
                    throw  new BusinessException(ResultCodeEnum.FAIL.getCode(),"Send message failed,message key："+message.getKey()+" "+sendResult.getSendStatus().name());

                }
                log.info("Send message success,key: {}",msg.getKeys());

            }catch (Exception e){
                //log.error(new Date()+"<<<<<<Send message failed,message key：{}",message.getKey(),"{}",e.getMessage());
                System.out.println("redis 持久化");
                throw  new BusinessException(ResultCodeEnum.FAIL.getCode(),"Send message failed,message key："+message.getKey()+" "+e.getMessage());


            }

        }
        producer.shutdown();

    }

    @Override
    public void asynSend(List<RocMqMessage> messages ) {
        //DefaultMQProducer producer = build();
        for(RocMqMessage message:messages){
            try{
                Message msg = new Message(message.getTopic(), message.getTags(), message.getKey(),ByteUtil.objectToBytes(message.getData()));
                producer.send(msg, new SendCallback() {
                    @Override
                    public void onSuccess(SendResult sendResult) {
                        log.info("Send message success,key: {}",msg.getKeys());
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

        }
        producer.shutdown();

    }

    @Override
    public void orderSend(List<RocMqMessage> messages) {

    }

    @Override
    public void delaySend(List<RocMqMessage> messages, Integer delayLevel) {
        //DefaultMQProducer producer = build();
        for(RocMqMessage message:messages){
            try{
                Message msg = new Message(message.getTopic(), message.getTags(), message.getKey(),ByteUtil.objectToBytes(message.getData()));
                msg.setDelayTimeLevel(delayLevel);
                SendResult sendResult = producer.send(msg);
                if(!sendResult.getSendStatus().equals(SendStatus.SEND_OK)){
                    log.error("redis 持久化");
                    throw  new BusinessException(ResultCodeEnum.FAIL.getCode(),"Send message failed,message key："+message.getKey()+" "+sendResult.getSendStatus().name());

                }
                log.info("Send message success,key: {}",msg.getKeys());

            }catch (Exception e){
                //log.error(new Date()+"<<<<<<Send message failed,message key：{}",message.getKey(),"{}",e.getMessage());
                log.error("redis 持久化");
                throw  new BusinessException(ResultCodeEnum.FAIL.getCode(),"Send message failed,message key："+message.getKey()+" "+e.getMessage());


            }

        }
        producer.shutdown();

    }


}
