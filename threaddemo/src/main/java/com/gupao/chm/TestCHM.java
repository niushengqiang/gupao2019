package com.gupao.chm;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TestCHM {
    public static void main(String[] args) {
        Map<Object, Object> map = new ConcurrentHashMap<>();
        map.put("1","1");

    }
}
