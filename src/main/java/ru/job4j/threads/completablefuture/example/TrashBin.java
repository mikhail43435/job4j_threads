package ru.job4j.threads.completablefuture.example;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * Допустим вы очень занятой человек и часто берете работу на дом.
 * Но дома тоже есть свои дела. Например, сходить выбросить мусор.
 * Вам некогда этим заниматься, но у вас есть сын, который может это сделать.
 * Вы сами работаете, а ему поручаете это дело.
 * Это проявление асинхронности, т.к. вы сами работаете, а тем временем ваш сын выбрасывает мусор.
 * Сымитируем эту ситуацию.
 */

public class TrashBin {

    public static void iWork() throws InterruptedException {
        int count = 0;
        while (count < 10) {
            System.out.println("Вы: Я работаю");
            TimeUnit.SECONDS.sleep(1);
            count++;
        }
    }

    public static CompletableFuture<Void> goToTrash() {
        return CompletableFuture.runAsync(
                () -> {
                    System.out.println("Сын: Мам/Пам, я пошел выносить мусор");
                    try {
                        TimeUnit.SECONDS.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Сын: Мам/Пап, я вернулся!");
                }
        );
    }

    public static void runAsyncExample() throws Exception {
        CompletableFuture<Void> gtt = goToTrash();
        iWork();
    }

    public static void thenRunExample() throws Exception {
        CompletableFuture<Void> gtt = goToTrash();
        gtt.thenRun(() -> {
            int count = 0;
            while (count < 3) {
                System.out.println("Сын: я мою руки");
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                count++;
            }
            System.out.println("Сын: Я помыл руки");
        });
        iWork();
    }

    public static void main(String[] args) throws Exception {
        thenRunExample();
    }
}
