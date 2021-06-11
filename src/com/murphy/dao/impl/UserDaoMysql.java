package com.murphy.dao.impl;

import com.murphy.bean.User;
import com.murphy.dao.BaseUserDao;
import com.murphy.util.DruidUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User - Dao的实现类
 *
 * @author murphy
 * @since 2021/6/7 10:39 下午
 */
public class UserDaoMysql implements BaseUserDao {

    /**
     * 用于查询数据库中的所有用户信息 - 总人数 + 日注册量(新增)
     */
    private static final String SQL_U_CONSOLE = "SELECT " +
            "COUNT(UID) U1_TOTAL, " +
            "COUNT(TO_DAYS(UINTIME)=TO_DAYS(NOW()) OR NULL) U1_DAY " +
            "FROM USER";

    /**
     * 用于查询所有用户信息
     */
    private static final String SQL_FIND_ALL = "SELECT * FROM USER";

    /**
     * 用于分页查询所有用户信息
     */
    private static final String SQL_FIND_LIMIT = "SELECT * FROM USER LIMIT ?,?";

    /**
     * 通过用户手机号查询
     */
    private static final String SQL_FIND_BY_PHONE = "SELECT * FROM USER WHERE uPHONE = ?";

    /**
     * 通过用户姓名模糊查询
     */
    private static final String SQL_FIND_BY_NAME = "SELECT * FROM USER WHERE uName LIKE ?";

    /**
     * 录入用户信息
     */
    private static final String SQL_INSERT = "INSERT INTO USER " +
            "(UNAME, UPHONE, PASSWORD, IDNUMBER, UINTIME) " +
            "VALUES(?,?,?,?,NOW())";

    /**
     * 用户信息修改
     */
    private static final String SQL_UPDATE = "UPDATE USER SET " +
            "UNAME = ?, UPHONE = ?, PASSWORD = ?, IDNUMBER = ? WHERE UID = ?";

    /**
     * 用户信息删除
     */
    private static final String SQL_DELETE = "DELETE FROM USER WHERE UID = ?";

    /**
     * 根据用户姓名，更新登录时间
     */
    private static final String SQL_UPDATE_LAST_LOGIN = "UPDATE USER SET  lastLogin = NOW() WHERE uPhone = ?";

