package com.mall.stock.feign;

import com.mall.common.model.Result;
import com.mall.model.dto.StockDto;
import com.mall.stock.feign.fallback.StockFeignFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @ClassName StockFeign
 * @Description TODO
 * @Author liupanda
 * @Date 2021/8/27 22:48
 * @Version 1.0
 **/

@FeignClient(name="stock-service", path = "/sts",fallbackFactory = StockFeignFallbackFactory.class, configuration = FeignConfiguration.class)
public interface StockFeign {

    @RequestMapping(value = "/stock/lock", method = RequestMethod.POST)
    public Result lock(@RequestBody List<StockDto> list);

    @RequestMapping(value = "/stock/lock2", method = RequestMethod.GET)
    public Result lock2();

    @RequestMapping(value = "/stock/release", method = RequestMethod.POST)
    public Result release(@RequestBody List<StockDto> list);
}
