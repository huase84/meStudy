package com.meStudy.core.toolkit;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * @ClassName: MD5Utils
 * @Description: MD5
 * @Author wultn
 * @Date 2020/2/10 21:02
 */
public class MD5Utils {

    /**
     * @Author wultn
     * @Description 根据条件MD5加密用户密码密码
     * @Date 21:02 2020/2/10
     * @Param [account, password, salt]
     * @return java.lang.String
     **/
    public  static String encryptPassword(String account, String password, String salt) {
        String builder = account + password + salt;
        return MD5Utils.hash(builder);
    }

    private static String hash(String s) {
        if (null == s || s.equals("")) {
            return "";
        }
        return hash(s, "UTF-8");
    }

    private static String hash(String s, String charsetName) {
        try {
            return new String(toHex(md5(s)).getBytes(charsetName), charsetName);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private static byte[] md5(String s) {
        try {
            MessageDigest algorithm = MessageDigest.getInstance("MD5");
            algorithm.reset();
            algorithm.update(s.getBytes(StandardCharsets.UTF_8));
            return algorithm.digest();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private static String toHex(byte[] hash) {
        if (null == hash) {
            return null;
        }

        StringBuilder buf = new StringBuilder(hash.length * 2);
        int i;

        for (i = 0; i < hash.length; i++) {
            if ((hash[i] & 0xff) < 0x10) {
                buf.append("0");
            }
            buf.append(Long.toString(hash[i] & 0xff, 16));
        }
        return buf.toString();
    }

    public static String encryptPassword(String password) {
        return MD5Utils.hash(password);
    }

    public static boolean judge(String password, String oldPassword) {
        return password.equals(encryptPassword(oldPassword));
    }
}
