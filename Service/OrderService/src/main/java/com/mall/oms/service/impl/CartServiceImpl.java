package com.mall.oms.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.common.enums.CartEnum;
import com.mall.common.enums.ResultCodeEnum;
import com.mall.common.enums.SkuEnmu;
import com.mall.common.exception.BusinessException;
import com.mall.common.util.RedisUtil;
import com.mall.oms.common.Constants;
import com.mall.oms.dao.CartMapper;
import com.mall.oms.dao.OrderInfoMapper;
import com.mall.oms.dto.CartSkuDto;
import com.mall.oms.entity.Cart;
import com.mall.oms.entity.OrderInfo;
import com.mall.oms.entity.Sku;
import com.mall.oms.service.CartService;
import com.mall.oms.service.SkuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springfox.documentation.spring.web.json.Json;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class CartServiceImpl extends ServiceImpl<CartMapper, Cart> implements CartService {

//    @Resource
//    CartMapper orderCartMapper;

    @Resource
    RedisUtil redisUtil;

    @Autowired
    SkuService skuService;


    @Transactional(rollbackFor = Exception.class)
    @Override
    public  void addCart(CartSkuDto cartDto) {
       // log.info("{}",Constants.getSkuKey(cartDto.getSkuId()));
        //从缓存获取商品信息
       Sku sku = skuService.getSku(cartDto.getSkuId());
//        CartSkuDto shopCartSkuDto = new CartSkuDto();
//        BeanUtils.copyProperties(sku,shopCartSkuDto);
//        shopCartSkuDto.setSkuId(sku.getId());
        //商品是否上架可用
        if(sku.getStatus().equals( SkuEnmu.OFF.getCode())||
                sku.getStatus().equals(SkuEnmu.DELETE.getCode())){
            throw new BusinessException(ResultCodeEnum.FAIL.getCode(),"操作失败，商品已下架或已删除");
        }
        //添加数量是否超过库存数
        if(cartDto.getSkuNum()>sku.getStock()){
            throw new BusinessException(ResultCodeEnum.FAIL.getCode(),"操作失败，添加数量超过库存数");
        }
        //最大添加数量
        if(cartDto.getSkuNum()>sku.getMax()){
            throw new BusinessException(ResultCodeEnum.FAIL.getCode(),"操作失败，添加数量超过上限");
        }
        //CartSkuDto cart = new CartSkuDto();
        //cart.setStatus(true);
        //购物车是否存在本商品
        CartSkuDto cart = (CartSkuDto) redisUtil.hGet(Constants.getCartKey(cartDto.getUserId()) , Constants.getCartSkuKey(cartDto.getSkuId()));
        if(null == cart){
            cartDto.setIsChoose(CartEnum.CHECKED.getCode());
            redisUtil.hSet(Constants.getCartKey(cartDto.getUserId()) , Constants.getCartSkuKey(cartDto.getSkuId()),cartDto);
        }
        else{
            int count = cart.getSkuNum()+cartDto.getSkuNum();
            //如果数量为0删除redis中的记录
            if(count == 0){
                redisUtil.hDel(Constants.getCartKey(cartDto.getUserId()) , Constants.getCartSkuKey(cartDto.getSkuId()));
            }
            else{
                cartDto.setSkuNum(count);
                cartDto.setIsChoose(CartEnum.CHECKED.getCode());
                redisUtil.hSet(Constants.getCartKey(cartDto.getUserId()) , Constants.getCartSkuKey(cartDto.getSkuId()),cartDto);
               // redisUtil.zAdd(Constants.getCartShopKey(cartDto.getUserId()),cartDto.getShopId(),System.currentTimeMillis());
            }
        }


    }

    @Override
    public void clear(Long userId) {
        //判断购物车存在
        if(redisUtil.hasKey(Constants.getCartKey(userId))){
           Boolean ret = redisUtil.del(Constants.getCartKey(userId));
           if(!ret){
               throw new BusinessException(ResultCodeEnum.FAIL.getCode(),"操作失败，未知错误");
           }
        }
        else{
            throw new BusinessException(ResultCodeEnum.FAIL.getCode(),"操作失败，购物车已清空");
        }

    }

    @Override
    public List<CartSkuDto> getCartList(Long userId) {
        //根据用户id获取缓存购物车列表
        Map<Object , Object> cartMap = redisUtil.hGetAll(Constants.getCartKey(userId));
//        if(cartMap.isEmpty()){
//            throw new BusinessException(ResultCodeEnum.FAIL.getCode(),"购物车为空");
//        }
        List<CartSkuDto> shopCartDtoList = new ArrayList<>();
        cartMap.forEach((key,value)->{
            CartSkuDto cartDto = (CartSkuDto) value;

            Sku sku = skuService.getSku(cartDto.getSkuId());
            BeanUtils.copyProperties(sku, cartDto);
            shopCartDtoList.add(cartDto);
        });
        return shopCartDtoList;

    }

    @Override
    public void delete(CartSkuDto cartDto) {
        //查询购物车是否存在该商品
        if(redisUtil.hHasKey(Constants.getCartKey(cartDto.getUserId()) , Constants.getCartSkuKey(cartDto.getSkuId()))){
            redisUtil.hDel(Constants.getCartKey(cartDto.getUserId()) , Constants.getCartSkuKey(cartDto.getSkuId()));
        }
        else{
            throw new BusinessException(ResultCodeEnum.FAIL.getCode(),"操作失败，该商品已删除");
        }
    }

    @Override
    public void check(CartSkuDto cartDto) {
        //查询购物车是否存在该商品
        CartSkuDto cartDto2 = (CartSkuDto) redisUtil.hGet(Constants.getCartKey(cartDto.getUserId()) , Constants.getCartSkuKey(cartDto.getSkuId()));
        if(null != cartDto2){
            cartDto2.setIsChoose(cartDto.getIsChoose());
            redisUtil.hSet(Constants.getCartKey(cartDto.getUserId()) , Constants.getCartSkuKey(cartDto.getSkuId()),cartDto2);
        }
        else{
            throw new BusinessException(ResultCodeEnum.FAIL.getCode(),"操作失败，购物车不存在该商品");
        }

    }

    @Override
    public void checkShop(Long userId, Long shopId) {

    }

    @Override
    public void checkAll(Long userId, Integer isChoose) {
        //
        Map<Object,Object> map = redisUtil.hGetAll(Constants.getCartKey(userId));
        if(!map.isEmpty()){
            map.forEach((key , value)->{
                CartSkuDto cartDto = (CartSkuDto) value;
                cartDto.setIsChoose(isChoose);
                redisUtil.hSet(Constants.getCartKey(userId),JSON.toJSONString(key),cartDto);

            });
        }
        else{
            throw new BusinessException(ResultCodeEnum.FAIL.getCode(),"操作失败，购物车不存在商品");
        }
    }
}
