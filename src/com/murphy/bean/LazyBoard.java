package com.murphy.bean;

/**
 * 懒人排行 - 实体类
 *
 * @author murphy
 * @since 2021/6/12 10:42 上午
 */
public class LazyBoard implements Comparable<LazyBoard>{

    private String uPhone;
    private String uName;
    private Integer expressNum;

    public LazyBoard(String uPhone, String uName, Integer expressNum) {
        this.uPhone = uPhone;
        this.uName = uName;
        this.expressNum = expressNum;
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

    public Integer getExpressNum() {
        return expressNum;
    }

    public void setExpressNum(Integer expressNum) {
        this.expressNum = expressNum;
    }

    @Override
    public String toString() {
        return "LazyBoard{" +
                "uPhone='" + uPhone + '\'' +
                ", uName='" + uName + '\'' +
                ", expressNum=" + expressNum +
                '}';
    }

    @Override
    public int compareTo(LazyBoard o) {
        int i = o.getExpressNum() - this.getExpressNum();
        if (i == 0){
            return this.getuName().compareTo(o.getuName());
        }
        return i;
    }
}
