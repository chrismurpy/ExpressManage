package com.murphy.dao.impl;

import com.murphy.bean.Courier;
import com.murphy.dao.BaseCourierDao;
import com.murphy.exception.DuplicateCodeException;
import com.murphy.util.DruidUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Courier - Dao的实现类
 *
 * @author murphy
 * @since 2021/6/5 6:07 下午
 */
public class CourierDaoMysql implements BaseCourierDao {

    /**
     * 用于查询数据库中的所有快递员信息 - 总人数 + 日注册量(新增)
     */
    private static final String SQL_C_CONSOLE = "SELECT " +
            "COUNT(CID) C1_TOTAL, " +
            "COUNT(TO_DAYS(CINTIME)=TO_DAYS(NOW()) OR NULL) C1_DAY" +
            " FROM COURIER";
    /**
     * 用于查询所有快递员信息
     */
    private static final String SQL_FIND_ALL = "SELECT * FROM COURIER";

    /**
     * 用于分页查询所有快递员信息
     */
    private static final String SQL_FIND_LIMIT = "SELECT * FROM COURIER LIMIT ?,?";

    /**
     * 通过快递员手机号查询
     */
    private static final String SQL_FIND_BY_PHONE = "SELECT * FROM COURIER WHERE cPHONE = ?";

    /**
     * 通过快递员姓名查询
     */
    private static final String SQL_FIND_BY_NAME = "SELECT * FROM COURIER WHERE CNAME LIKE ?";

    /**
     * 录入快递员信息
     */
    private static final String SQL_INSERT = "INSERT INTO COURIER " +
            "(CNAME,CPHONE,IDNUMBER,PASSWORD,CNUMBER,CINTIME) " +
            "VALUES(?,?,?,?,?,NOW())";

    /**
     * 快递员信息修改
     */
    private static final String SQL_UPDATE = "UPDATE COURIER SET " +
            "CNAME = ?, CPHONE = ?, IDNUMBER = ?, PASSWORD = ? WHERE CID = ?";

    /**
     * 快递员信息删除
     */
    private static final String SQL_DELETE = "DELETE FROM COURIER WHERE CID = ?";

