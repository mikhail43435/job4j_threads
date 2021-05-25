package ru.job4j.threads.concurrent;

public class ConsoleProgress implements Runnable {

    public void run() {
        try {
            int counter = 0;
            String[] symbols = {"|", "/", "-", "\\"};
            while (!Thread.currentThread().isInterrupted()) {
                System.out.print("\r loading: " + symbols[counter++]);
                if (counter == 4) counter = 0;
                Thread.sleep(300);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        Thread.sleep(4000);
        progress.interrupt();
    }
}