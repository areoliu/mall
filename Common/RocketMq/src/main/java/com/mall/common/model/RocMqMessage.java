package com.mall.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

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
public class RocMqMessage<T> implements Serializable {

    private String key;

    private String topic;

    private String group;

    private T  data;

    private String tags;
}
