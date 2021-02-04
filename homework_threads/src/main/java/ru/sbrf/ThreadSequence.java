package ru.sbrf;

import java.util.concurrent.atomic.AtomicInteger;

public class ThreadSequence {
    private static AtomicInteger atomicInteger = new AtomicInteger(1);

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new WorkerThread(0, atomicInteger));
        Thread t2 = new Thread(new WorkerThread(1, atomicInteger));
        Thread t3 = new Thread(new WorkerThread(2, atomicInteger));
        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();

        System.out.println("Done with main");
    }
}


public class WorkerThread implements Runnable {
    private int id;
    private AtomicInteger atomicInteger;

    public WorkerThread(int id, AtomicInteger atomicInteger) {
        this.id = id;
        this.atomicInteger = atomicInteger;
    }

    @Override
    public void run() {
        while (atomicInteger.get() < 10) {
            synchronized (atomicInteger) {
                if (atomicInteger.get() % 3 == id) {
                    System.out.println("Thread:" + id + " = " + atomicInteger);
                    atomicInteger.incrementAndGet();
                }
            }

        }
    }
}