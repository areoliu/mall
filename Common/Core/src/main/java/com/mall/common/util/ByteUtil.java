package com.mall.common.util;

import java.io.*;

/**
 * @ClassName ByteUtil
 * @Description TODO
 * @Author liupanda
 * @Date 2021/9/6 23:00
 * @Version 1.0
 **/
public class ByteUtil {
    /**
     * 对象转字节数组
     */
    public static byte[] objectToBytes(Object obj) throws IOException {
        try(
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                ObjectOutputStream sOut = new ObjectOutputStream(out);
        ){
            sOut.writeObject(obj);
            sOut.flush();
            byte[] bytes = out.toByteArray();
            return bytes;
        }
    }

    /**
     * 字节数组转对象
     */
    public static Object bytesToObject(byte[] bytes) throws IOException, ClassNotFoundException {
        try(
                ByteArrayInputStream in = new ByteArrayInputStream(bytes);
                ObjectInputStream sIn = new ObjectInputStream(in);
        ){
            return sIn.readObject();
        }
    }
}