    /**
     * 用于查询数据库中的所有用户信息 - 总人数 + 日注册量(新增)
     *
     * @return [{total:总数, day:新增}]
     */
    @Override
    public List<Map<String, Integer>> uConsole() {
        ArrayList<Map<String, Integer>> list = new ArrayList<>();
        // 1. 获取数据库的连接
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        ResultSet result = null;
        // 2. 预编译SQL语句
        try {
            state = conn.prepareStatement(SQL_U_CONSOLE);
            // 3. 执行SQL语句
            result = state.executeQuery();
            // 4. 获取执行结果
            if (result.next()) {
                int u1_Total = result.getInt(1);
                int u1_Day = result.getInt(2);
                Map u1 = new HashMap();
                u1.put("u1_Total", u1_Total);
                u1.put("u1_Day", u1_Day);
                list.add(u1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DruidUtil.close(conn,state,result);
        }
        return list;
    }

    /**
     * 用于查询所有用户信息
     *
     * @param limit      是否分页的标志
     * @param offset     SQL语句的起始索引
     * @param pageNumber 页查询的数量
     * @return 快递员信息的集合
     */
    @Override
    public List<User> findAll(boolean limit, int offset, int pageNumber) {
        ArrayList<User> list = new ArrayList<>();
        // 1. 获取数据库连接
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        ResultSet result = null;
        // 2. 预编译SQL语句
        try {
            if (limit) {
                state = conn.prepareStatement(SQL_FIND_LIMIT);
                // 3. 参数填充
                state.setInt(1, offset);
                state.setInt(2, pageNumber);
            } else {
                state = conn.prepareStatement(SQL_FIND_ALL);
            }
            // 4. 执行SQL语句
            result = state.executeQuery();
            while (result.next()) {
                Integer uId = result.getInt(1);
                String uName = result.getString(2);
                String uPhone = result.getString(3);
                String password = result.getString(4);
                String idNumber = result.getString(5);
                Timestamp uinTime = result.getTimestamp(6);
                Timestamp lastLogin = result.getTimestamp(7);
                User u = new User(uId,uName,uPhone,password,idNumber,uinTime,lastLogin);
                list.add(u);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DruidUtil.close(conn,state,result);
        }
        return list;
    }

    /**
     * 根据手机号查询用户信息
     *
     * @param uPhone 用户手机号
     * @return 查询的快递员信息，不存在则返回null
     */
    @Override
    public User findByuPhone(String uPhone) {
        // 1. 获取数据库连接
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        ResultSet result = null;
        // 2. 预编译SQL语句
        try {
            state = conn.prepareStatement(SQL_FIND_BY_PHONE);
            // 3. 参数填充
            state.setString(1, uPhone);
            // 4. 执行语句
            result = state.executeQuery();
            if (result.next()) {
                Integer uId = result.getInt(1);
                String uName = result.getString(2);
                String password = result.getString(4);
                String idNumber = result.getString(5);
                Timestamp uinTime = result.getTimestamp(6);
                Timestamp lastLogin = result.getTimestamp(7);
                User u = new User(uId,uName,uPhone,password,idNumber,uinTime,lastLogin);
                return u;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DruidUtil.close(conn,state,result);
        }
        return null;
    }

    /**
     * 根据用户姓名，模糊查询用户信息
     *
     * @param uName 用户姓名
     * @return 用户信息的集合
     */
    @Override
    public List<User> findByuName(String uName) {
        ArrayList<User> list = new ArrayList<>();
        // 1. 获取数据库连接
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        ResultSet result = null;
        // 2. 预编译SQL语句
        try {
            state = conn.prepareStatement(SQL_FIND_BY_NAME);
            // 3. 参数填充
            state.setString(1,"%" + uName + "%");
            // 4. 执行SQL语句
            result = state.executeQuery();
            // 5. 获取执行的结果
            while (result.next()) {
                Integer uId = result.getInt(1);
                String name = result.getString(2);
                String uPhone = result.getString(3);
                String password = result.getString(4);
                String idNumber = result.getString(5);
                Timestamp uinTime = result.getTimestamp(6);
                Timestamp lastLogin = result.getTimestamp(7);
                User u = new User(uId,name,uPhone,password,idNumber,uinTime,lastLogin);
                list.add(u);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DruidUtil.close(conn,state,result);
        }
        return list;
    }

    /**
     * 用户信息的录入
     *
     * @param u 要录入的用户信息
     * @return 录入的结果 - true / false
     */
    @Override
    public boolean insert(User u) {
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        try {
            state = conn.prepareStatement(SQL_INSERT);
            state.setString(1,u.getuName());
            state.setString(2,u.getuPhone());
            state.setString(3,u.getPassword());
            state.setString(4,u.getIdNumber());
            return state.executeUpdate() > 0 ? true : false;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DruidUtil.close(conn,state,null);
        }
        return false;
    }

    /**
     * 用户信息的修改
     *
     * @param uId     要修改的用户ID
     * @param newUser 新的用户对象(uName, uPhone, idNumber, password)
     * @return 修改的结果 - true / false
     */
    @Override
    public boolean update(int uId, User newUser) {
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        try {
            state = conn.prepareStatement(SQL_UPDATE);
            state.setString(1, newUser.getuName());
            state.setString(2, newUser.getuPhone());
            state.setString(3, newUser.getPassword());
            state.setString(4, newUser.getIdNumber());
            state.setInt(5, uId);
            return state.executeUpdate() > 0 ? true : false;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DruidUtil.close(conn, state, null);
        }
        return false;
    }

    /**
     * 根据ID 删除单个用户信息
     *
     * @param uId 要删除的用户ID
     * @return 删除的结果 - true / false
     */
    @Override
    public boolean delete(int uId) {
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        try {
            state = conn.prepareStatement(SQL_DELETE);
            state.setInt(1, uId);
            return state.executeUpdate() > 0 ? true : false;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DruidUtil.close(conn,state,null);
        }
        return false;
    }

    /**
     * 根据用户名，更新登录时间和登录IP
     *
     * @param uPhone
     */
    @Override
    public void updateLoginTime(String uPhone) {
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        try {
            state = conn.prepareStatement(SQL_UPDATE_LAST_LOGIN);
            state.setString(1, uPhone);
            state.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DruidUtil.close(conn,state,null);
        }
    }
}
