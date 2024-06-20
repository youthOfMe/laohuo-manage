package com.laohuo.company.util;

/**
 * 密码校验工具类
 */
public class PassWordUtil {

    /**
     * 判断字符串是否同时包含数字和字母
     * @param str  传入的校验字符串
     * @return true 正确 false 错误
     */
    public static boolean isLetterDigit(String str) {
        String regex = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]+$";
        return str.matches(regex);
    }

}
