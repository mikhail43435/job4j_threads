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
        Thread newTread = null;
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

    public boolean hasPerformingTasks() {
        for (int i = 0; i < poolSize; i++) {
            System.out.println(threads.get(i).getState());
            if (threads.get(i).isAlive()
                    && threads.get(i).getState() != Thread.State.WAITING) {
                return true;
            }
        }
        return false;
    }

    public boolean allThreadsAreShutdown() {
        for (int i = 0; i < poolSize; i++) {
            System.out.println(threads.get(i).getState());
            if (threads.get(i).isAlive()) {
                return true;
            }
        }
        return false;
    }

    private class InnerThread extends Thread {

        @Override
        public void run() {
            Runnable task = null;
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    task = tasks.poll();
                    if (task == null) {
                        wait();
                    } else {
                        System.out.println("Thread <" + currentThread().getName()
                                + "> has started make job <" + task.toString());
                        task.run();
                    }
                }
            } catch (InterruptedException e) {
                System.out.println("Thread is interrupted: " + currentThread().getName());
                e.printStackTrace();
            }
        }
    }
}