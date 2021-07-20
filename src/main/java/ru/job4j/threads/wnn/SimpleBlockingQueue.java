package ru.job4j.threads.wnn;

import net.jcip.annotations.*;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;

@ThreadSafe
public class SimpleBlockingQueue<T> {

    private final Object monitor = this;
    //@GuardedBy("monitor")
    private AtomicInteger size = new AtomicInteger(0);
    @GuardedBy("monitor")
    private final Queue<T> queue = new LinkedList<>();
    private final int sizeLimit;

    public SimpleBlockingQueue(int sizeLimit) {
        if (sizeLimit < 0) {
            throw new IllegalArgumentException("Illegal size value: " + sizeLimit);
        }
        this.sizeLimit = sizeLimit;
    }

    public void offer(T value) throws InterruptedException {
        synchronized (monitor) {
            while (size.get() >= sizeLimit) {
                monitor.wait();
            }
            queue.offer(value);
            size.incrementAndGet();
            notifyAll();
        }
    }

    public T poll() {
        T result = null;
        synchronized (monitor) {
            while (size.get() == 0) {
                try {
                    monitor.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
            }
            result = queue.poll();
            size.decrementAndGet();
            notifyAll();
        }
        return result;
    }

    public int getSizeLimit() {
        return sizeLimit;
    }

    public synchronized boolean isFull() {
        return size.get() == sizeLimit;
    }

    public synchronized boolean isEmpty() {
        return size.get() == 0;
    }

    public synchronized int getCurrentSize() {
        return size.get();
    }
}