package com.mall.stock.feign;

import com.mall.stock.dto.StockDto;

import com.mall.common.model.Result;
import com.mall.stock.feign.fallback.StockFeignFallback;
import com.mall.stock.feign.fallback.StockFeignFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @ClassName StockFeign
 * @Description TODO
 * @Author liupanda
 * @Date 2021/8/27 22:48
 * @Version 1.0
 **/

@FeignClient(value="stock-service", fallbackFactory = StockFeignFallbackFactory.class)
public interface StockFeign {

    @PostMapping("/stock/lock")
    public Result lock(@RequestBody List<StockDto> list);
}
