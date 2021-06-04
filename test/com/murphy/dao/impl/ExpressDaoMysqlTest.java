package com.murphy.dao.impl;

import com.murphy.bean.Express;
import com.murphy.dao.BaseExpressDao;
import com.murphy.exception.DuplicateCodeException;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class ExpressDaoMysqlTest {

    BaseExpressDao dao = new ExpressDaoMysql();

    @Test
    public void console() {
        List<Map<String, Integer>> console = dao.console();
        System.out.println(console);
    }

    @Test
    public void findAll() {
//        List<Express> all = dao.findAll(true, 2, 2); 3-4
        List<Express> all = dao.findAll(false, 0, 0);
        System.out.println(all);
    }

    @Test
    public void findByNumber() {
        Express e = dao.findByNumber("JD123456");
        System.out.println(e);
    }

    @Test
    public void findByCode() {
        Express e = dao.findByCode("458124");
        System.out.println(e);
    }

    @Test
    public void findByUserPhone() {
        List<Express> list = dao.findByUserPhone("14782149325");
        System.out.println(list);
    }

    @Test
    public void findBySysPhone() {
        List<Express> li = dao.findBySysPhone("18888888888");
        System.out.println(li);
    }

    @Test
    public void insert() {
        Express e = new Express("ST524999","Qianzai","18723810291","申通快递","18888889999","481202");
        boolean insert = false;
        try {
            insert = dao.insert(e);
        } catch (DuplicateCodeException duplicateCodeException) {
            System.out.println("取件码重复的异常被捕获到了！");
        }
        System.out.println(insert);
    }

    @Test
    public void update() {
        Express e = new Express();
        e.setNumber("SF124990");
        e.setCompany("顺丰快递");
        e.setUsername("Qianzai");
        e.setStatus(0);
        boolean update = dao.update(11, e);
        System.out.println(update);
    }

    @Test
    public void updateStatus() {
        boolean flag = dao.updateStatus("481202");
        System.out.println(flag);
    }

    @Test
    public void delete() {
        boolean flag = dao.delete(4);
        System.out.println(flag);
    }
}