package com.mall.oms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
import com.mall.oms.dto.CartSkuDto;
import com.mall.oms.dto.ConfirmOrderDto;
import com.mall.oms.entity.Coupon;
import com.mall.oms.dto.OrderPara;
import com.mall.oms.entity.ReceiveAddress;
import com.mall.oms.entity.OrderInfo;
import com.mall.oms.entity.OrderItem;
import com.mall.oms.entity.Sku;

import com.mall.oms.service.CartService;
import com.mall.oms.service.OrderInfoService;
import com.mall.oms.service.OrderItemService;
import com.mall.oms.service.SkuService;
import com.mall.stock.feign.StockFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName OrderInfoServiceImpl
 * @Description TODO
 * @Author liupanda
 * @Date 2021/8/19 22:22
 * @Version 1.0
 **/
@Service
@Slf4j
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoMapper, OrderInfo> implements OrderInfoService {

    @Resource
    CartService cartService;

    @Resource
    SkuService skuService;

    @Resource
    RedisUtil redisUtil;

    @Resource
    OrderInfoMapper orderInfoMapper;

    @Resource
    OrderItemService orderItemService;

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
    public ConfirmOrderDto confirmOrder() {
        //????????????
        ConfirmOrderDto result = new ConfirmOrderDto();
        //??????????????????
        List<CartSkuDto> cartSkuDtoList = cartService.getCartList(1L);
        if(cartSkuDtoList.size()==0){
            throw new BusinessException(ResultCodeEnum.FAIL.getCode(),"???????????????");
        }
        //????????????????????????
        List<ReceiveAddress> receiveAddressList = new ArrayList<>();
        //?????????????????????
        List<Coupon> couponList = new ArrayList<>();
        //??????????????????????????????
        return result;



    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> createOrder(OrderPara orderPara)  {
        //??????????????????

        Long userId =1L;
        List<CartSkuDto> cartSkuDtoList = cartService.getCartList(userId);
        if(cartSkuDtoList.size()==0){
            throw new BusinessException(ResultCodeEnum.FAIL.getCode(),"???????????????");
        }
        List<OrderItem> orderItemList = new ArrayList<>();
        List<StockDto> stockDtos = new ArrayList<>();
        //????????????
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setId(new SnowFlakeIdWorker(1,1).nextId());
        orderInfo.setCreateDate(new Date());
        orderInfo.setOrderStatus(OrderEnum.ORDER_NEW.getCode());
        orderInfo.setOrderSource(ClientEnum.ANDROID_APP.getCode());

        //????????????
        for(CartSkuDto cartSkuDto: cartSkuDtoList){
            if(cartSkuDto.getIsChoose()==1){
                //cartSkuDtoList.add(cartSkuDto);
                Sku sku = skuService.getSku(cartSkuDto.getSkuId());
                if(sku==null|| sku.getStatus()!=1){
                    throw new BusinessException(ResultCodeEnum.FAIL.getCode(),sku.getSkuName()+"?????????????????????");
                }
                OrderItem orderItem = new OrderItem();
                orderItem.setDiscountAmount(new BigDecimal(5));
                orderItem.setQuantity(cartSkuDto.getSkuNum());
                orderItem.setUserId(cartSkuDto.getUserId());
                if(orderItem.getQuantity()>sku.getStock()){
                    throw new BusinessException(ResultCodeEnum.FAIL.getCode(),orderItem.getSkuName()+"????????????");
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
                orderInfo.setOrderUserId(cartSkuDto.getUserId());

            }

        };

        //??????????????????


        //????????????
        //orderInfo.setOrderUserId(1);
        orderInfo.setOrderUserName("liul");
        orderInfo.setPhone("13488857615");
        orderInfo.setProvince("??????");
        orderInfo.setCity("??????");
        orderInfo.setRegion("?????????");
        orderInfo.setAddress("sdfsfsfsdfsdfsd");


        //??????????????????
        orderInfo.setOrderAmount(new BigDecimal(110));

        orderInfo.setPayAmount(new BigDecimal(100));
        if(orderInfo.getPayAmount().compareTo(new BigDecimal(0))==-1){
            orderInfo.setPayAmount(new BigDecimal(0));

        }
        orderInfo.setCouponsDiscountAmount(new BigDecimal(5));
        orderInfo.setMarketDiscountAmount(new BigDecimal(5));
        orderInfo.setDiscountAmount(new BigDecimal(10));
        orderInfo.setPayStatus(PayEnum.WAIT_PAY.getCode());

        //????????????????????????
        orderInfo.setShipName("??????");
        orderInfo.setShipNo("12312321");


        //????????????
        orderInfoMapper.insert(orderInfo);
        orderItemService.insertBatch(orderItemList);




        //????????????
        try{
            Result result = stcokFeign.lock(stockDtos);
            if(result!=null&&!result.getCode().equals(ResultCodeEnum.SUCCESS.getCode())){
                throw new BusinessException(ResultCodeEnum.FAIL.getCode(),result.getMessage());
            }
        }catch (Exception e){
            //log.error(e.toString());
            throw new BusinessException(ResultCodeEnum.FAIL.getCode(),e.getMessage());
        }


        //???????????????????????????
        cartSkuDtoList.stream().forEach(cartSkuDto -> {
            cartService.delete(cartSkuDto);
        });

        RocMqMessage rocketMqMessage = new RocMqMessage();
        rocketMqMessage.setKey(orderInfo.getId()+"");
        rocketMqMessage.setData(orderInfo.getId());
        rocketMqMessage.setTags(tag);
        rocketMqMessage.setTopic(topic);
        rocketMqMessage.setGroup(group);

        //??????????????????
        rocMqProducerService.delaySend(rocketMqMessage,5);
        Map<String, Object> map = new ConcurrentHashMap<>();
        map.put("order",orderInfo);


        return map;

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancelTimeOutOOrder(Long orderId) {
        OrderInfo orderInfo = orderInfoMapper.selectById(orderId);
        if(null == orderInfo){
            throw new BusinessException(ResultCodeEnum.FAIL.getCode(),"???????????????");
        }
        if(orderInfo.getOrderStatus().equals( OrderEnum.ORDER__FINISH.getCode())){
            throw new BusinessException(ResultCodeEnum.FAIL.getCode(),"??????????????????????????????");

        }
        if(orderInfo.getOrderStatus().equals( OrderEnum.ORDER__CANCEL.getCode())){
            throw new BusinessException(ResultCodeEnum.FAIL.getCode(),"???????????????");
        }
        if(!orderInfo.getOrderStatus().equals( OrderEnum.ORDER_NEW.getCode())){
            orderInfo.setOrderStatus(OrderEnum.ORDER__CANCEL.getCode());
            orderInfo.setUpdateDate(new Date());
            orderInfoMapper.updateById(orderInfo);

        }
        else{
            throw new BusinessException(ResultCodeEnum.FAIL.getCode(),"????????????????????????????????????");
        }


    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancelOrder(Long orderId) {
        OrderInfo orderInfo = orderInfoMapper.selectById(orderId);
        if(null == orderInfo){
            throw new BusinessException(ResultCodeEnum.FAIL.getCode(),"???????????????");
        }
        if(orderInfo.getOrderStatus().equals( OrderEnum.ORDER__FINISH.getCode())){
            throw new BusinessException(ResultCodeEnum.FAIL.getCode(),"??????????????????????????????");
        }
        if(orderInfo.getOrderStatus().equals( OrderEnum.ORDER__CANCEL.getCode())){
            throw new BusinessException(ResultCodeEnum.FAIL.getCode(),"???????????????");
        }
        if(orderInfo.getOrderStatus().equals( OrderEnum.ORDER_NEW.getCode())){
            throw new BusinessException(ResultCodeEnum.FAIL.getCode(),"????????????????????????????????????");
        }
        else{
            //????????????
            orderInfo.setOrderStatus(OrderEnum.ORDER__CANCEL.getCode());
            orderInfo.setUpdateDate(new Date());
            orderInfoMapper.updateById(orderInfo);
            //????????????


            List<StockDto> list = new ArrayList<>();
            //????????????
            stcokFeign.release(list);
        }

    }

    @Override
    public Map<String, Object> pay(Long orderId) {

        OrderInfo orderInfo = orderInfoMapper.selectById(orderId);
        if(null == orderInfo){
            throw new BusinessException(ResultCodeEnum.FAIL.getCode(),"???????????????");
        }
        if(orderInfo.getOrderStatus().equals( OrderEnum.ORDER__CANCEL.getCode())){
            throw new BusinessException(ResultCodeEnum.FAIL.getCode(),"???????????????");
        }
        if(orderInfo.getOrderStatus().equals( OrderEnum.ORDER_NEW.getCode())){
            orderInfo.setOrderStatus(OrderEnum.ORDER_PAY.getCode());
            orderInfoMapper.updateById(orderInfo);
            Map<String, Object> map = new ConcurrentHashMap<>();
            map.put("order",orderInfo);
            return map;
        }
        else{
            throw new BusinessException(ResultCodeEnum.FAIL.getCode(),"???????????????????????????????????????????????????");
        }

    }

    @Override
    public void deleteOrder(Long orderId) {
        OrderInfo orderInfo = orderInfoMapper.selectById(orderId);
        if(null == orderInfo){
            throw new BusinessException(ResultCodeEnum.FAIL.getCode(),"???????????????");
        }
        if(orderInfo.getIsDelete()==0){
            orderInfo.setIsDelete(1);

        }

    }


}
