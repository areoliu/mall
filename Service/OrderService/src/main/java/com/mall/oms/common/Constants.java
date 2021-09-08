package com.mall.oms.common;

import org.springframework.beans.factory.annotation.Value;

public class Constants {
    public static String ORDER_SKU_REDIS_PREFIX="Sku:";
    public static String ORDER_CART_REDIS_PREFIX="Cart:";
   // public static String ORDER_CART_SHOP_REDIS_PREFIX="Shop:";
    public static String ORDER_CART_SKU_REDIS_PREFIX="skuId:";

    public  static String getSkuKey(Long skuId) {
        return ORDER_SKU_REDIS_PREFIX+skuId;
    }

    public static String getCartKey(Long userId) {
        return ORDER_CART_REDIS_PREFIX+userId;
    }
//    public static String getCartShopKey(Long userId) {
//        return ORDER_CART_SHOP_REDIS_PREFIX+userId;
//    }

    public static String getCartSkuKey(Long skuId) {
        return ORDER_CART_SKU_REDIS_PREFIX+skuId;
    }
}
