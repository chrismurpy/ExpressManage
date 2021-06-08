package com.murphy.bean;

import java.sql.Timestamp;

/**
 * User - 表格显示处理
 *
 * @author murphy
 * @since 2021/6/7 10:01 下午
 */
public class BootStrapTableUser {
    private Integer uId;
    private String uName;
    private String uPhone;
    private String password;
    private String idNumber;
    private String uinTime;
    private String lastLogin;

    public BootStrapTableUser(Integer uId, String uName, String uPhone, String password, String idNumber, String uinTime, String lastLogin) {
        this.uId = uId;
        this.uName = uName;
        this.uPhone = uPhone;
        this.password = password;
        this.idNumber = idNumber;
        this.uinTime = uinTime;
        this.lastLogin = lastLogin;
    }

    public BootStrapTableUser() {
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public Integer getuId() {
        return uId;
    }

    public void setuId(Integer uId) {
        this.uId = uId;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getuPhone() {
        return uPhone;
    }

    public void setuPhone(String uPhone) {
        this.uPhone = uPhone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUinTime() {
        return uinTime;
    }

    public void setUinTime(String uinTime) {
        this.uinTime = uinTime;
    }

    public String getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }
}
