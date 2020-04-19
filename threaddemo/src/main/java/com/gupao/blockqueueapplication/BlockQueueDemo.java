package com.gupao.blockqueueapplication;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;


/***
 * 在我们的实际工作当时是有基于队列做的消息消费.
 * 因公司的代码不便于公开.这里写一个demo
 *
 */
public class BlockQueueDemo {

    private  static final BlockingQueue<String> blockingQueue=new LinkedBlockingQueue();

    public static void main(String[] args) throws InterruptedException {
        new Thread(() ->{
            for (int i=0;;i++){
                //在实际的业务当中写的是 任务id ,基于redis存储
                blockingQueue.add(i+"");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        Thread t2 = new Thread(() -> {
            //在实际的业务当中写的是 任务id ,基于redis存储
            while (true) {
                String poll = blockingQueue.poll();
                if (poll == null) continue;
                System.out.println("获取的数据为" + poll + "进行下一步的业务处理");
            }
        });
        t2.start();
        t2.join();



    }

}
