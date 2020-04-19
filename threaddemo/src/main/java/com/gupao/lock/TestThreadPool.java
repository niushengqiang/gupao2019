package com.gupao.lock;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TestThreadPool {
    public static void main(String[] args) {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 20, 20, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
        System.out.println("执行开始");
        for (int i = 0; i < 1000; i++) {
            threadPoolExecutor.submit(new Runnable() {
                @Override
                public void run() {
                    if(1==1)throw new RuntimeException();
                }
            });
            threadPoolExecutor.submit(new Runnable() {
                @Override
                public void run() {
                    if(1==1)throw new RuntimeException();
                }
            });
        }
        System.out.println("执行结束");
    }
}
