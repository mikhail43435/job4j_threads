package ru.job4j.threads.concurrent.filedownloader;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;

public class WgetDF implements Runnable {
    private final String url;
    private final int speed;

    public WgetDF(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    @Override
    public void run() {
        System.out.println("Downloading stated at :" + LocalDateTime.now());
        try (var in = new BufferedInputStream(new URL(url).openStream());
             var fileOutputStream = new FileOutputStream("pom_tmp.xml")) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            long time1;
            long time2;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                time1 = System.currentTimeMillis();
                fileOutputStream.write(dataBuffer, 0, bytesRead);
                time2 = System.currentTimeMillis();
                if ((time2 - time1) < speed) {
                    Thread.sleep(speed - (time2 - time1));
                }
            }
        } catch (InterruptedException | IOException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("Downloading ended at :" + LocalDateTime.now());
    }

    public static void main(String[] args) throws InterruptedException {
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        Thread wget = new Thread(new WgetDF(url, speed));
        wget.start();
        wget.join();
    }
}