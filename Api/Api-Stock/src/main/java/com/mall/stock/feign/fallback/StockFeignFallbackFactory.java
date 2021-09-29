package com.mall.stock.feign.fallback;

import com.mall.common.model.Result;
import com.mall.model.dto.StockDto;
import com.mall.stock.feign.StockFeign;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ClassName StockFeignFallbackFactory
 * @Description TODO
 * @Author liupanda
 * @Date 2021/9/2 23:20
 * @Version 1.0
 **/
@Component
@Slf4j
public class StockFeignFallbackFactory implements FallbackFactory<StockFeign> {

    @Override
    public StockFeign create(Throwable throwable) {
        return new StockFeign() {
            @Override
            public Result lock(List<StockDto> list) {
                return new Result().Fail("服务异常，锁定库存失败");
            }

            @Override
            public Result lock2() {
                return new Result().Fail("服务异常，锁定库存失败");
            }

            @Override
            public Result release(List<StockDto> list) {
                return new Result().Fail("服务异常，释放库存失败");
            }
        };
    }
}
