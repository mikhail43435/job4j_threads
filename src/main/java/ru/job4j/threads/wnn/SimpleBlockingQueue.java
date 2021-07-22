package ru.job4j.threads.wnn;

import net.jcip.annotations.*;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {

    private final Object monitor = this;
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
            while (queue.size() >= sizeLimit) {
                monitor.wait();
            }
            queue.offer(value);
            notifyAll();
        }
    }

    public T poll() throws InterruptedException {
        T result = null;
        synchronized (monitor) {
            while (queue.isEmpty()) {
                monitor.wait();
            }
            result = queue.poll();
            notifyAll();
        }
        return result;
    }

    public synchronized boolean isEmpty() {
        return queue.isEmpty();
    }

    public synchronized int getCurrentSize() {
        return queue.size();
    }
}