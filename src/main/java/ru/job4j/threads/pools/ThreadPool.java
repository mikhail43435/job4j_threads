package ru.job4j.threads.pools;

import net.jcip.annotations.NotThreadSafe;
import ru.job4j.threads.wnn.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

@NotThreadSafe
public class ThreadPool {
    private final int poolSize = Runtime.getRuntime().availableProcessors();
    private final List<Thread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(100);

    public ThreadPool() {
        Thread newTread;
        for (int i = 0; i < poolSize; i++) {
            newTread = new InnerThread();
            threads.add(newTread);
            newTread.start();
        }
        System.out.println("Pool successfully created. Number of thread(s): " + poolSize);
    }

    public void work(Runnable job) throws InterruptedException {
        tasks.offer(job);
    }

    public void shutdownAllThreads() {
        for (Thread thread : threads) {
            thread.interrupt();
        }
    }

    private class InnerThread extends Thread {

        @Override
        public void run() {
            Runnable task = null;
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    task = tasks.poll();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
                System.out.println("Thread <" + currentThread().getName()
                            + "> has started make job <" + task.toString());
                    task.run();
                }
        }
    }
}