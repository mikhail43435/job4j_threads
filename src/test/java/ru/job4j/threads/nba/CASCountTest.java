package ru.job4j.threads.nba;

import junit.framework.TestCase;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class CASCountTest {

    @Test
    public void whenExecute2ThreadThen2() throws InterruptedException {
        final CASCount count = new CASCount();
        int limit = 200;
        Thread threadUp = new Thread(
                () -> {
                    for (int i = 0; i < limit; i++) {
                        count.increment();
                    }
                }
        );
        int[] counterForDownThreads = new int[2];
        Thread threadDown1 = new Thread(
                () -> {
                    while (count.get() > 0) {
                        count.decrement();
                        System.out.println("deleted1");
                        System.out.println(count.get());
                        counterForDownThreads[0]++;
                    }
                }
        );
        Thread threadDown2 = new Thread(
                () -> {
                    while (count.get() > 0) {
                        count.decrement();
                        System.out.println("deleted2");
                        System.out.println(count.get());
                        counterForDownThreads[1]++;
                    }
                }
        );
        threadUp.start();
        threadDown1.start();
        threadDown2.start();
        threadUp.join();
        threadDown1.join();
        threadDown2.join();
        System.out.println(">>>>" + count.get());
        assertThat(counterForDownThreads[0] + counterForDownThreads[1], is(limit));
    }
}