package com.gupao.lock;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class TestCountDownLatch{
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch=new   CountDownLatch(3);
        new Thread(()->{
            System.out.println(""+Thread.currentThread().getName()+"-执行开始");
            countDownLatch.countDown();
            System.out.println(""+Thread.currentThread().getName()+"-执行完毕");
        },"t1").start();

        new Thread(()->{
            System.out.println(""+Thread.currentThread().getName()+"-执行开始");
            countDownLatch.countDown();
            System.out.println(""+Thread.currentThread().getName()+"-执行完毕");
        },"t2").start();

        new Thread(()->{
            System.out.println(""+Thread.currentThread().getName()+"-执行开始");
            countDownLatch.countDown();
            System.out.println(""+Thread.currentThread().getName()+"-执行完毕");
        },"t3").start();


        TimeUnit.SECONDS.sleep(1);
         countDownLatch.await();
        System.out.println("所有线程执行完毕");
    }
    
    
}
