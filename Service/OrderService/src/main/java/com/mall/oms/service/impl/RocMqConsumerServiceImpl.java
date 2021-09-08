package com.mall.oms.service.impl;

import com.mall.common.model.RocMqMessage;
import com.mall.common.service.RocMqConsumerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service("rocMqConsumerService")
public class RocMqConsumerServiceImpl implements RocMqConsumerService {
    @Override
    public void consumer(RocMqMessage message) {
        log.info("消费消息1"+message.getData());

    }


}
