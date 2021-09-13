package ru.job4j.threads.pools.forksearch;

import junit.framework.TestCase;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ParallelSearchTest {

    @Test
    public void test01ThenDigitsAndFound() {
        Integer[] array = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
        ParallelSearch ps = new ParallelSearch(array, 0, array.length - 1, 6);
        assertThat(ps.compute(), is(5));
    }

    @Test
    public void test02ThenDigitsNotFound() {
        Integer[] array = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
        ParallelSearch ps = new ParallelSearch(array, 0, array.length - 1, 45);
        assertThat(ps.compute(), is(-1));
    }

    @Test
    public void test03henStringsAndFound() {
        String[] array = new String[]{"a", "b", "e", "d", "rw", "fds", "sd", "fs", "cx", "www", "qwe", "f"};
        ParallelSearch ps = new ParallelSearch(array, 0, array.length - 1, "e");
        assertThat(ps.compute(), is(2));
    }

    @Test
    public void test04ThenStringsAndNotFound() {
        String[] array = new String[]{"a", "b", "e", "d", "rw", "fds", "sd", "fs", "cx", "www", "qwe", "f"};
        ParallelSearch ps = new ParallelSearch(array, 0, array.length - 1, "eE");
        assertThat(ps.compute(), is(-1));
    }


    @Test
    public void test05henStringsLessThan10AndFound() {
        String[] array = new String[]{"a", "b", "e"};
        ParallelSearch ps = new ParallelSearch(array, 0, array.length - 1, "a");
        assertThat(ps.compute(), is(0));
    }

    @Test
    public void test06ThenStringsLessThan10AndNotFound() {
        String[] array = new String[]{"a", "b", "e"};
        ParallelSearch ps = new ParallelSearch(array, 0, array.length - 1, "eE");
        assertThat(ps.compute(), is(-1));
    }

    @Test (expected = IllegalArgumentException.class)
    public void test07ThenEmptyArray() {
        String[] array = new String[]{};
        ParallelSearch ps = new ParallelSearch(array, 0, array.length - 1, "eE");
        assertThat(ps.compute(), is(-1));
    }

    @Test (expected = IllegalArgumentException.class)
    public void test08ThenWrongArguments() {
        Integer[] array = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
        ParallelSearch ps = new ParallelSearch(array, -1, array.length - 1, 6);
        assertThat(ps.compute(), is(5));
    }

    @Test (expected = IllegalArgumentException.class)
    public void test09ThenWrongArguments() {
        Integer[] array = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
        ParallelSearch ps = new ParallelSearch(array, 3, array.length, 6);
        assertThat(ps.compute(), is(5));
    }

    @Test (expected = IllegalArgumentException.class)
    public void test10ThenWrongArguments() {
        Integer[] array = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
        ParallelSearch ps = new ParallelSearch(array, 4, array.length, 3);
        assertThat(ps.compute(), is(5));
    }

    @Test
    public void test011ThenDigitsFoundInRange() {
        Integer[] array = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
        ParallelSearch ps = new ParallelSearch(array, 3, 6, 6);
        assertThat(ps.compute(), is(5));
    }

    @Test
    public void test012ThenDigitsNotFoundInRange() {
        Integer[] array = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
        ParallelSearch ps = new ParallelSearch(array, 3, 6, 45);
        assertThat(ps.compute(), is(-1));
    }

}