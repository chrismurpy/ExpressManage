package com.murphy.bean;

import java.sql.Timestamp;

/**
 * Courier 表格显示处理
 *
 * @author murphy
 * @since 2021/6/5 10:56 下午
 */
public class BootStrapTableCourier {
    private Integer cId;
    private String cName;
    private String cPhone;
    private String idNumber;
    private String password;
    private Integer cNumber;
    private String cinTime;
    private String lastLogin;

    public BootStrapTableCourier(Integer cId, String cName, String cPhone, String idNumber, String password, Integer cNumber, String cinTime, String lastLogin) {
        this.cId = cId;
        this.cName = cName;
        this.cPhone = cPhone;
        this.idNumber = idNumber;
        this.password = password;
        this.cNumber = cNumber;
        this.cinTime = cinTime;
        this.lastLogin = lastLogin;
    }

    public BootStrapTableCourier() {
    }

    public Integer getcId() {
        return cId;
    }

    public void setcId(Integer cId) {
        this.cId = cId;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public String getcPhone() {
        return cPhone;
    }

    public void setcPhone(String cPhone) {
        this.cPhone = cPhone;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getcNumber() {
        return cNumber;
    }

    public void setcNumber(Integer cNumber) {
        this.cNumber = cNumber;
    }

    public String getCinTime() {
        return cinTime;
    }

    public void setCinTime(String cinTime) {
        this.cinTime = cinTime;
    }

    public String getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }
}
