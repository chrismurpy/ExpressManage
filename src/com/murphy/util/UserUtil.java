package com.murphy.util;

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
        return "";
    }

    /**
     * TODO: 未编写柜子端，未存储任何的录入人信息
     * @param session
     * @return
     */
    public static String getSysPhone(HttpSession session) {
        return "18888888888";
    }
}
