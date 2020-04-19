package com.gupao.lock;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class TestCyclicBarrier extends Thread {

    @Override
    public void run() {
        System.out.println("开始进行数 据分析");
    }

    public static void main(String[] args) {
        CyclicBarrier cycliBarrier = new CyclicBarrier(3, new TestCyclicBarrier());
        new Thread(new DataImportThread(cycliBarrier, "file 1")).start();
        new Thread(new DataImportThread(cycliBarrier, "file 2")).start();
        new Thread(new DataImportThread(cycliBarrier, "file 3")).start();
    }


    static class DataImportThread extends Thread {
        private CyclicBarrier cyclicBarrier;
        private String path;
        public DataImportThread(CyclicBarrier cyclicBarrier, String path) {
            this.cyclicBarrier = cyclicBarrier;
            this.path = path;
        }

        @Override
        public void run() {
            System.out.println("开始导入： " + path + "位置的数据");
            try {
                cyclicBarrier.await();// 阻塞
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            System.out.println("开始导入： " + path + "位置的数据完成" );
        }
    }


}
