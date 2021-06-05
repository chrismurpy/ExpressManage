package com.murphy.bean;

import java.sql.Timestamp;
import java.util.Objects;

/**
 * 快递员 - 实体类
 *
 * @author murphy
 * @since 2021/6/5 5:45 下午
 */
public class Courier {
    private Integer cId;
    private String cName;
    private String cPhone;
    private String idNumber;
    private String password;
    private Integer cNumber;
    private Timestamp cinTime;
    private Timestamp lastLogin;

    public Courier(Integer cId, String cName, String cPhone, String idNumber, String password, Integer cNumber, Timestamp cinTime, Timestamp lastLogin) {
        this.cId = cId;
        this.cName = cName;
        this.cPhone = cPhone;
        this.idNumber = idNumber;
        this.password = password;
        this.cNumber = cNumber;
        this.cinTime = cinTime;
        this.lastLogin = lastLogin;
    }

    public Courier() {
    }

    public Courier(String cName, String cPhone, String idNumber, String password, Integer cNumber) {
        this.cName = cName;
        this.cPhone = cPhone;
        this.idNumber = idNumber;
        this.password = password;
        this.cNumber = cNumber;
    }

    public Courier(String cName, String cPhone, String idNumber, String password) {
        this.cName = cName;
        this.cPhone = cPhone;
        this.idNumber = idNumber;
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Courier courier = (Courier) o;
        return Objects.equals(cId, courier.cId) &&
                Objects.equals(cName, courier.cName) &&
                Objects.equals(cPhone, courier.cPhone) &&
                Objects.equals(idNumber, courier.idNumber) &&
                Objects.equals(password, courier.password) &&
                Objects.equals(cNumber, courier.cNumber) &&
                Objects.equals(cinTime, courier.cinTime) &&
                Objects.equals(lastLogin, courier.lastLogin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cId, cName, cPhone, idNumber, password, cNumber, cinTime, lastLogin);
    }

    @Override
    public String toString() {
        return "Courier{" +
                "cId=" + cId +
                ", cName='" + cName + '\'' +
                ", cPhone='" + cPhone + '\'' +
                ", idNumber='" + idNumber + '\'' +
                ", password='" + password + '\'' +
                ", cNumber=" + cNumber +
                ", cinTime=" + cinTime +
                ", lastLogin=" + lastLogin +
                '}';
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

    public Timestamp getCinTime() {
        return cinTime;
    }

    public void setCinTime(Timestamp cinTime) {
        this.cinTime = cinTime;
    }

    public Timestamp getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Timestamp lastLogin) {
        this.lastLogin = lastLogin;
    }
}
