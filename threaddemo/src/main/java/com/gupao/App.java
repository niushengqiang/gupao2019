package com.gupao;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * author 钮胜强
 */
public class App {

    public static void main(String[] args) {
        ReadWriteLock lock=new ReentrantReadWriteLock();
        //================================================
        Lock rlock = lock.readLock();
        rlock.lock();
        rlock.unlock();

        //================================================
        Lock wlock = lock.writeLock();
        wlock.lock();
        wlock.unlock();
    }
}
