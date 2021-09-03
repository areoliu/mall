package com.mall.stock.feign.fallback;

import com.mall.stock.feign.StockFeign;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @ClassName StockFeignFallbackFactory
 * @Description TODO
 * @Author liupanda
 * @Date 2021/9/2 23:20
 * @Version 1.0
 **/
@Component
public class StockFeignFallbackFactory implements FallbackFactory<StockFeign> {

    @Override
    public StockFeign create(Throwable throwable) {
        return new StockFeignFallback(throwable);
    }
}
