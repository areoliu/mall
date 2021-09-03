package com.mall.oms.controller;

import com.mall.common.log.LogAnno;
import com.mall.common.model.Result;
import com.mall.common.util.RedisUtil;
import com.mall.oms.common.Constants;
import com.mall.oms.dto.CartSkuDto;
import com.mall.oms.service.CartService;
import com.mall.oms.vo.CartSkuVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequestMapping("/cart")
@RestController
@Api( "购物车接口")
public class CartController {

    @Resource
    CartService orderCartService;

    @Resource
    RedisUtil redisUtil;

    @PostMapping("/add")
    @ApiOperation("添加购物车")
    @LogAnno(fun="购物车",des = "添加购物车",type = "新增")
    public Result addCart(@Validated @RequestBody CartSkuDto cartDto){
        orderCartService.addCart(cartDto);
        return new Result().Success();
    }

    @PostMapping("/clear/{userId}")
    @ApiOperation("清空购物车")
    @LogAnno(fun="购物车",des = "清空购物车",type = "删除")
    public Result clearCart(@PathVariable Long userId){
        orderCartService.clear(userId);
        return new Result().Success();
    }

    @PostMapping("/delete")
    @ApiOperation("删除购物车")
    @LogAnno(fun="购物车",des = "删除购物车",type = "删除")
    public Result deleteCart(@Validated @RequestBody CartSkuDto cartDto){
        orderCartService.delete(cartDto);
        return new Result().Success();
    }

    @PostMapping("/getCartList/{userId}")
    @ApiOperation("获取购物车列表")
    @LogAnno(fun="购物车",des = "获取购物车列表",type = "查询")
    public Result getCartList(@PathVariable Long userId ){
        List<CartSkuDto> cartSkuDtoList = orderCartService.getCartList(userId);
        List<CartSkuVo> cartSkuVoList = new ArrayList<>();
        for(CartSkuDto cartSkuDto : cartSkuDtoList){
            CartSkuVo cartSkuVo = new CartSkuVo();
            BeanUtils.copyProperties(cartSkuDto, cartSkuVo);
            cartSkuVoList.add(cartSkuVo);
        }
        return new Result().Success(cartSkuVoList);
    }


    @PostMapping("/test")
    @ApiOperation("购物车")
    public Result test( ){
        log.error("#################################test order cart service");
//        redisUtil.hSet("Sku:1","skuCode","11011");
//        redisUtil.hSet("Sku:1","skuId","11011");
//        redisUtil.hSet("Sku:1","skuName","test");
//        redisUtil.hSet("Sku:1","skuPrice","10");
//        redisUtil.hSet("Sku:1","skuImg","www.baidu.comn");
//        redisUtil.hSet("Sku:1","stock","100");
        redisUtil.hGetAll(Constants.getSkuKey(1L));
        return new Result().Success();
    }

    @PostMapping("/test2")
    @ApiOperation("购物车")
    public Result test2( ){
        log.error("#################################test order cart service");
        redisUtil.hSet("Sku:1","skuCode",11011);
        redisUtil.hSet("Sku:1","skuId",11011);
        redisUtil.hSet("Sku:1","skuName","test");
        redisUtil.hSet("Sku:1","skuPrice",10);
        redisUtil.hSet("Sku:1","skuImg","www.baidu.comn");
        redisUtil.hSet("Sku:1","stock",100);
        redisUtil.hSet("Sku:1","max",2);
        redisUtil.hSet("Sku:1","shopId",112);
        redisUtil.hSet("Sku:1","shopName","shop1");
        redisUtil.hSet("Sku:1","status",1);


        return new Result().Success();
    }
}
