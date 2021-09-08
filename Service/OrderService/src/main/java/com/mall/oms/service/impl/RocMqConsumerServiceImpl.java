package com.mall.oms.service.impl;

import com.mall.common.model.RocMqMessage;
import com.mall.common.service.RocMqConsumerService;
import com.mall.oms.entity.OrderInfo;
import com.mall.oms.service.OrderInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author liupanda
 */
@Slf4j
@Service("rocMqConsumerService")
public class RocMqConsumerServiceImpl implements RocMqConsumerService {

    @Autowired
    OrderInfoService orderInfoService;

    @Override
    public void consumer(RocMqMessage message) {
        log.info("消费消息1"+message.getData());
        Long orderInfoId = Long.parseLong(message.getData().toString());
        orderInfoService.cancel(orderInfoId);
        log.info("消费消息1成功");

    }


}
