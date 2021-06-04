package com.murphy.dao.impl;

import com.murphy.dao.BaseAdminDao;
import com.murphy.util.DruidUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * eAdmin Dao的实现类
 * @author murphy
 */
public class AdminDaoMysql implements BaseAdminDao {
    private static final String SQL_UPDATE_LOGIN_TIME = "UPDATE EADMIN SET LOGINTIME = ?," +
            "LOGINIP = ? WHERE USERNAME = ?";
    private static final String SQL_LOGIN = "SELECT ID FROM eAdmin WHERE username = ? AND password = ?";

    /**
     * 根据用户名，更新登录时间和登录IP
     *
     * @param username
     * @param date
     * @param ip
     */
    @Override
    public void updateLoginTime(String username, Date date, String ip) {
        // 1. 获取连接
        Connection conn = DruidUtil.getConnection();
        PreparedStatement ps = null;
        // 2. 预编译SQL语句
        try {
            ps = conn.prepareStatement(SQL_UPDATE_LOGIN_TIME);
            // 3. 填充参数
            ps.setDate(1,new java.sql.Date(date.getTime()));
            ps.setString(2,ip);
            ps.setString(3,username);
            // 4. 执行
            ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            // 5. 释放资源
            DruidUtil.close(conn,ps,null);
        }
    }

    /**
     * 管理员根据账号密码登录
     *
     * @param username
     * @param password
     * @return 登录的结果，true表示登录成功
     */
    @Override
    public boolean login(String username, String password) {
        // 1. 获取连接
        Connection conn = DruidUtil.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        // 2. 预编译SQL语句
        try {
            ps = conn.prepareStatement(SQL_LOGIN);
            // 3. 填充参数
            ps.setString(1,username);
            ps.setString(2,password);
            // 4. 执行并获取结果
            rs = ps.executeQuery();
            // 5. 根据查询结果，返回true或false
            return rs.next();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            // 6. 释放资源
            DruidUtil.close(conn,ps,rs);
        }
        return false;
    }
}
