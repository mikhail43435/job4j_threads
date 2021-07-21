package ru.job4j.threads.wnn;

import net.jcip.annotations.*;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;

@ThreadSafe
public class SimpleBlockingQueue<T> {

    private final Object monitor = this;
    private final AtomicInteger size = new AtomicInteger(0);
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

    public T poll() throws InterruptedException {
        T result = null;
        synchronized (monitor) {
            while (size.get() == 0) {
                    monitor.wait();
            }
            result = queue.poll();
            size.decrementAndGet();
            notifyAll();
        }
        return result;
    }

    public synchronized boolean isEmpty() {
        return size.get() == 0;
    }

    public synchronized int getCurrentSize() {
        return size.get();
    }
}