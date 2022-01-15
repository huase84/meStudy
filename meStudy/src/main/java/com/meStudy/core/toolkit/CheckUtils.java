package com.meStudy.core.toolkit;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * @Author wultn
 * @Description 校验工具类
 * @Date 21:28 2020/2/17
 * @Param
 * @return
 **/
public class CheckUtils {

    /**
     * @Author wultn
     * @Description 校验邮箱
     * @Date 15:24 2019/3/21
     * @Param [email]
     **/
    public static boolean checkEmail(String email) {// 验证邮箱的正则表达式
        final String format = "^[A-Za-z\\d]+([-_.][A-Za-z\\d]+)*@([A-Za-z\\d]+[-.])+[A-Za-z\\d]{2,5}$";
        //p{Alpha}:内容是必选的，和字母字符[\p{Lower}\p{Upper}]等价。如：200896@163.com不是合法的。
        //w{2,15}: 2~15个[a-zA-Z_0-9]字符；w{}内容是必选的。 如：dyh@152.com是合法的。
        //[a-z0-9]{3,}：至少三个[a-z0-9]字符,[]内的是必选的；如：dyh200896@16.com是不合法的。
        //[.]:'.'号时必选的； 如：dyh200896@163com是不合法的。
        //p{Lower}{2,}小写字母，两个以上。如：dyh200896@163.c是不合法的。
        if (email.matches(format)) {
            return true;// 邮箱名合法，返回true
        } else {
            return false;// 邮箱名不合法，返回false
        }
    }

    /**
     * @Author wultn
     * @Description 校验身份证号
     * @Date 16:40 2019/6/20
     * @Param [idCard]
     **/
    public static boolean checkIdCard (String idCard) {
        // 身份证号为15位或18位
        if( idCard.length() != 15 && idCard.length() != 18){
            return false;
        }
        // 除了最后一位，都是正整数
        Pattern pattern = Pattern.compile("^[+]{0,1}(\\d+)$");
        return pattern.matcher(idCard.substring(0, idCard.length()-1)).matches();
    }

    /**
     * @Author wultn
     * @Description 以1开头的11位整数
     * @Date 15:25 2019/3/21
     * @Param [str]
     **/
    public static boolean isPhoneLegal(String str) throws PatternSyntaxException {
        final String regExp = "^(1)\\d{10}$";
        final Pattern p = Pattern.compile(regExp);
        final Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * @Author wultn
     * @Description 大陆号码
     * @Date 15:25 2019/3/21
     * @Param [str]
     **/
    public static boolean isChinaPhoneLegal(String str) throws PatternSyntaxException {
        final String regExp = "^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$";
        final Pattern p = Pattern.compile(regExp);
        final Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * @Author wultn
     * @Description 香港手机号码8位数，5|6|8|9开头+7位任意数
     * @Date 15:25 2019/3/21
     * @Param [str]
     **/
    public static boolean isHKPhoneLegal(String str) throws PatternSyntaxException {
        final String regExp = "^(5|6|8|9)\\d{7}$";
        final Pattern p = Pattern.compile(regExp);
        final Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * @Author wultn
     * @Description 校验是否为IP
     * @Date 17:17 2019/10/15
     * @Param []
     **/
    public static boolean isIp (String str) {
        final String regExp = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
                + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
        final Pattern p = Pattern.compile(regExp);
        final Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * @Author wultn
     * @Description 校验字符串是否是纯英文
     * @Date 17:48 2019/10/15
     * @Param [str]
     **/
    public static boolean isEnglish (String str) {
        final String regExp = "[a-zA-Z]*";
        final Pattern p = Pattern.compile(regExp);
        final Matcher m = p.matcher(str);
        return m.matches();
    }
}
