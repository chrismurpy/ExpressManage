package com.murphy.service;

import com.murphy.bean.Courier;
import com.murphy.dao.BaseCourierDao;
import com.murphy.dao.impl.CourierDaoMysql;
import com.murphy.exception.DuplicateCodeException;

import java.util.List;
import java.util.Map;

/**
 * Service 层 - Courier 实现
 * @author murphy
 * @since 2021/6/5 9:51 下午
 */
public class CourierService{

    private static BaseCourierDao dao = new CourierDaoMysql();

    /**
     * 用于查询数据库中的所有快递员信息 - 总人数 + 日注册量(新增)
     *
     * @return [{total:总数, day:新增}]
     */
    public static List<Map<String, Integer>> cConsole() {
        return dao.cConsole();
    }

    /**
     * 用于查询所有快递员信息
     *
     * @param limit      是否分页的标记
     * @param offset     SQL语句的起始索引
     * @param pageNumber 页查询的数量
     * @return 快递员信息的集合
     */
    public static List<Courier> findAll(boolean limit, int offset, int pageNumber) {
        return dao.findAll(limit, offset, pageNumber);
    }

    /**
     * 根据手机号查询快递员信息
     *
     * @param cPhone 手机号
     * @return 查询的快递员信息，不存在时，返回null
     */
    public static Courier findBycPhone(String cPhone) {
        return dao.findBycPhone(cPhone);
    }

    /**
     * 根据快递员姓名，查询快递员信息
     *
     * @param cName 快递员姓名
     * @return 查询的快递员信息
     */
    public static List<Courier> findBycName(String cName) {
        return dao.findBycName(cName);
    }

    /**
     * 快递员信息的录入
     *
     * @param c 要录入的快递员信息
     * @return 录入的结果 - true / false
     * @throws DuplicateCodeException
     */
    public static boolean insert(Courier c){
        return dao.insert(c);
    }

    /**
     * 快递员信息的修改
     *
     * @param cId        要修改的快递员ID
     * @param newCourier 新的快递员对象 (cName, cPhone, idNumber, password)
     * @return
     */
    public static boolean update(int cId, Courier newCourier) {
        return dao.update(cId, newCourier);
    }

    /**
     * 根据ID，删除单个快递信息
     *
     * @param cId 要删除的快递员ID
     * @return 删除的结果 - true / false
     */
    public static boolean delete(int cId) {
        return dao.delete(cId);
    }
}
