package com.mall.common.service;

import com.mall.common.RocketMqMessage;

/**
 * @ClassName MessageService
 * @Description TODO
 * @Author liupanda
 * @Date 2021/9/6 23:21
 * @Version 1.0
 **/
public interface MessageService {

    public void add(RocketMqMessage rocketMqMessage);
}
