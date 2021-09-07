package com.mall.oms.controller;

import com.mall.common.enums.ResultCodeEnum;
import com.mall.common.exception.BusinessException;
import com.mall.common.log.LogAnno;
import com.mall.common.model.Result;
import com.mall.common.model.RocMqMessage;
import com.mall.common.service.RocMqProducerService;
import com.mall.model.dto.StockDto;
import com.mall.oms.service.OrderInfoService;
import com.mall.oms.vo.OrderVo;

import com.mall.stock.feign.StockFeign;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    OrderInfoService orderInfoService;

    @Resource
    StockFeign stockFeign;

    @Autowired
    RocMqProducerService rocMqProducerService;

    @PostMapping("/create/{userId}")
    @ApiOperation("创建订单")
    @LogAnno(fun="订单管理",des = "创建订单",type = "新增")
    public Result<OrderVo> CreateOrder(@PathVariable Long userId){
        orderInfoService.create(userId);
        return new Result().Success();

    }

    @GetMapping("/test")
    @ApiOperation("测试")
    @LogAnno(fun="订单管理",des = "创建订单",type = "新增")
    public Result<OrderVo> test(){
        List<StockDto> list =new ArrayList<>();
        StockDto stockDto = new StockDto();
        stockDto.setSkuNum(1);
        stockDto.setSkuId(1L);
        list.add(stockDto);
        Result result = stockFeign.lock(list);
        RocMqMessage rocketMqMessage = new RocMqMessage();
        rocketMqMessage.setKey("1111");
        rocketMqMessage.setData("1111");
        rocketMqMessage.setTopic("oms");
        List<RocMqMessage> rocMqMessages = new ArrayList<>();
        rocMqMessages.add(rocketMqMessage);
        //延时取消通知
        rocMqProducerService.delaySend(rocMqMessages,1);
        if(result==null){
            throw new BusinessException(ResultCodeEnum.FAIL.getCode(),"锁定库存失败");
        }
        if(!result.getCode().equals(ResultCodeEnum.SUCCESS.getCode())){
            throw new BusinessException(ResultCodeEnum.FAIL.getCode(),result.getMessage());
        }
        return new Result().Success();

    }


}
