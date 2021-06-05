package com.murphy.dao.impl;

import com.murphy.bean.Courier;
import com.murphy.dao.BaseCourierDao;
import com.murphy.exception.DuplicateCodeException;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class CourierDaoMysqlTest {

    BaseCourierDao dao = new CourierDaoMysql();

    @Test
    public void insert() throws DuplicateCodeException {
        Courier c = new Courier("John","17164539813","314011199312211613","c123123", 8);
        boolean insert = false;
        insert = dao.insert(c);
        System.out.println(insert);
    }

    @Test
    public void findBycName() {
        List<Courier> list = dao.findBycName("o");
        System.out.println(list);
    }

    @Test
    public void cConsole() {
        List<Map<String, Integer>> list = dao.cConsole();
        System.out.println(list);
    }

    @Test
    public void findAll() {
        List<Courier> all = dao.findAll(true,1,2);
        System.out.println(all);
    }

    @Test
    public void findBycPhone() {
        Courier c = dao.findBycPhone("19949231487");
        System.out.println(c);
    }

    @Test
    public void update() {
        Courier c = new Courier();
        c.setcName("Anki");
        c.setcPhone("18523418891");
        c.setIdNumber("210814198908191311");
        c.setPassword("c123456");
        boolean update = dao.update(2,c);
        System.out.println(update);
    }

    @Test
    public void delete() {
        boolean flag = dao.delete(7);
        System.out.println(flag);
    }
}