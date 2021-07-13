package ru.job4j.threads.resourcesync.userstorage;

import junit.framework.TestCase;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class UserStorageTest extends TestCase {

    private final AtomicInteger counter = new AtomicInteger(0);

    @Test
    public void testMultiThreadThenTransfer() throws InterruptedException {
        final UserStorage userStorage = new UserStorage();
        userStorage.add(new User(1, 2000000000));
        userStorage.add(new User(2, 2000000000));
        Thread thread1 = new Thread((
                () -> {
                    for (int i = 0; i < 2000000; i++) {
                        userStorage.transfer(1, 2, 1);
                    }
                }));
        Thread thread2 = new Thread((
                () -> {
                    for (int i = 0; i < 2000000; i++) {
                        userStorage.transfer(2, 1, 1);
                    }
                }));
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        assertThat(userStorage.getUserById(1).getAmount(), is(2000000000));
        assertThat(userStorage.getUserById(2).getAmount(), is(2000000000));
    }

    private class ThreadDeleteUser extends Thread {
        private final User user;
        private final Thread addThread;
        private final UserStorage userStorage;


        private ThreadDeleteUser(final User user, UserStorage userStorage, Thread addThread) {
            this.user = user;
            this.addThread = addThread;
            this.userStorage = userStorage;
        }

        @Override
        public void run() {
            System.out.println(currentThread().getName() + " is active");
            while (addThread.isAlive()) {
                if (userStorage.delete(user)) {
                    counter.decrementAndGet();
                }
            }
            System.out.println(currentThread().getName() + " is out");
        }
    }

    @Test
    public void testMultiThreadThenAddAndDelete() throws InterruptedException {
        final UserStorage userStorage = new UserStorage();
        final AtomicBoolean flagDeleteThreadsStarted = new AtomicBoolean(false);
        User user = new User(1, 100);
        Thread thread1Add = new Thread((
                () -> {
                    System.out.println("Add thread started");
                    System.out.println(Thread.currentThread().getState());
                    while (!flagDeleteThreadsStarted.get()) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("Add thread run");
                    System.out.println(Thread.currentThread().getState());
                    for (int i = 0; i < 100000000; i++) {
                        if (userStorage.add(user)) {
                            counter.incrementAndGet();
                        }
                    }
                    System.out.println("Thread Add has been finished");
                }));
        thread1Add.start();
        //thread1Add.join();
        while (thread1Add.getState() != Thread.State.RUNNABLE) {
            Thread.sleep(1000);
        }
        int numOfThreads = 15;
        Thread[] array = new Thread[numOfThreads];
        for (int i = 0; i < array.length; i++) {
            array[i] = new ThreadDeleteUser(user, userStorage, thread1Add);
            array[i].start();
            //array[i].join();
        }

        int threadCount = 0;
        while (threadCount != numOfThreads) {
            threadCount = 0;
            for (Thread thread : array) {
                if (thread.getState() == Thread.State.RUNNABLE) {
                    threadCount++;
                } else {
                    Thread.sleep(1000);
                }
            }
        }
        flagDeleteThreadsStarted.set(true);
        while (thread1Add.getState() != Thread.State.TERMINATED) {
            Thread.sleep(1000);
        }

        threadCount = 0;
        while (threadCount != numOfThreads) {
            threadCount = 0;
            for (Thread thread : array) {
                if (thread.getState() == Thread.State.TERMINATED) {
                    threadCount++;
                } else {
                    Thread.sleep(1000);
                }
            }
        }
        assertThat(counter.get(), is(0));
    }

    @Test
    public void testSingleThreadThenAdd() throws InterruptedException {
        final UserStorage userStorage = new UserStorage();
        User user = new User(1, 100);
        assertThat(userStorage.add(user), is(true));
        assertThat(userStorage.add(user), is(false));
    }

    @Test
    public void testSingleThreadThenDelete() throws InterruptedException {
        final UserStorage userStorage = new UserStorage();
        User user = new User(1, 100);
        assertThat(userStorage.add(user), is(true));
        assertThat(userStorage.delete(user), is(true));
        assertThat(userStorage.delete(user), is(false));
    }

    @Test
    public void testSingleThreadThenUpdate() throws InterruptedException {
        final UserStorage userStorage = new UserStorage();
        User user1 = new User(1, 100);
        User user2 = new User(1, 200);
        assertThat(userStorage.add(user1), is(true));
        assertThat(userStorage.update(user2), is(true));
        assertThat(userStorage.add(user2), is(false));
        assertThat(userStorage.getUserById(user2.getId()).getAmount(), is(user2.getAmount()));
    }
}