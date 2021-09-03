package com.mall.stock.controller;

import com.mall.common.log.LogAnno;
import com.mall.common.model.Result;
import com.mall.stock.dto.StockDto;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName StockController
 * @Description TODO
 * @Author liupanda
 * @Date 2021/8/27 22:07
 * @Version 1.0
 **/

@Slf4j
@RequestMapping("/stock")
@RestController
public class StockController {


    @ApiOperation("锁定库存")
    @LogAnno(fun="库存",des = "锁定",type = "修改")
    @PostMapping("/lock")
    public Result lock(@RequestBody List<StockDto> list){
        log.info("system output loc");
        return  new Result().Success();

    }
}
