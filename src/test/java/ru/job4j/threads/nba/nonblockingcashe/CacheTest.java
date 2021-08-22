package ru.job4j.threads.nba.nonblockingcashe;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class CacheTest {

    @Test
    public void thenAdd() {
        Cache cache = new Cache();
        Base base = new Base(1, 0);
        cache.add(base);
        cache.add(base);
        assertThat(cache.getCacheList().size(), is(1));
        assertThat(cache.getCacheList().get(0), is(base));
    }

    @Test
    public void thenDelete() {
        Cache cache = new Cache();
        Base base = new Base(1, 0);
        cache.add(base);
        assertThat(cache.getCacheList().size(), is(1));
        cache.delete(base);
        assertThat(cache.getCacheList().size(), is(0));
    }

    @Test
    public void thenGet() {
        Cache cache = new Cache();
        Base base = new Base(1, 0);
        base.setName("name");
        cache.add(base);
        assertThat(cache.get(base.getId()).getName(), is("name"));
    }

    @Test
    public void thenUpdate() {
        Cache cache = new Cache();
        Base base = new Base(1, 0);
        base.setName("name 1");
        cache.add(base);

        Base newBase = new Base(1, 0);
        newBase.setName("name 2");
        cache.update(newBase);

        assertThat(cache.getCacheList().size(), is(1));
        assertThat(cache.get(newBase.getId()).getName(), is("name 2"));
    }

    @Test(expected = OptimisticException.class)
    public void thenUpdateVersionConflict() {
        Cache cache = new Cache();
        Base base = new Base(1, 0);
        base.setName("name 1");
        cache.add(base);
        Base newBase1 = new Base(1, 0);
        newBase1.setName("name 2.1");
        Base newBase2 = new Base(1, 0);
        newBase2.setName("name 2.2");
        cache.update(newBase1);
        cache.update(newBase2);
        assertThat(cache.getCacheList().size(), is(1));
        assertThat(cache.get(newBase1.getId()).getName(), is("name 2.1"));
    }
}