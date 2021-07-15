package ru.job4j.threads.wnn;

import net.jcip.annotations.ThreadSafe;

@ThreadSafe
public class CountBarrier {

    private final Object monitor = this;
    private final int total;
    private int count = 0;

    public CountBarrier(final int total) {
        this.total = total;
    }

    public void count() {
        synchronized (monitor) {
            count++;
            System.out.println(count);
                monitor.notifyAll();
        }
    }

    public void await() {
        synchronized (monitor) {
            while (count < total) {
                try {
                    monitor.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
            }
            System.out.println("I am here...");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        CountBarrier cb = new CountBarrier(10);
        Thread thread1 = new Thread(
                () -> {
                    for (int i = 0; i < 30; i++) {
                        cb.count();
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            Thread.currentThread().interrupt();
                        }
                    }
                });
        Thread thread2 = new Thread(cb::await);
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
    }
}