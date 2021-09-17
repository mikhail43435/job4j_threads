package ru.job4j.threads.completablefuture.rolcolsum;

import java.util.Arrays;
import java.util.Objects;

/**
 * Дан каркас класса.
 * Ваша задача подсчитать суммы по строкам и столбцам матрицы.
 * - sums[i].rowSum - сумма элементов по i строке
 * - sums[i].colSum  - сумма элементов по i столбцу
 */

public class RolColSum {

    public static class Sums {
        private int rowSum;
        private int colSum;

        public Sums(int rowSum, int colSum) {
            this.rowSum = rowSum;
            this.colSum = colSum;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Sums sums = (Sums) o;
            return rowSum == sums.rowSum &&
                    colSum == sums.colSum;
        }

        @Override
        public int hashCode() {
            return Objects.hash(rowSum, colSum);
        }

        @Override
        public String toString() {
            return "Sums{" +
                    "rowSum=" + rowSum +
                    ", colSum=" + colSum +
                    '}';
        }

        public Sums() {
            this.rowSum = 0;
            this.colSum = 0;
        }

        public int getRowSum() {
            return rowSum;
        }

        public void setRowSum(int rowSum) {
            this.rowSum = rowSum;
        }

        public int getColSum() {
            return colSum;
        }

        public void setColSum(int colSum) {
            this.colSum = colSum;
        }
    }

    public static Sums[] sum(int[][] matrix) {
        int tempSum = 0;
        Sums[] sums = new Sums[Math.max(matrix.length, matrix[0].length)];
        for (int i = 0; i < sums.length; i++) {
            sums[i] = new Sums();
        }

        // идем по строкам
        for (int i = 0; i < matrix.length; i++) {
            for (int k = 0; k < matrix[0].length; k++) {
                tempSum += matrix[i][k];
            }
            sums[i].rowSum = tempSum;
            tempSum = 0;
        }
        // идем по колонкам
        for (int i = 0; i < matrix[0].length; i++) {
            for (int k = 0; k < matrix.length; k++) {
                tempSum += matrix[k][i];
            }
            sums[i].colSum = tempSum;
            tempSum = 0;
        }
        System.out.println(Arrays.deepToString(sums));
        return sums;
    }

    public static Sums[] asyncSum(int[][] matrix) {

        return null;
    }

}