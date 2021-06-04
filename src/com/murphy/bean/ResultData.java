package com.murphy.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据存储
 *
 * @author murphy
 * @since 2021/6/4 5:26 下午
 */
public class ResultData<T> {

    /**
     * 存储分页的数据 - 每次查询的数据集合
     */
    private List<T> rows = new ArrayList<>();
    /**
     * 数据的总量
     */
    private int total;

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
