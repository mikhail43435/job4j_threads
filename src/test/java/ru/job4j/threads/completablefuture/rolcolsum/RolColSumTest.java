package ru.job4j.threads.completablefuture.rolcolsum;

import junit.framework.TestCase;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class RolColSumTest {

    @Test
    public void test01() {
        int[][] matrix = new int[][]{{1, 2, 3}, {5, 2, 3}, {1, 2, 3}, {3, 4, 5}};
        RolColSum.Sums[] expected  = new RolColSum.Sums[4];
        expected [0] = new RolColSum.Sums(6, 10);
        expected [1] = new RolColSum.Sums(10, 10);
        expected [2] = new RolColSum.Sums(6, 14);
        expected [3] = new RolColSum.Sums(12, 0);
        System.out.println(Arrays.deepToString(expected ));
        assertArrayEquals(RolColSum.sum(matrix), expected );
    }


    @Test
    public void test02() {
        int[][] matrix = new int[][]{{1, 2}, {3, 4}};
        RolColSum.Sums[] expected  = new RolColSum.Sums[2];
        expected [0] = new RolColSum.Sums(3, 4);
        expected [1] = new RolColSum.Sums(7, 6);
        System.out.println(Arrays.deepToString(expected));
        assertArrayEquals(RolColSum.sum(matrix), expected);
    }


    @Test
    public void test03() {
        int[][] matrix = new int[2][2];
        RolColSum.Sums[] expected  = new RolColSum.Sums[2];
        expected [0] = new RolColSum.Sums(0, 0);
        expected [1] = new RolColSum.Sums(0, 0);
        System.out.println(Arrays.deepToString(expected));
        assertArrayEquals(RolColSum.sum(matrix), expected);
    }

}