package com.mall.oms.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.common.enums.ResultCodeEnum;
import com.mall.common.exception.BusinessException;
import com.mall.common.util.RedisUtil;
import com.mall.oms.common.Constants;
import com.mall.oms.dao.SkuMapper;
import com.mall.oms.entity.Sku;
import com.mall.oms.service.SkuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @ClassName SkuServiceImpl
 * @Description TODO
 * @Author liupanda
 * @Date 2021/8/26 22:23
 * @Version 1.0
 **/
@Service
public class SkuServiceImpl extends ServiceImpl<SkuMapper, Sku> implements SkuService {
    @Resource
    RedisUtil redisUtil;

    @Override
    public Sku getSku(Long skuId) {
        Map<Object,Object> map = redisUtil.hGetAll(Constants.getSkuKey(skuId));
        //  String json = JSON.toJSONString(map);
        Sku sku =null;
        //从数据库获取商品信息
        if(map.isEmpty()){
            throw new BusinessException(ResultCodeEnum.FAIL.getCode(),"操作失败，没有该商品信息");
        }
        else{
            //sku = JSON.parseObject(json,Sku.class);
            sku = JSON.parseObject(JSON.toJSONString(map), Sku.class);
        }
        return sku;
    }
}
