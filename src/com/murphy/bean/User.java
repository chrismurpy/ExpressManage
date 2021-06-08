package com.murphy.bean;

import java.sql.Timestamp;
import java.util.Objects;

/**
 * 用户 - 实体类
 *
 * @author murphy
 * @since 2021/6/7 9:15 下午
 */
public class User {
    private Integer uId;
    private String uName;
    private String uPhone;
    private String password;
    private String idNumber;
    private Timestamp uinTime;
    private Timestamp lastLogin;
    private boolean user;

    public boolean isUser() {
        return user;
    }

    public void setUser(boolean user) {
        this.user = user;
    }

    public User(Integer uId, String uName, String uPhone, String password, String idNumber, Timestamp uinTime, Timestamp lastLogin) {
        this.uId = uId;
        this.uName = uName;
        this.uPhone = uPhone;
        this.password = password;
        this.idNumber = idNumber;
        this.uinTime = uinTime;
        this.lastLogin = lastLogin;
    }

    public User(String uName, String uPhone, String password, String idNumber) {
        this.uName = uName;
        this.uPhone = uPhone;
        this.password = password;
        this.idNumber = idNumber;
    }

    public User() {
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

    public Timestamp getUinTime() {
        return uinTime;
    }

    public void setUinTime(Timestamp uinTime) {
        this.uinTime = uinTime;
    }

    public Timestamp getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Timestamp lastLogin) {
        this.lastLogin = lastLogin;
    }

    @Override
    public String toString() {
        return "User{" +
                "uId=" + uId +
                ", uName='" + uName + '\'' +
                ", uPhone='" + uPhone + '\'' +
                ", password='" + password + '\'' +
                ", uinTime=" + uinTime +
                ", lastLogin=" + lastLogin +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(uId, user.uId) &&
                Objects.equals(uName, user.uName) &&
                Objects.equals(uPhone, user.uPhone) &&
                Objects.equals(password, user.password) &&
                Objects.equals(uinTime, user.uinTime) &&
                Objects.equals(lastLogin, user.lastLogin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uId, uName, uPhone, password, uinTime, lastLogin);
    }
}
