package com.gupao.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 */
public class TestReentrantLock {

    private  static  Lock lock=new ReentrantLock();

    private static int j=0;

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 100; i++) {
            new Thread(()->{
                try{
                    lock.lock();
                    j++;
                }finally {
                    lock.unlock();
                }
            }).start();
        }
        TimeUnit.SECONDS.sleep(20);
        System.out.println("最终获得的结果是:"+j);

    }
}
