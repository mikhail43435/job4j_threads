package ru.job4j.threads.pools.forksort;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ParallelMergeSortTest {

    @Test
    public void testMergeSort() {
        assertThat(ParallelMergeSort.sort(new int[] {5, 8, 7, 1, 3, 10, 2, 4, 6, 9}),
                is(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10 }));
    }
}