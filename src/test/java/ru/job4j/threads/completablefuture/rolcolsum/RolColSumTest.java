package ru.job4j.threads.completablefuture.rolcolsum;

import org.junit.Test;
import static org.junit.Assert.assertArrayEquals;

public class RolColSumTest {

    @Test
    public void testThenOneThread01() {
        int[][] matrix = new int[][]{{1, 2, 3}, {5, 2, 3}, {1, 2, 3}, {3, 4, 5}};
        RolColSum.Sums[] expected  = new RolColSum.Sums[4];
        expected [0] = new RolColSum.Sums(6, 10);
        expected [1] = new RolColSum.Sums(10, 10);
        expected [2] = new RolColSum.Sums(6, 14);
        expected [3] = new RolColSum.Sums(12, 0);
        assertArrayEquals(expected, RolColSum.sum(matrix) );
    }

    @Test
    public void testThenOneThread02() {
        int[][] matrix = new int[][]{{1, 2}, {3, 4}};
        RolColSum.Sums[] expected  = new RolColSum.Sums[2];
        expected [0] = new RolColSum.Sums(3, 4);
        expected [1] = new RolColSum.Sums(7, 6);
        assertArrayEquals(expected, RolColSum.sum(matrix));
    }

    @Test
    public void testThenOneThread03() {
        int[][] matrix = new int[2][2];
        RolColSum.Sums[] expected  = new RolColSum.Sums[2];
        expected [0] = new RolColSum.Sums(0, 0);
        expected [1] = new RolColSum.Sums(0, 0);
        assertArrayEquals(expected, RolColSum.sum(matrix));
    }

    //  1  2  = 3
    //  3  4  = 7
    //  1  1  = 2
    //  -  -
    //  5  7
    @Test
    public void testThenOneThread04() {
        int[][] matrix = new int[][]{{1, 2}, {3, 4}, {1, 1}};
        RolColSum.Sums[] expected  = new RolColSum.Sums[3];
        expected [0] = new RolColSum.Sums(3, 5);
        expected [1] = new RolColSum.Sums(7, 7);
        expected [2] = new RolColSum.Sums(2, 0);
        assertArrayEquals(expected, RolColSum.sum(matrix));
    }

    @Test
    public void testThenCompletableFutured01() {
        int[][] matrix = new int[][]{{1, 2, 3}, {5, 2, 3}, {1, 2, 3}, {3, 4, 5}};
        RolColSum.Sums[] expected  = new RolColSum.Sums[4];
        expected [0] = new RolColSum.Sums(6, 10);
        expected [1] = new RolColSum.Sums(10, 10);
        expected [2] = new RolColSum.Sums(6, 14);
        expected [3] = new RolColSum.Sums(12, 0);
        assertArrayEquals(expected, RolColSum.asyncSum(matrix));
    }

    @Test
    public void testThenCompletableFuture02() {
        int[][] matrix = new int[][]{{1, 2}, {3, 4}};
        RolColSum.Sums[] expected  = new RolColSum.Sums[2];
        expected [0] = new RolColSum.Sums(3, 4);
        expected [1] = new RolColSum.Sums(7, 6);
        assertArrayEquals(expected, RolColSum.asyncSum(matrix));
    }

    @Test
    public void testThenCompletableFuture03() {
        int[][] matrix = new int[2][2];
        RolColSum.Sums[] expected  = new RolColSum.Sums[2];
        expected [0] = new RolColSum.Sums(0, 0);
        expected [1] = new RolColSum.Sums(0, 0);
        assertArrayEquals(expected, RolColSum.asyncSum(matrix));
    }

    @Test
    public void testThenCompletableFuture04() {
        int[][] matrix = new int[][]{{1, 2, 3}, {5, 2, 3}, {1, 2, 3}, {3, 4, 5}};
        RolColSum.Sums[] expected  = new RolColSum.Sums[4];
        expected [0] = new RolColSum.Sums(6, 10);
        expected [1] = new RolColSum.Sums(10, 10);
        expected [2] = new RolColSum.Sums(6, 14);
        expected [3] = new RolColSum.Sums(12, 0);
        assertArrayEquals(expected, RolColSum.asyncSum(matrix));
    }
}