package com.liu.spring.springzuul.concurrent;

import com.liu.spring.springzuul.SpringZuulApplicationTests;

import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;

public class test {


    public static void main(String[] args) throws InterruptedException {
//        Lock lock = new ReentrantLock();
//        AtomicInteger data = new AtomicInteger(0);
//
//        ExecutorService executoe = Executors.newCachedThreadPool();
//        executoe.execute(new LockAddTask(lock, data));
//        executoe.execute(new LockAddTask(lock, data));
//
//        executoe.shutdown();
//        Thread.sleep(10);
//        System.out.println(data.get());
//
//        ConcurrentHashMap a = null;
//        a.get("");


        Map<SpringZuulApplicationTests, String> hashMap = new HashMap<SpringZuulApplicationTests, String>();
        Map<SpringZuulApplicationTests, String> identityMap = new IdentityHashMap<SpringZuulApplicationTests, String>();

        Map<String,String> map3 =new IdentityHashMap<String,String>(100);
        String a = new String("aaa");
        map3.put(a, "cc");
//        map3.put(128, "bb");
        map3.put("aaa", "dd");
        System.out.println(map3.size()); //3

    }


    static class LockAddTask implements Runnable{
        private Lock lock;
        private AtomicInteger data;

        public LockAddTask(Lock lock, AtomicInteger data)
        {
            this.lock = lock;
            this.data = data;
        }

        @Override
        public void run() {
            int count = 10;

            while (count-- > 0){
                try {
                    lock.lockInterruptibly();
                    data.getAndIncrement();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        }
    }




}
