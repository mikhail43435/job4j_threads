package ru.job4j.threads.nba;

import junit.framework.TestCase;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class CASCountTest {

    @Test
    public void whenExecute2ThreadThen2() throws InterruptedException {
        final CASCount count = new CASCount();
        int limit = 100;
        Thread threadUp = new Thread(
                () -> {
                    for (int i = 0; i < limit; i++) {
                        count.increment();
                    }
                }
        );
        int[] counterDown = new int[2];
        Thread threadDown1 = new Thread(
                () -> {
                    while (count.get() > 0) {
                        count.decrement();
                        System.out.println("deleted1");
                        System.out.println(count.get());
                        counterDown[0]++;
                    }
                }
        );
        Thread threadDown2 = new Thread(
                () -> {
                    while (count.get() > 0) {
                        count.decrement();
                        System.out.println("deleted2");
                        System.out.println(count.get());
                        counterDown[1]++;
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
        assertThat(counterDown[0] + counterDown[1], is(limit));
    }
}