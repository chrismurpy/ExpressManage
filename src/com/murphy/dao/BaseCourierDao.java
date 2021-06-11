package com.murphy.dao;

import com.murphy.bean.Courier;
import com.murphy.exception.DuplicateCodeException;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 用于定义Courier表格的操作规范
 * @author murphy
 * @since 2021/6/5 5:48 下午
 */
public interface BaseCourierDao {
    /**
     * 用于查询数据库中的所有快递员信息 - 总人数 + 日注册量(新增)
     * @return [{total:总数, day:新增}]
     */
    List<Map<String, Integer>> cConsole();

    /**
     * 用于查询所有快递员信息
     * @param limit 是否分页的标记
     * @param offset SQL语句的起始索引
     * @param pageNumber 页查询的数量
     * @return 快递员信息的集合
     */
    List<Courier> findAll(boolean limit, int offset, int pageNumber);

    /**
     * 根据手机号查询快递员信息
     * @param cPhone 手机号
     * @return 查询的快递员信息，不存在时，返回null
     */
    Courier findBycPhone(String cPhone);

    /**
     * 根据快递员姓名，模糊查询快递员信息
     * @param cName 快递员姓名
     * @return 查询的快递员信息
     */
    List<Courier> findBycName(String cName);

    /**
     * 快递员信息的录入
     * @param c 要录入的快递员信息
     * @return 录入的结果 - true / false
     * @throws DuplicateCodeException
     */
    boolean insert(Courier c);

    /**
     * 快递员信息的修改
     * @param cId 要修改的快递员ID
     * @param newCourier 新的快递员对象 (cName, cPhone, idNumber, password)
     * @return 修改的结果 - true / false
     */
    boolean update(int cId, Courier newCourier);

    /**
     * 根据ID，删除单个快递信息
     * @param cId 要删除的快递员ID
     * @return 删除的结果 - true / false
     */
    boolean delete(int cId);

    /**
     * 根据用户名，更新登录时间和登录IP
     * @param cPhone
     */
    void updateLoginTime(String cPhone);
}
