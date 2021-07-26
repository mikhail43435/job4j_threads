package ru.job4j.threads.nba;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
public class CASCount {
    private final AtomicReference<Integer> count = new AtomicReference<>(0);

    public void increment() {
        AtomicInteger currentValue = new AtomicInteger(0);
        do {
            currentValue.set(count.get());
        } while (!count.compareAndSet(currentValue.get(), currentValue.get() + 1));
        //System.out.println(count.get());
    }

    public void decrement() {
        AtomicInteger currentValue = new AtomicInteger(0);
        do {
            currentValue.set(count.get());
        } while (!count.compareAndSet(currentValue.get(), currentValue.get() - 1));
        //System.out.println(count.get());
    }

    public synchronized int get() {
        return count.get();
    }
}