package ru.sbrf;

import java.util.concurrent.CountDownLatch;

public class Worker implements Runnable {
    private int id;
    private volatile Integer count;
    private CountDownLatch latch;

    public Worker(int id, Integer count, CountDownLatch latch) {
        this.id = id;
        this.count = count;
        this.latch = latch;
    }

    @Override
    public void run() {
        while (count <= 10) {
            synchronized (latch) {
                if (count % 3 == id) {
                    System.out.println("Thread: " + id + ":" + count);
                    count++;
                    latch.countDown();
                }
            }
        }
    }

}
