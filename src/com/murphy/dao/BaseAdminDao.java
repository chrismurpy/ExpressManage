package com.murphy.dao;

import java.util.Date;

/**
 * 用于定义eAdmin表格的操作规范
 * @author murphy
 */
public interface BaseAdminDao {
    /**
     * 根据用户名，更新登录时间和登录IP
     * @param username
     * @param date
     * @param ip
     */
    void updateLoginTime(String username, Date date,String ip);

    /**
     * 管理员根据账号密码登录
     * @param username
     * @param password
     * @return 登录的结果，true表示登录成功
     */
    boolean login(String username, String password);
}
