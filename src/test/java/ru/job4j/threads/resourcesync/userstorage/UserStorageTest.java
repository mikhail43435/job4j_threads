package ru.job4j.threads.resourcesync.userstorage;

import junit.framework.TestCase;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class UserStorageTest extends TestCase {

    private static class ThreadTransfer1 extends Thread {
        private final UserStorage userStorage;

        private ThreadTransfer1(final UserStorage userStorage) {
            this.userStorage = userStorage;
        }

        @Override
        public void run() {
            for (int i = 0; i < 2000000; i++) {
                this.userStorage.transfer(1, 2, 1);
            }
        }
    }

    private static class ThreadTransfer2 extends Thread {
        private final UserStorage userStorage;

        private ThreadTransfer2(final UserStorage userStorage) {
            this.userStorage = userStorage;
        }

        @Override
        public void run() {
            for (int i = 0; i < 2000000; i++) {
                this.userStorage.transfer(2, 1, 1);
            }
        }
    }

    @Test
    public void testTransfer() throws InterruptedException {
        final UserStorage userStorage = new UserStorage();
        //userStorage.add(new User(1, new AtomicInteger(2000000)));
        userStorage.add(new User(1, 2000000000));
        //userStorage.add(new User(2, new AtomicInteger(2000000)));
        userStorage.add(new User(2, 2000000000));
        Thread first = new ThreadTransfer1(userStorage);
        Thread second = new ThreadTransfer2(userStorage);
        first.start();
        second.start();
        first.join();
        second.join();
        assertThat(userStorage.getUserById(1).getAmount(), is(2000000000));
        assertThat(userStorage.getUserById(2).getAmount(), is(2000000000));
    }
}