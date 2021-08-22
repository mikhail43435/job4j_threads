package ru.job4j.threads.nba;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicInteger;

@ThreadSafe
public class CASCount {
    private final AtomicInteger count = new AtomicInteger(0);

    public void increment() {
        AtomicInteger currentValue = new AtomicInteger(0);
        do {
            currentValue.set(count.get());
        } while (!count.compareAndSet(currentValue.get(), currentValue.get() + 1));
    }

    public void decrement() {
        AtomicInteger currentValue = new AtomicInteger(0);
        do {
            currentValue.set(count.get());
        } while (!count.compareAndSet(currentValue.get(), currentValue.get() - 1));
    }

    public synchronized int get() {
        return count.get();
    }
}