package com.murphy.service;

import com.murphy.bean.Express;
import org.junit.Test;

import static org.junit.Assert.*;

public class ExpressServiceTest {

    @Test
    public void insert() {
        Express e = new Express("ST524999","Qianzai","18723810291","申通快递","18888889999","");
        boolean flag = ExpressService.insert(e);
        System.out.println(flag);
    }
}