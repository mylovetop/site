package com.springapp.mvc.test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zdsoft on 14-7-7.
 */
public class TestServiceImpl implements TestService {

    @Override
    public List getAllObject() {
        System.out.println("---TestService：Cache内不存在该element，查找并放入Cache！");
        List<String> list = new ArrayList<String>();
        list.add("ehcache--1");
        list.add("ehcache--2");
        list.add("ehcache--3");
        return list;
    }

    @Override
    public void updateObject(Object object) {
        System.out.println("---TestService：更新了对象，这个Class产生的cache都将被remove！");
    }
}
