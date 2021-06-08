package com.murphy.util;

/**
 * 模拟短信登录
 *
 * @author murphy
 * @since 2021/6/8 11:17 上午
 */
public class SMSLogin {
    public static boolean loginSMS(String phoneNumber,String code) {
        System.out.println("Phone: " + phoneNumber + "，您的验证码是 " + code);
        return true;
    }
}
