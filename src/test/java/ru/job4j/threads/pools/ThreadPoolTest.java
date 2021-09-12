package ru.job4j.threads.pools;

import org.junit.Test;

import static java.lang.Thread.sleep;

public class ThreadPoolTest {

  /*  @Test
    public void testThenAddCountersAndWait() throws InterruptedException {
        ThreadPool threadPool = new ThreadPool();
        for (int i = 0; i < 100; i++) {
            threadPool.work(new InnerClassCounter("Instance " + i));
        }
        while (threadPool.hasPerformingTasks()) {
            sleep(1000);
        }
    }

    @Test
    public void testThenAddLoopsAndShutdownThem() throws InterruptedException {
        ThreadPool threadPool = new ThreadPool();
        for (int i = 0; i < 20; i++) {
            threadPool.work(new InnerClassLoop("Instance " + i));
        }
        sleep(10000);
        threadPool.shutdownAllThreads();
        while (!threadPool.allThreadsAreShutdown()) {
            sleep(1000);
        }
    }

    private class InnerClassCounter extends Thread {
        private final String instanceName;

        private InnerClassCounter(String instanceName) {
            this.instanceName = instanceName;
        }

        @Override
        public void run() {
            for (int i = 0; i < 2_000_000; i++) {
            }
            System.out.println("Task is finished: " + instanceName);
        }

        @Override
        public String toString() {
            return "Instance name: counter > " + instanceName;
        }
    }

    private class InnerClassLoop extends Thread {
        private final String instanceName;

        private InnerClassLoop(String instanceName) {
            this.instanceName = instanceName;
        }

        @Override
        public void run() {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    sleep(100);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        @Override
        public String toString() {
            return "Instance name: loop > " + instanceName;
        }
    }*/
}