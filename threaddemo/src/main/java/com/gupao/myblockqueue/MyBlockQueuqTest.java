package com.gupao.myblockqueue;

public class MyBlockQueuqTest {
    final static MyBlockQueue<String> stringMyBlockQueue = new MyBlockQueueImpl<String>();
    public static void main(String[] args) throws InterruptedException {
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        int  i=0;
                        for (;;){
                            i++;
                            try {
                                stringMyBlockQueue.put(String.valueOf(i));
                                System.out.println("成功写入"+i);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
        ).start();

        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        int  i=0;
                        for (;;){
                            i++;
                            try {
                                Object take = stringMyBlockQueue.take();
                                System.out.println("成功读取"+take);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
        ).start();


    }
}
