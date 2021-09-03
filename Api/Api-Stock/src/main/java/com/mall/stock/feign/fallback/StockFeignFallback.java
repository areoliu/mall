package com.mall.stock.feign.fallback;

import com.mall.stock.dto.StockDto;
import com.mall.stock.feign.StockFeign;
import com.mall.common.model.Result;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ClassName StockFeignFallback
 * @Description TODO
 * @Author liupanda
 * @Date 2021/8/28 21:43
 * @Version 1.0
 **/

public class StockFeignFallback implements StockFeign {
    private Throwable throwable;

    StockFeignFallback(Throwable throwable) {
        this.throwable = throwable;
    }

    @Override
    public Result lock(List<StockDto> list) {
        return new Result().Fail("服务异常，锁定库存失败");
    }
}
