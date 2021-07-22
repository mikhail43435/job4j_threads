package ru.job4j.threads.nba;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
public class CASCount {
    private final AtomicReference<Integer> count = new AtomicReference<>(0);

    public void increment() {
        int currentValue;
        do {
            currentValue = count.get();
        } while (!count.compareAndSet(currentValue, currentValue + 1));
        //System.out.println(count.get());
    }

    public void decrement() {
        int currentValue;
        do {
            currentValue = count.get();
        } while (!count.compareAndSet(currentValue, currentValue - 1));
        //System.out.println(count.get());
    }

    public synchronized int get() {
        return count.get();
    }
}