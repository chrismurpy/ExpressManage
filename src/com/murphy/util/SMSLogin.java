package com.murphy.util;

/**
 * 模拟短信登录
 *
 * @author murphy
 * @since 2021/6/8 11:17 上午
 */
public class SMSLogin {
    /**
     * 模拟短信验证码 - 登录
     * @param phoneNumber
     * @param code
     * @return
     */
    public static boolean loginSMS(String phoneNumber, String code) {
        System.out.println("Phone: " + phoneNumber + "，您的验证码是 " + code);
        return true;
    }

    /**
     * 模拟短信验证码 - 修改
     * @param phoneNumber
     * @param code
     * @return
     */
    public static boolean updateSMS(String phoneNumber, String code) {
        System.out.println("Phone: " + phoneNumber + "，您的验证码是 " + code);
        return true;
    }
}
