package ru.job4j.threads.wnn;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class SimpleBlockingQueueTest {

    @Test
    public void add() throws InterruptedException {
        final int numOfOffers = 100;
        final int queueSize = 10;
        int[] maxValue = new int[1];
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(queueSize);
        Thread threadProducer = new Thread(
                () ->
                {
                    try {
                        for (int i = 1; i <= numOfOffers; i++) {
                            while (queue.isFull()) {
                                Thread.sleep(10);
                            }
                            queue.offer(i);
                            System.out.println("добавил:" + i + "    размер:" + queue.getCurrentSize());
                            maxValue[0] = Math.max(maxValue[0], queue.getCurrentSize());

                        }
                        while (!queue.isEmpty()) {
                            Thread.sleep(1000);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("producer out");
                }

        );
        Integer[] value = new Integer[2];
        value[1] = 0;
        Thread threadConsumer = new Thread(
                () ->
                {
                    try {
                        while (!queue.isFull()) {
                            Thread.sleep(1000);
                        }
                        while (!threadProducer.isInterrupted()) {
                            queue.poll();
                            System.out.println("удалил. размер: " + queue.getCurrentSize());
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("consumer out");
                }
        );
        threadConsumer.start();
        threadProducer.start();
        threadConsumer.join();
        while (!queue.isEmpty()) {
            Thread.sleep(1000);
        }
        notifyAll();
        threadConsumer.interrupt();
        assertThat(maxValue[0], is(queueSize));
    }
}