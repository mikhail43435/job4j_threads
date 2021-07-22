package ru.job4j.threads.wnn;

public class ParallelSearch {

    public static void main(String[] args) throws InterruptedException {


        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(1000);
        final Thread producer = new Thread(
                () -> {
                    for (int index = 0; index != 30; index++) {
                        try {
                            queue.offer(index);
                                Thread.sleep(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        producer.start();
        final Thread consumer = new Thread(
                () -> {
                    try {
                        while (queue.isEmpty()) {
                            Thread.sleep(3000);
                        }
                        while (producer.isAlive() || !queue.isEmpty()) {
                            queue.poll();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        Thread.currentThread().interrupt();
                    }
                }
        );
        consumer.start();
        producer.join();
        consumer.interrupt();
}
}