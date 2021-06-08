package com.murphy.dao.impl;

import com.murphy.bean.User;
import com.murphy.dao.BaseUserDao;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class UserDaoMysqlTest {

    BaseUserDao dao = new UserDaoMysql();

    @Test
    public void uConsole() {
        List<Map<String, Integer>> list = dao.uConsole();
        System.out.println(list);
    }

    @Test
    public void findAll() {
        List<User> all = dao.findAll(true,0,2);
        System.out.println(all);
    }

    @Test
    public void findByuPhone() {
        User u = dao.findByuPhone("15805703033");
        System.out.println(u);
    }

    @Test
    public void findByuName() {
        List<User> list = dao.findByuName("i");
        System.out.println(list);
    }

    @Test
    public void insert() {
        User u = new User("qianzai","15105704581","610121199912271425","qianzai123");
        boolean insert = false;
        insert = dao.insert(u);
        System.out.println(insert);
    }

    @Test
    public void update() {
        User u = new User();
        u.setuName("Qianzi");
        u.setuPhone("15105704580");
        u.setPassword("yangqian111");
        u.setIdNumber("610121199912271427");
        boolean update = dao.update(3,u);
        System.out.println(update);
    }

    @Test
    public void delete() {
        boolean flag = dao.delete(4);
        System.out.println(flag);
    }
}