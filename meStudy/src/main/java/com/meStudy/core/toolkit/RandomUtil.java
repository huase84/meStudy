package com.meStudy.core.toolkit;

import java.util.Random;

/**
 * @Author wultn
 * @Description 随机生成数值的工具类
 * @Date 15:01 2020/2/13
 **/
public class RandomUtil {
    /** 所有的字符串，包括数字、小写字母、大写字母 */
    private static final String ALL_CHAR = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /**
     * @Author wultn
     * @Description 生成一个包含大小写字母、数字的定长随机字符串
     * @Date 15:12 2020/2/13
     * @Param 生成随机字符串的长度
     * @return 返回生成的随机字符串
     **/
    public static String generateString(int length) {
        StringBuilder result = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            result.append(ALL_CHAR.charAt(random.nextInt(ALL_CHAR.length())));
        }
        return result.toString();
    }

}
