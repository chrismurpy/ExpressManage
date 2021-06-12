package com.murphy.dao.impl;

import com.murphy.bean.Express;
import com.murphy.dao.BaseAdminDao;
import com.murphy.dao.BaseExpressDao;
import com.murphy.exception.DuplicateCodeException;
import com.murphy.util.DruidUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Express - Dao的实现类
 * @author murphy
 * @since 2021/6/4 11:52 上午
 */
public class ExpressDaoMysql implements BaseExpressDao {
    /**
     * 用于查询数据库中的全部快递(总数+新增)，待取件快递(总数+新增)
     */
    private static final String SQL_CONSOLE = "SELECT " +
            "COUNT(ID) DATA1_SIZE," +
            "COUNT(TO_DAYS(INTIME)=TO_DAYS(NOW()) OR NULL) DATA1_DAY," +
            "COUNT(STATUS=0 OR NULL) DATA2_SIZE," +
            "COUNT(TO_DAYS(INTIME)=TO_DAYS(NOW()) AND STATUS=0 OR NULL) DATA2_DAY" +
            " FROM EXPRESS";

    /**
     * 用于查询源于某发货地的快递总数
     */
    private static final String SQL_DEPARTURE = "SELECT " +
            "departure Area1, COUNT(1) Size1 " +
            "FROM Express GROUP BY Area1 ORDER BY Size1 DESC";

    /**
     * 用于查询源于某收货地的快递总数
     */
    private static final String SQL_DESTINATION = "SELECT " +
            "destination Area2, COUNT(1) Size2 " +
            "FROM Express GROUP BY Area2 ORDER BY Size2 DESC";
    /**
     * 用于查询所有快递信息
     */
    private static final String SQL_FIND_ALL = "SELECT * FROM Express";
    /**
     * 用于分页查询所有快递信息
     */
    private static final String SQL_FIND_LIMIT = "SELECT * FROM EXPRESS LIMIT ?,?";
    /**
     * 通过取件码查询快递信息
     */
    private static final String SQL_FIND_BY_CODE = "SELECT * FROM EXPRESS WHERE CODE = ?";
    /**
     * 通过快递单号查询快递信息
     */
    private static final String SQL_FIND_BY_NUMBER = "SELECT * FROM EXPRESS WHERE NUMBER = ?";
    /**
     * 通过录入人手机号查询快递信息
     */
    private static final String SQL_FIND_BY_SYSPHONE = "SELECT * FROM EXPRESS WHERE SYSPHONE = ?";
    /**
     * 通过收件人手机号查询快递信息
     */
    private static final String SQL_FIND_BY_USERPHONE = "SELECT * FROM EXPRESS WHERE USERPHONE = ?";
    /**
     * 通过手机号查询未取件快递
     */
    private static final String SQL_FIND_BY_USERPHONE_STATUS = "SELECT * FROM EXPRESS WHERE USERPHONE = ? AND STATUS = ?";
    /**
     * 录入快递
     */
    private static final String SQL_INSERT = "INSERT INTO EXPRESS " +
            "(NUMBER,USERNAME,USERPHONE,COMPANY,CODE,INTIME,STATUS,SYSPHONE) " +
            "VALUES(?,?,?,?,?,NOW(),0,?)";
    /**
     * 快递修改
     */
    private static final String SQL_UPDATE = "UPDATE EXPRESS SET " +
            "NUMBER = ?,USERNAME = ?,COMPANY = ?, STATUS = ? " +
            "WHERE ID = ?";
    /**
     * 快递的状态码改变(取件)
     */
    private static final String SQL_UPDATE_STATUS = "UPDATE EXPRESS SET STATUS=1,OUTTIME=NOW(),CODE=NULL WHERE CODE = ?";
    /**
     * 快递的删除
     */
    private static final String SQL_DELETE = "DELETE FROM EXPRESS WHERE ID = ?";

    /**
     * 查询一年内某用户的总快递数
     */
    private static final String SQL_FIND_BY_PHONE_AMONG_YEAR = "SELECT * FROM EXPRESS WHERE USERPHONE = ? " +
            "AND YEAR(NOW()) - YEAR(INTIME) < 1";

