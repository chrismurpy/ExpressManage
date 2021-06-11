package com.murphy.dao;

import com.murphy.bean.User;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 用于定义用户的操作规范
 *
 * @author murphy
 * @since 2021/6/7 10:04 下午
 */
public interface BaseUserDao {
    /**
     * 用于查询数据库中的所有用户信息 - 总人数 + 日注册量(新增)
     * @return [{total:总数, day:新增}]
     */
    List<Map<String, Integer>> uConsole();

    /**
     * 用于查询所有用户信息
     * @param limit 是否分页的标志
     * @param offset SQL语句的起始索引
     * @param pageNumber 页查询的数量
     * @return 快递员信息的集合
     */
    List<User> findAll(boolean limit, int offset, int pageNumber);

    /**
     * 根据手机号查询用户信息
     * @param uPhone 用户手机号
     * @return 查询的快递员信息，不存在则返回null
     */
    User findByuPhone(String uPhone);

    /**
     * 根据用户姓名，模糊查询用户信息
     * @param uName 用户姓名
     * @return 用户信息的集合
     */
    List<User> findByuName(String uName);

    /**
     * 用户信息的录入
     * @param u 要录入的用户信息
     * @return 录入的结果 - true / false
     */
    boolean insert(User u);

    /**
     * 用户信息的修改
     * @param uId 要修改的用户ID
     * @param newUser 新的用户对象(uName, uPhone, idNumber, password)
     * @return 修改的结果 - true / false
     */
    boolean update(int uId, User newUser);

    /**
     * 根据ID 删除单个用户信息
     * @param uId 要删除的用户ID
     * @return 删除的结果 - true / false
     */
    boolean delete(int uId);

    /**
     * 根据手机号，更新登录时间
     * @param uPhone
     */
    void updateLoginTime(String uPhone);
}
