package com.gupao.myblockqueue;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MyBlockQueueImpl<T> implements MyBlockQueue{

    private Lock lock;
    private Condition takeCondition;
    private Condition putCondition;
    private LinkedList<T> list;
    private final int maxSize;

    public MyBlockQueueImpl() {
        this(10);
    }

    public MyBlockQueueImpl(int size) {
        lock=new ReentrantLock();
        takeCondition=lock.newCondition();
        putCondition=lock.newCondition();
        list=new LinkedList();
        maxSize=size;
    }

    @Override
    public Object take() throws InterruptedException {
        this.lock.lock();
        try{
            while(list.size()==0){
                takeCondition.await();
            }
            T poll = list.poll();
            putCondition.signal();
            return poll;
        }finally {
            this.lock.unlock();
        }
    }

    @Override
    public void put(Object o) throws InterruptedException {
        try{
            this.lock.lock();
            while(list.size()>=maxSize){
                putCondition.await();
            }
            list.add((T)o);
            takeCondition.signal();
        }finally {
            this.lock.unlock();
        }
    }




}
