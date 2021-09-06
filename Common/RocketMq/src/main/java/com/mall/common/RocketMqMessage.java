package com.mall.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName RocketMqMessage
 * @Description TODO
 * @Author liupanda
 * @Date 2021/9/6 22:48
 * @Version 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RocketMqMessage<T> {

    private String key;

    private String topic;

    private T  data;

    private String tags;
}
