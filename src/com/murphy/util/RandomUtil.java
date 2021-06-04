package com.murphy.util;

import java.util.Random;

/**
 * 生成随机数 - 工具类
 *
 * @author murphy
 * @since 2021/6/4 4:30 下午
 */
public class RandomUtil {
    private static Random r = new Random();
    public static int getCode() {
        return r.nextInt(900000) + 100000;
    }
}
