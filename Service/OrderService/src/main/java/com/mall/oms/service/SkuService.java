package com.mall.oms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.oms.entity.Sku;

/**
 * @ClassName SkuService
 * @Description TODO
 * @Author liupanda
 * @Date 2021/8/26 22:22
 * @Version 1.0
 **/
public interface SkuService extends IService<Sku> {

    public Sku getSku(Long skuId);
}