    /**
     * 查询一月内某用户的总快递数
     */
    private static final String SQL_FIND_BY_PHONE_AMONG_MONTH = "SELECT * FROM EXPRESS WHERE USERPHONE = ? " +
            "AND MONTH(NOW()) - MONTH(INTIME) < 1";

    /**
     * 用于查询数据库中的全部快递(总数+新增)，待取件快递(总数+新增)
     *
     * @return [{size:总数, day:新增}, {size:总数, day:新增}]
     */
    @Override
    public List<Map<String, Integer>> console() {
        ArrayList<Map<String, Integer>> list = new ArrayList<>();
        // 1. 获取数据库的连接
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        ResultSet result = null;
        // 2. 预编译SQL语句
        try {
            state = conn.prepareStatement(SQL_CONSOLE);
            // 3. 填充参数(填充)
            // 4. 执行SQL语句
            result = state.executeQuery();
            // 5. 获取执行的结果
            if (result.next()) {
                int data1_Size = result.getInt(1);
                int data1_Day = result.getInt(2);
                int data2_Size = result.getInt(3);
                int data2_Day = result.getInt(4);
                Map data1 = new HashMap();
                data1.put("data1_Size", data1_Size);
                data1.put("data1_Day", data1_Day);
                Map data2 = new HashMap();
                data2.put("data2_Size", data2_Size);
                data2.put("data2_Day", data2_Day);
                list.add(data1);
                list.add(data2);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            // 6. 资源的释放
            DruidUtil.close(conn,state,result);
        }

        return list;
    }

    @Override
    public List<Map<String, Integer>> areaAll() {
        ArrayList<Map<String, Integer>> list = new ArrayList<>();
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        ResultSet result = null;
        try {
            state = conn.prepareStatement(SQL_DEPARTURE);
            result = state.executeQuery();
            while (result.next()) {
                Map<String, Integer> map = new HashMap();
                String name = result.getString(1);
                Integer value = result.getInt(2);
                map.put(name, value);
                list.add(map);
            }

            state = conn.prepareStatement(SQL_DESTINATION);
            result = state.executeQuery();
            while (result.next()) {
                Map<String, Integer> map = new HashMap();
                String name = result.getString(1);
                Integer value = result.getInt(2);
                map.put(name, value);
                list.add(map);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DruidUtil.close(conn,state,result);
        }
        return list;
    }

    /**
     * 用于查询所有快递
     *
     * @param limit      是否分页的标记 - true表示分页 / false表示查询所有快递
     * @param offset     SQL语句的起始索引
     * @param pageNumber 页查询的数量
     * @return 快递的集合
     */
    @Override
    public List<Express> findAll(boolean limit, int offset, int pageNumber) {
        ArrayList<Express> list = new ArrayList<>();
        // 1. 获取数据库的连接
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        ResultSet result = null;
        // 2. 预编译SQL语句
        try {
            if (limit) {
                state = conn.prepareStatement(SQL_FIND_LIMIT);
                // 3. 填充参数(填充)
                state.setInt(1,offset);
                state.setInt(2,pageNumber);
            } else {
                state = conn.prepareStatement(SQL_FIND_ALL);
            }

            // 4. 执行SQL语句
            result = state.executeQuery();
            // 5. 获取执行的结果
            while (result.next()) {
                Integer id = result.getInt(1);
                String number = result.getString("number");
                String username = result.getString("username");
                String userPhone = result.getString("userphone");
                String company = result.getString("company");
                String code = result.getString("code");
                Timestamp inTime = result.getTimestamp("intime");
                Timestamp outTime = result.getTimestamp("outtime");
                Integer status = result.getInt("status");
                String sysPhone = result.getString("sysphone");
                Express e = new Express(id,number,username,userPhone,company,code,inTime,outTime,status,sysPhone);
                list.add(e);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            // 6. 资源的释放
            DruidUtil.close(conn,state,result);
        }

        return list;
    }

    /**
     * 根据快递单号，查询快递信息
     *
     * @param number 单号
     * @return 查询的快递信息，单号不存在时，返回null
     */
    @Override
    public Express findByNumber(String number) {
        // 1. 获取数据库的连接
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        ResultSet result = null;
        // 2. 预编译SQL语句
        try {
            state = conn.prepareStatement(SQL_FIND_BY_NUMBER);
            // 3. 填充参数(填充)
            state.setString(1,number);
            // 4. 执行SQL语句
            result = state.executeQuery();
            // 5. 获取执行的结果
            if (result.next()) {
                Integer id = result.getInt("id");
                String username = result.getString("username");
                String userPhone = result.getString("userphone");
                String company = result.getString("company");
                String code = result.getString("code");
                Timestamp inTime = result.getTimestamp("intime");
                Timestamp outTime = result.getTimestamp("outtime");
                Integer status = result.getInt("status");
                String sysPhone = result.getString("sysphone");
                Express e = new Express(id,number,username,userPhone,company,code,inTime,outTime,status,sysPhone);
                return e;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            // 6. 资源的释放
            DruidUtil.close(conn,state,result);
        }
        return null;
    }

    /**
     * 根据取件码，查询快递信息
     *
     * @param code 取件码
     * @return 查询的快递信息，取件码不存在时，返回null
     */
    @Override
    public Express findByCode(String code) {
        // 1. 获取数据库的连接
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        ResultSet result = null;
        // 2. 预编译SQL语句
        try {
            state = conn.prepareStatement(SQL_FIND_BY_CODE);
            // 3. 填充参数(填充)
            state.setString(1,code);
            // 4. 执行SQL语句
            result = state.executeQuery();
            // 5. 获取执行的结果
            if (result.next()) {
                Integer id = result.getInt("id");
                String number = result.getString("number");
                String username = result.getString("username");
                String userPhone = result.getString("userphone");
                String company = result.getString("company");
                Timestamp inTime = result.getTimestamp("intime");
                Timestamp outTime = result.getTimestamp("outtime");
                Integer status = result.getInt("status");
                String sysPhone = result.getString("sysphone");
                Express e = new Express(id,number,username,userPhone,company,code,inTime,outTime,status,sysPhone);
                return e;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            // 6. 资源的释放
            DruidUtil.close(conn,state,result);
        }

        return null;
    }

    /**
     * 根据用户手机号码，查询其所有的快递信息
     *
     * @param userPhone 手机号码
     * @return 查询的快递信息列表
     */
    @Override
    public List<Express> findByUserPhone(String userPhone) {
        ArrayList<Express> list = new ArrayList<>();
        // 1. 获取数据库的连接
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        ResultSet result = null;
        // 2. 预编译SQL语句
        try {
            state = conn.prepareStatement(SQL_FIND_BY_USERPHONE);
            // 3. 填充参数(填充)
            state.setString(1,userPhone);
            // 4. 执行SQL语句
            result = state.executeQuery();
            // 5. 获取执行的结果
            while (result.next()) {
                Integer id = result.getInt("id");
                String number = result.getString("number");
                String username = result.getString("username");
                String company = result.getString("company");
                String code = result.getString("code");
                Timestamp inTime = result.getTimestamp("intime");
                Timestamp outTime = result.getTimestamp("outtime");
                Integer status = result.getInt("status");
                String sysPhone = result.getString("sysphone");
                Express e = new Express(id,number,username,userPhone,company,code,inTime,outTime,status,sysPhone);
                list.add(e);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            // 6. 资源的释放
            DruidUtil.close(conn,state,result);
        }

        return list;
    }

    /**
     * 根据用户手机号码/快递状态，查询其所有的未取件快递信息
     *
     * @param userPhone 手机号码
     * @return 查询的快递信息列表
     */
    @Override
    public List<Express> findByUserPhoneAndStatus(String userPhone, int status) {
        ArrayList<Express> list = new ArrayList<>();
        // 1. 获取数据库的连接
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        ResultSet result = null;
        // 2. 预编译SQL语句
        try {
            state = conn.prepareStatement(SQL_FIND_BY_USERPHONE_STATUS);
            // 3. 填充参数(填充)
            state.setString(1,userPhone);
            state.setInt(2,status);
            // 4. 执行SQL语句
            result = state.executeQuery();
            // 5. 获取执行的结果
            while (result.next()) {
                Integer id = result.getInt("id");
                String number = result.getString("number");
                String username = result.getString("username");
                String company = result.getString("company");
                String code = result.getString("code");
                Timestamp inTime = result.getTimestamp("intime");
                Timestamp outTime = result.getTimestamp("outtime");
                String sysPhone = result.getString("sysphone");
                Express e = new Express(id,number,username,userPhone,company,code,inTime,outTime,status,sysPhone);
                list.add(e);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            // 6. 资源的释放
            DruidUtil.close(conn,state,result);
        }

        return list;
    }

    /**
     * 根据录入人手机号码，查询录入的所有记录
     *
     * @param sysPhone 录入人手机号码
     * @return 查询的快递信息列表
     */
    @Override
    public List<Express> findBySysPhone(String sysPhone) {
        ArrayList<Express> list = new ArrayList<>();
        // 1. 获取数据库的连接
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        ResultSet result = null;
        // 2. 预编译SQL语句
        try {
            state = conn.prepareStatement(SQL_FIND_BY_SYSPHONE);
            // 3. 填充参数(填充)
            state.setString(1,sysPhone);
            // 4. 执行SQL语句
            result = state.executeQuery();
            // 5. 获取执行的结果
            while (result.next()) {
                Integer id = result.getInt("id");
                String number = result.getString("number");
                String username = result.getString("username");
                String userPhone = result.getString("userphone");
                String company = result.getString("company");
                String code = result.getString("code");
                Timestamp inTime = result.getTimestamp("intime");
                Timestamp outTime = result.getTimestamp("outtime");
                Integer status = result.getInt("status");
                Express e = new Express(id,number,username,userPhone,company,code,inTime,outTime,status,sysPhone);
                list.add(e);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            // 6. 资源的释放
            DruidUtil.close(conn,state,result);
        }

        return list;
    }

    /**
     * 快递的录入
     * INSERT INTO EXPRESS
     * (NUMBER,USERNAME,USERPHONE,COMPANY,CODE,INTIME,STATUS,SYSPHONE) VALUES(?,?,?,?,?,NOW(),0,?)
     * @param e 要录入的快递信息
     * @return 录入的结果 - true表示成功 / false表示失败
     */
    @Override
    public boolean insert(Express e) throws DuplicateCodeException {
        // 1. 连接的获取
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        // 2. 预编译SQL语句
        try {
            // 3. 填充参数
            state = conn.prepareStatement(SQL_INSERT);
            state.setString(1,e.getNumber());
            state.setString(2,e.getUsername());
            state.setString(3,e.getUserPhone());
            state.setString(4,e.getCompany());
            state.setString(5,e.getCode());
            state.setString(6,e.getSysPhone());
            // 4. 执行SQL语句，并获取执行结果
            return state.executeUpdate() > 0 ? true : false;
        } catch (SQLException e1) {
//            throwables.printStackTrace();
            if (e1.getMessage().endsWith("for key 'Express_code_uindex'")){
                // 是因为取件码重复，出现异常
                DuplicateCodeException e2 = new DuplicateCodeException(e1.getMessage());
                throw e2;
            } else {
                e1.printStackTrace();
            }
        } finally {
            // 5. 释放资源
            DruidUtil.close(conn,state,null);
        }
        return false;
    }

    /**
     * 快递的修改
     * UPDATE EXPRESS SET
     * NUMBER = ?,USERNAME = ?,COMPANY = ?
     * WHERE ID = ?
     * @param id         要修改的快递id
     * @param newExpress 新的快递对象 (number,username,company,status)
     * @return 修改的结果 - true表示成功 / false表示失败
     */
    @Override
    public boolean update(int id, Express newExpress) {
        // 1. 连接的获取
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        // 2. 预编译SQL语句
        try {
            state = conn.prepareStatement(SQL_UPDATE);
            state.setString(1, newExpress.getNumber());
            state.setString(2, newExpress.getUsername());
            state.setString(3, newExpress.getCompany());
            state.setInt(4, newExpress.getStatus());
            state.setInt(5, id);
            return state.executeUpdate() > 0 ? true : false;
        } catch (SQLException e1) {
            e1.printStackTrace();
        } finally {
            DruidUtil.close(conn,state,null);
        }
        return false;
    }

    /**
     * 更改快递的状态为1，表示取件完成
     *
     * @param code 要修改的快递单号
     * @return 修改的结果 - true表示成功 / false表示失败
     */
    @Override
    public boolean updateStatus(String code) {
        // 1. 连接的获取
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        // 2. 预编译SQL语句
        try {
            state = conn.prepareStatement(SQL_UPDATE_STATUS);
            state.setString(1, code);
            return state.executeUpdate() > 0 ? true : false;
        } catch (SQLException e1) {
            e1.printStackTrace();
        } finally {
            DruidUtil.close(conn,state,null);
        }
        return false;
    }

    /**
     * 根据ID，删除单个快递信息
     *
     * @param id 要删除的快递ID
     * @return 删除的结果 - true表示成功 / false表示失败
     */
    @Override
    public boolean delete(int id) {
        // 1. 连接的获取
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        // 2. 预编译SQL语句
        try {
            state = conn.prepareStatement(SQL_DELETE);
            state.setInt(1, id);
            return state.executeUpdate() > 0 ? true : false;
        } catch (SQLException e1) {
            e1.printStackTrace();
        } finally {
            DruidUtil.close(conn,state,null);
        }
        return false;
    }

    /**
     * 根据手机号查询一年内的所有快递数
     *
     * @param uPhone
     * @return
     */
    @Override
    public List<Express> findAllAmongYearByPhone(String uPhone) {
        ArrayList<Express> list = new ArrayList<>();
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        ResultSet result = null;
        try {
            state = conn.prepareStatement(SQL_FIND_BY_PHONE_AMONG_YEAR);
            state.setString(1, uPhone);
            result = state.executeQuery();
            while (result.next()) {
                Integer id = result.getInt(1);
                String number = result.getString(2);
                String userName = result.getString(3);
                String userPhone = result.getString(4);
                String company = result.getString(5);
                String code = result.getString(6);
                Timestamp inTime = result.getTimestamp(7);
                Timestamp outTime = result.getTimestamp(8);
                Integer status = result.getInt(9);
                String sysPhone = result.getString(10);
                String departure = result.getString(11);
                String destination = result.getString(12);
                Express e = new Express(id,number,userName,userPhone,company,code,inTime,outTime,status,sysPhone,departure,destination);
                list.add(e);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DruidUtil.close(conn,state,result);
        }
        return list;
    }

    /**
     * 根据手机号查询一月内的所有快递数
     *
     * @param uPhone
     * @return
     */
    @Override
    public List<Express> findAllAmongMonthByPhone(String uPhone) {
        ArrayList<Express> list = new ArrayList<>();
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        ResultSet result = null;
        try {
            state = conn.prepareStatement(SQL_FIND_BY_PHONE_AMONG_MONTH);
            state.setString(1, uPhone);
            result = state.executeQuery();
            while (result.next()) {
                Integer id = result.getInt(1);
                String number = result.getString(2);
                String userName = result.getString(3);
                String userPhone = result.getString(4);
                String company = result.getString(5);
                String code = result.getString(6);
                Timestamp inTime = result.getTimestamp(7);
                Timestamp outTime = result.getTimestamp(8);
                Integer status = result.getInt(9);
                String sysPhone = result.getString(10);
                String departure = result.getString(11);
                String destination = result.getString(12);
                Express e = new Express(id,number,userName,userPhone,company,code,inTime,outTime,status,sysPhone,departure,destination);
                list.add(e);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DruidUtil.close(conn,state,result);
        }
        return list;
    }
}
