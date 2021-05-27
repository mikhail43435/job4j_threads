package ru.job4j.threads.atom;

public class Count {
    private int value;

    public synchronized void increment() {
        for (int i = 0; i < 100000; i++) {
            value++;
        }

    }

    public synchronized int get() {
        return value;
    }
}
