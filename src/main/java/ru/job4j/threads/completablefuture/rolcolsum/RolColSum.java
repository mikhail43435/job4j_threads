package ru.job4j.threads.completablefuture.rolcolsum;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;


/**
 * Задача:
 * Дан каркас класса.
 * Ваша задача подсчитать суммы по строкам и столбцам матрицы.
 * - sums[i].rowSum - сумма элементов по i строке
 * - sums[i].colSum  - сумма элементов по i столбцу
 * Реализовать последовательную версию программы
 * Реализовать асинхронную версию программы. i - я задача считает сумму по i столбцу и i строке
 * Написать тесты
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
    }

    /**
     * Считает суммы по строкам и столбцам матрицы.
     * - sums[i].rowSum - сумма элементов по i строке
     * - sums[i].colSum  - сумма элементов по i столбцу
     * @param matrix    - матрица
     * @return          - массив объектов класса RolColSum
     */
    public static Sums[] sum(int[][] matrix) {
        Sums[] sums = new Sums[Math.max(matrix.length, matrix[0].length)];
        for (int i = 0; i < sums.length; i++) {
            sums[i] = new Sums();
        }
        // идем по строкам
        for (int i = 0; i < matrix.length; i++) {
            for (int k = 0; k < matrix[0].length; k++) {
                sums[i].rowSum += matrix[i][k];
            }
        }
        // идем по колонкам
        for (int i = 0; i < matrix[0].length; i++) {
            for (int k = 0; k < matrix.length; k++) {
                sums[i].colSum += matrix[k][i];
            }
        }
        return sums;
    }

    /**
     * Считает суммы по строкам и столбцам матрицы
     * в двух одтельных асинхронных потоках CompletableFuture
     * по колонкам и срокам матрицы соответственно
     * - sums[i].rowSum - сумма элементов по i строке
     * - sums[i].colSum  - сумма элементов по i столбцу
     * @param matrix    - матрица
     * @return          - массив объектов класса RolColSum
     */
    public static Sums[] asyncSum(int[][] matrix) {
        Sums[] sums = new Sums[Math.max(matrix.length, matrix[0].length)];
        for (int i = 0; i < sums.length; i++) {
            sums[i] = new Sums();
        }
        CompletableFuture<Void> all = CompletableFuture.allOf(
                countRowSum(matrix, sums), countColumnSum(matrix, sums)
        );
        while (!all.isDone()) {
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return sums;
    }

    /**
     * Метод возвращает экземплят класса CompletableFuture<Void>
     * который выполняет подсчет сумм по строкам переданной матрицы
     * и заносит их в переданный объект sums
     * @param matrix    - матрица
     * @param sums      - массив объектов класса RolColSum
     * @return          - CompletableFuture<Void>
     */
    public static CompletableFuture<Void> countRowSum(int[][] matrix, Sums[] sums) {
        return CompletableFuture.runAsync(
                () -> {
                    for (int i = 0; i < matrix.length; i++) {
                        for (int k = 0; k < matrix[0].length; k++) {
                            sums[i].rowSum += matrix[i][k];
                        }
                    }
                }
        );
    }

    /**
     * Метод возвращает экземплят класса CompletableFuture<Void>
     * который выполняет подсчет сумм по колонкам переданной матрицы
     * и заносит их в переданный объект sums
     * @param matrix    - матрица
     * @param sums      - массив объектов класса RolColSum
     * @return          - CompletableFuture<Void>
     */
        public static CompletableFuture<Void> countColumnSum(int[][] matrix, Sums[] sums) {
        return CompletableFuture.runAsync(
                () -> {
                    for (int i = 0; i < matrix[0].length; i++) {
                        for (int k = 0; k < matrix.length; k++) {
                            sums[i].colSum += matrix[k][i];
                        }
                    }
                }
        );
    }
}