    /**
     * 用于查询数据库中的所有快递员信息 - 总人数 + 日注册量(新增)
     *
     * @return [{total:总数, day:新增}]
     */
    @Override
    public List<Map<String, Integer>> cConsole() {
        ArrayList<Map<String, Integer>> list = new ArrayList<>();
        // 1. 获取数据库的连接
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        ResultSet result = null;
        // 2. 预编译SQL语句
        try {
            state = conn.prepareStatement(SQL_C_CONSOLE);
            // 3. 执行SQL语句
            result = state.executeQuery();
            // 4. 获取执行结果
            if (result.next()) {
                int c1_Total = result.getInt(1);
                int c1_Day = result.getInt(2);
                Map c1 = new HashMap();
                c1.put("c1_Total", c1_Total);
                c1.put("c1_Day", c1_Day);
                list.add(c1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DruidUtil.close(conn,state,result);
        }
        return list;
    }

    /**
     * 用于查询所有快递员信息
     *
     * @param limit      是否分页的标记
     * @param offset     SQL语句的起始索引
     * @param pageNumber 页查询的数量
     * @return 快递员信息的集合
     */
    @Override
    public List<Courier> findAll(boolean limit, int offset, int pageNumber) {
        ArrayList<Courier> list = new ArrayList<>();
        // 1. 获取数据库连接
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        ResultSet result = null;
        // 2. 预编译SQL语句
        try {
            if (limit) {
                state = conn.prepareStatement(SQL_FIND_LIMIT);
                // 3. 参数填充
                state.setInt(1,offset);
                state.setInt(2,pageNumber);
            } else {
                state = conn.prepareStatement(SQL_FIND_ALL);
            }
            // 4. 执行SQL语句
            result = state.executeQuery();
            while (result.next()){
                Integer cid = result.getInt(1);
                String cName = result.getString(2);
                String cPhone = result.getString(3);
                String idNumber = result.getString(4);
                String password = result.getString(5);
                Integer cNumber = result.getInt(6);
                Timestamp cInTime = result.getTimestamp(7);
                Timestamp lastLogin = result.getTimestamp(8);
                Courier c = new Courier(cid,cName,cPhone,idNumber,password,cNumber,cInTime,lastLogin);
                list.add(c);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DruidUtil.close(conn,state,result);
        }
        return list;
    }

    /**
     * 根据手机号查询快递员信息
     *
     * @param cPhone 手机号
     * @return 查询的快递员信息，不存在时，返回null
     */
    @Override
    public Courier findBycPhone(String cPhone) {
        // 1. 获取数据库连接
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        ResultSet result = null;
        // 2. 预编译SQL语句
        try {
            state = conn.prepareStatement(SQL_FIND_BY_PHONE);
            // 3. 参数填充
            state.setString(1,cPhone);
            // 4. 执行SQL语句
            result = state.executeQuery();
            if (result.next()) {
                Integer cid = result.getInt(1);
                String cName = result.getString(2);
                String idNumber = result.getString(4);
                String password = result.getString(5);
                Integer cNumber = result.getInt(6);
                Timestamp cInTime = result.getTimestamp(7);
                Timestamp lastLogin = result.getTimestamp(8);
                Courier c = new Courier(cid,cName,cPhone,idNumber,password,cNumber,cInTime,lastLogin);
                return c;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DruidUtil.close(conn,state,result);
        }
        return null;
    }

    /**
     * 根据快递员姓名，查询快递员信息
     *
     * @param cName 快递员姓名
     * @return 查询的快递员信息
     */
    @Override
    public List<Courier> findBycName(String cName) {
        ArrayList<Courier> list = new ArrayList<>();
        // 1. 获取数据库的连接
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        ResultSet result = null;
        // 2. 预编译SQL语句
        try {
            state = conn.prepareStatement(SQL_FIND_BY_NAME);
            // 3. 参数填充
            state.setString(1,"%" + cName + "%");
            // 4. 执行SQL语句
            result = state.executeQuery();
            // 5. 获取执行的结果
            while (result.next()) {
                Integer cid = result.getInt(1);
                String name = result.getString(2);
                String cPhone = result.getString(3);
                String idNumber = result.getString(4);
                String password = result.getString(5);
                Integer cNumber = result.getInt(6);
                Timestamp cInTime = result.getTimestamp(7);
                Timestamp lastLogin = result.getTimestamp(8);
                Courier c = new Courier(cid,name,cPhone,idNumber,password,cNumber,cInTime,lastLogin);
                list.add(c);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DruidUtil.close(conn,state,result);
        }
        return list;
    }

    /**
     * 快递员信息的录入
     *
     * @param c 要录入的快递员信息
     * @return 录入的结果 - true / false
     */
    @Override
    public boolean insert(Courier c) throws DuplicateCodeException {
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        try {
            state = conn.prepareStatement(SQL_INSERT);
            state.setString(1,c.getcName());
            state.setString(2,c.getcPhone());
            state.setString(3,c.getIdNumber());
            state.setString(4, c.getPassword());
            state.setInt(5,c.getcNumber());
            return state.executeUpdate() > 0 ? true : false;
        } catch (SQLException throwables) {
            if (throwables.getMessage().endsWith("for key 'Courier_cphone_uindex'")) {
                // 手机号重复，出现异常
                DuplicateCodeException e1 = new DuplicateCodeException(throwables.getMessage());
                throw e1;
            } else {
                throwables.printStackTrace();
            }
        } finally {
            DruidUtil.close(conn,state,null);
        }
        return false;
    }

    /**
     * 快递员信息的修改
     *
     * @param cId        要修改的快递员ID
     * @param newCourier 新的快递员对象 (cName, cPhone, idNumber, password)
     * @return
     */
    @Override
    public boolean update(int cId, Courier newCourier) {
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        try {
            state = conn.prepareStatement(SQL_UPDATE);
            state.setString(1, newCourier.getcName());
            state.setString(2, newCourier.getcPhone());
            state.setString(3, newCourier.getIdNumber());
            state.setString(4, newCourier.getPassword());
            state.setInt(5, cId);
            return state.executeUpdate() > 0 ? true : false;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DruidUtil.close(conn,state,null);
        }
        return false;
    }

    /**
     * 根据ID，删除单个快递信息
     *
     * @param cId 要删除的快递员ID
     * @return 删除的结果 - true / false
     */
    @Override
    public boolean delete(int cId) {
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        try {
            state = conn.prepareStatement(SQL_DELETE);
            state.setInt(1, cId);
            return state.executeUpdate() > 0 ? true : false;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DruidUtil.close(conn,state,null);
        }
        return false;
    }
}
