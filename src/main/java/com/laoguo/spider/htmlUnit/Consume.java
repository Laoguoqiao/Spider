package com.laoguo.spider.htmlUnit;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Consume implements Runnable {
    private static ConcurrentLinkedQueue<String> concurrentLinkedQueue=new ConcurrentLinkedQueue<String>();
    public  volatile boolean stop = false;   //共享变量

    public Consume(ConcurrentLinkedQueue queue){
        concurrentLinkedQueue =queue;
    }
    @Override
    public void run() {
         while(concurrentLinkedQueue.size()!=0){
             try {
                 HttpUnitCraw.getElement(concurrentLinkedQueue.poll());
                 //System.out.println( Thread.currentThread().getName()+":"+concurrentLinkedQueue.poll());
             } catch (Exception e) {
                 e.printStackTrace();
             }
         }
         if(concurrentLinkedQueue.size()==0){
             stop=true;
         }
    }
}
