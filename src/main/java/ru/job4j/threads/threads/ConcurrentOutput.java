package ru.job4j.threads.threads;

public class ConcurrentOutput {

    public static void main(String[] args) {
        Thread another = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );
        Thread secondThread = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );
        another.start();
        secondThread.start();
        System.out.println(Thread.currentThread().getName());
    }
}
