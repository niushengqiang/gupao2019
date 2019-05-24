package com.gupao.myblockqueue;

public interface MyBlockQueue<T> {

    <T> T take() throws InterruptedException;
    void put(T t) throws InterruptedException;
}
