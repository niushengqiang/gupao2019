package com.gupao.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TestReetrantLockCondition {


    public static void main(String[] args) throws InterruptedException {
        ReentrantLock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        ConditionDemoWait conditionDemoWait = new ConditionDemoWait(lock,condition);
        ConditionDemoSignal conditionDemoSignal = new ConditionDemoSignal(lock,condition);
        new Thread(conditionDemoWait).start();
        TimeUnit.SECONDS.sleep(1);
        new Thread(conditionDemoSignal).start();
    }

    static class ConditionDemoWait implements
            Runnable{

        private Lock lock;
        private Condition condition;
        public ConditionDemoWait(Lock lock,
                                 Condition condition){
            this.lock=lock;
            this.condition=condition;
        }

        @Override
        public void run() {
            System.out.println("begin ConditionDemoWait");
            try {
                lock.lock();
                condition.await();
                System.out.println("end -ConditionDemoWait");
            } catch ( Exception e) {
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        }
    }

    static class ConditionDemoSignal implements
            Runnable{

        private Lock lock;
        private Condition condition;
        public ConditionDemoSignal(Lock lock,
                                   Condition condition){
            this.lock=lock;
            this.condition=condition;
        }

        @Override
        public void run() {
            System.out.println("begin ConditionDemoSignal");
            try {
                lock.lock();
                 condition.signal();
                System.out.println("end-ConditionDemoSignal");
            }finally {
                lock.unlock();
            }
        }
    }


}
