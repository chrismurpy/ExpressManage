package com.murphy.util;

import com.murphy.bean.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * User - 工具类
 *
 * @author murphy
 * @since 2021/6/4 6:56 下午
 */
public class UserUtil {
    public static String getUserName(HttpSession session) {
        return (String) session.getAttribute("adminUserName");
    }

    /**
     * TODO: 未编写柜子端，未存储任何的录入人信息
     * @param session
     * @return
     */
    public static String getSysPhone(HttpSession session) {
        return "18888888888";
    }

    public static String getLoginSms(HttpSession session, String userPhone) {
        return (String) session.getAttribute(userPhone);
    }

    public static void setLoginSms(HttpSession session, String userPhone, String code) {
        session.setAttribute(userPhone, code);
    }

    public static void setWxUser(HttpSession session, User user){
        session.setAttribute("wxUser",user);
    }

    public static User getWxUser(HttpSession session){
        return (User) session.getAttribute("wxUser");
    }
}
