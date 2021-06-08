package com.murphy.service;

import com.murphy.bean.User;
import com.murphy.dao.BaseUserDao;
import com.murphy.dao.impl.UserDaoMysql;

import java.util.List;
import java.util.Map;

/**
 * Service 层 - User 实现
 *
 * @author murphy
 * @since 2021/6/7 11:39 下午
 */
public class UserService {

    private static BaseUserDao dao = new UserDaoMysql();

    /**
     * 用于查询数据库中的所有用户信息 - 总人数 + 日注册量(新增)
     *
     * @return [{total:总数, day:新增}]
     */
    public static List<Map<String, Integer>> uConsole() {
        return dao.uConsole();
    }

    /**
     * 用于查询所有用户信息
     *
     * @param limit      是否分页的标志
     * @param offset     SQL语句的起始索引
     * @param pageNumber 页查询的数量
     * @return 快递员信息的集合
     */
    public static List<User> findAll(boolean limit, int offset, int pageNumber) {
        return dao.findAll(limit, offset, pageNumber);
    }

    /**
     * 根据手机号查询用户信息
     *
     * @param uPhone 用户手机号
     * @return 查询的快递员信息，不存在则返回null
     */
    public static User findByuPhone(String uPhone) {
        return dao.findByuPhone(uPhone);
    }

    /**
     * 根据用户姓名，模糊查询用户信息
     *
     * @param uName 用户姓名
     * @return 用户信息的集合
     */
    public static List<User> findByuName(String uName) {
        return dao.findByuName(uName);
    }

    /**
     * 用户信息的录入
     *
     * @param u 要录入的用户信息
     * @return 录入的结果 - true / false
     */
    public static boolean insert(User u) {
        return dao.insert(u);
    }

    /**
     * 用户信息的修改
     *
     * @param uId     要修改的用户ID
     * @param newUser 新的用户对象(uName, uPhone, idNumber, password)
     * @return 修改的结果 - true / false
     */
    public static boolean update(int uId, User newUser) {
        return dao.update(uId, newUser);
    }

    /**
     * 根据ID 删除单个用户信息
     *
     * @param uId 要删除的用户ID
     * @return 删除的结果 - true / false
     */
    public static boolean delete(int uId) {
        return dao.delete(uId);
    }
}
