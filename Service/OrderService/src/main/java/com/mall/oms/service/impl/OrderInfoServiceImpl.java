package com.mall.oms.service.impl;

import com.mall.common.enums.ClientEnum;
import com.mall.common.enums.OrderEnum;
import com.mall.common.enums.PayEnum;
import com.mall.common.enums.ResultCodeEnum;
import com.mall.common.exception.BusinessException;
import com.mall.common.model.Result;
import com.mall.common.model.RocMqMessage;
import com.mall.common.service.RocMqProducerService;
import com.mall.common.util.RedisUtil;
import com.mall.common.util.SnowFlakeIdWorker;
import com.mall.model.dto.StockDto;
import com.mall.oms.dao.OrderInfoMapper;
import com.mall.oms.dao.OrderItemMapper;
import com.mall.oms.dto.CartSkuDto;
import com.mall.oms.entity.OrderInfo;
import com.mall.oms.entity.OrderItem;
import com.mall.oms.entity.Sku;

import com.mall.oms.service.CartService;
import com.mall.oms.service.OrderInfoService;
import com.mall.oms.service.SkuService;
import com.mall.stock.feign.StockFeign;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName OrderInfoServiceImpl
 * @Description TODO
 * @Author liupanda
 * @Date 2021/8/19 22:22
 * @Version 1.0
 **/
@Service
@Slf4j
public class OrderInfoServiceImpl implements OrderInfoService {

    @Resource
    CartService cartService;

    @Resource
    SkuService skuService;

    @Resource
    RedisUtil redisUtil;

    @Resource
    OrderInfoMapper orderInfoMapper;

    @Resource
    OrderItemMapper orderItemMapper;

    @Resource
    StockFeign stcokFeign;

    @Resource
    RocMqProducerService rocMqProducerService;

    @Value("${rocketmq.producer.group}")
    private String group;

    @Value("${rocketmq.producer.topic}")
    private String topic;

    @Value("${rocketmq.producer.tag}")
    private String tag;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(Long userId)  {
        //风控检查用户


        //从redis获取用户购物车数据
//        Map<Object , Object> cartMap = redisUtil.hGetAll(Constants.getCartKey(userId));
//        if(cartMap.isEmpty()){
//            throw new BusinessException(ResultCodeEnum.FAIL.getCode(),"购物车为空");
//
//        }
        List<CartSkuDto> cartSkuDtoList = cartService.getCartList(userId);
        if(cartSkuDtoList.size()==0){
            throw new BusinessException(ResultCodeEnum.FAIL.getCode(),"购物车为空");
        }
        List<OrderItem> orderItemList = new ArrayList<>();
        List<StockDto> stockDtos = new ArrayList<>();
        //订单信息
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setId(new SnowFlakeIdWorker(1,1).nextId());
        orderInfo.setCreateDate(new Date());
        orderInfo.setOrderStatus(OrderEnum.ORDER_NEW.getCode());
        orderInfo.setOrderSource(ClientEnum.ANDROID_APP.getCode());
        //商品详情
        for(CartSkuDto cartSkuDto: cartSkuDtoList){
            if(cartSkuDto.getIsChoose()==1){
                //cartSkuDtoList.add(cartSkuDto);
                Sku sku = skuService.getSku(cartSkuDto.getSkuId());
                if(sku==null|| sku.getStatus()!=1){
                    throw new BusinessException(ResultCodeEnum.FAIL.getCode(),sku.getSkuName()+"不存在或已下架");
                }
                OrderItem orderItem = new OrderItem();
                orderItem.setDiscountAmount(new BigDecimal(5));
                orderItem.setQuantity(cartSkuDto.getSkuNum());
                if(orderItem.getQuantity()>sku.getStock()){
                    throw new BusinessException(ResultCodeEnum.FAIL.getCode(),orderItem.getSkuName()+"库存不足");
                }
                orderItem.setMarketAmount(new BigDecimal(5));
                orderItem.setSkuPrice(new BigDecimal(110));
                orderItem.setSkuId(1L);
                orderItem.setSkuCode("1");
                orderItem.setOrderInfoId(orderInfo.getId());
                orderItemList.add(orderItem);
                StockDto stockDto = new StockDto();
                stockDto.setSkuId(cartSkuDto.getSkuId());
                stockDto.setSkuNum(cartSkuDto.getSkuNum());
                stockDtos.add(stockDto);

            }

        };

        //订单商品信息


        //收货信息
        orderInfo.setOrderUserId(1);
        orderInfo.setOrderUserName("liul");
        orderInfo.setPhone("13488857615");
        orderInfo.setProvince("北京");
        orderInfo.setCity("北京");
        orderInfo.setRegion("朝阳区");
        orderInfo.setAddress("sdfsfsfsdfsdfsd");


        //订单支付信息
        orderInfo.setOrderAmount(new BigDecimal(110));

        orderInfo.setPayAmount(new BigDecimal(100));
        if(orderInfo.getPayAmount().compareTo(new BigDecimal(0))==-1){
            orderInfo.setPayAmount(new BigDecimal(0));

        }
        orderInfo.setCouponsDiscountAmount(new BigDecimal(5));
        orderInfo.setMarketDiscountAmount(new BigDecimal(5));
        orderInfo.setDiscountAmount(new BigDecimal(10));
        orderInfo.setPayStatus(PayEnum.WAIT_PAY.getCode());

        //订单物流信息异步
        orderInfo.setShipName("京东");
        orderInfo.setShipNo("12312321");


        //创建订单
        orderInfoMapper.insert(orderInfo);
        orderItemMapper.insertBatch(orderItemList);




        //锁定库存
        try{
            Result result = stcokFeign.lock(stockDtos);
            if(result!=null&&!result.getCode().equals(ResultCodeEnum.SUCCESS.getCode())){
                throw new BusinessException(ResultCodeEnum.FAIL.getCode(),result.getMessage());
            }
        }catch (Exception e){
            //log.error(e.toString());
            throw new BusinessException(ResultCodeEnum.FAIL.getCode(),e.getMessage());
        }


        //删除购物车下单商品
        cartSkuDtoList.stream().forEach(cartSkuDto -> {
            cartService.delete(cartSkuDto);
        });

        RocMqMessage rocketMqMessage = new RocMqMessage();
        rocketMqMessage.setKey(orderInfo.getId()+"");
        rocketMqMessage.setData(orderInfo.getId());
        rocketMqMessage.setTags(tag);
        rocketMqMessage.setTopic(topic);
        rocketMqMessage.setGroup(group);

        //延时取消通知
        rocMqProducerService.delaySend(rocketMqMessage,5);

    }

    @Override
    public void cancel(Long orderId) {
        OrderInfo orderInfo = orderInfoMapper.selectById(orderId);
        if(null == orderInfo){
            throw new BusinessException(ResultCodeEnum.FAIL.getCode(),"订单不存在");
        }
        if(!orderInfo.getOrderStatus().equals( OrderEnum.ORDER__FINISH.getCode())){
            orderInfo.setOrderStatus(OrderEnum.ORDER__CANCEL.getCode());
            orderInfo.setUpdateDate(new Date());
            orderInfoMapper.updateById(orderInfo);

        }
        else{
            throw new BusinessException(ResultCodeEnum.FAIL.getCode(),"订单已取消");
        }

    }

    @Override
    public void pay(Long orderId) {

    }


}
