package ru.job4j.threads.threads;

public class ThreadState {
    public static void main(String[] args) {
        Thread firstThread = new Thread(
                () -> {
                    System.out.println(Thread.currentThread().getName());
                    for (long i = 0; i < 10000000000L; i++) {
                        i = i + 100 - 100;
                    }
                }
        );
        Thread secondThread = new Thread(
                () -> {
                    System.out.println(Thread.currentThread().getName());
                }
        );
        firstThread.start();
        secondThread.start();
        while (firstThread.getState() != Thread.State.TERMINATED
                || secondThread.getState() != Thread.State.TERMINATED) {
            System.out.print("\rFirst thread state: "
                    + firstThread.getState()
                    + "   Second thread state: "
                    + secondThread.getState());
        }
        System.out.println();
        System.out.println("All threads are terminated");
    }
}