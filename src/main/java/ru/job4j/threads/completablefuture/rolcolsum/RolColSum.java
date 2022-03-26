package ru.job4j.threads.completablefuture.rolcolsum;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

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
     *
     * @param matrix - матрица
     * @return - массив объектов класса RolColSum
     */
    public static Sums[] sum(int[][] matrix) {
        if (!checkIntMatrixForSquareness(matrix)) {
            throw new IllegalArgumentException("The matrix array is not square");
        }
        Sums[] sums = Stream.generate(Sums::new).limit(matrix.length).toArray(Sums[]::new);
        for (int i = 0; i < matrix.length; i++) {
            for (int k = 0; k < matrix[0].length; k++) {
                sums[i].rowSum += matrix[i][k];
                sums[i].colSum += matrix[k][i];
            }
        }
        return sums;
    }

    /**
     * Checking the int matrix array for squareness
     * @param matrix - array for ckeck
     * @return - true - if matrix array is square, false - if matrix array is not square
     */
    public static boolean checkIntMatrixForSquareness(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            if (matrix[i].length != matrix.length) {
                return false;
            }
        }
        return true;
    }

    /**
     * Считает суммы по строкам и столбцам матрицы
     * в двух одтельных асинхронных потоках CompletableFuture
     * по колонкам и срокам матрицы соответственно
     * - sums[i].rowSum - сумма элементов по i строке
     * - sums[i].colSum  - сумма элементов по i столбцу
     *
     * @param matrix - матрица
     * @return - массив объектов класса RolColSum
     */
    public static Sums[] asyncSum(int[][] matrix) {
        if (!checkIntMatrixForSquareness(matrix)) {
            throw new IllegalArgumentException("The matrix array is not square");
        }
        Sums[] sums = Stream.generate(Sums::new).limit(matrix.length).toArray(Sums[]::new);
        CompletableFuture<Void>[] CFPoolArray = new CompletableFuture[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            CFPoolArray[i] = countSumOfCertainRowAndColumn(matrix, sums, i);
        }
        CompletableFuture<Void> allOfCF = CompletableFuture.allOf(CFPoolArray);
        try {
            allOfCF.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
        return sums;
    }

    /**
     * Метод возвращает экземплят класса CompletableFuture<Void>
     * который выполняет подсчет сумм по колонкам переданной матрицы
     * и заносит их в переданный объект sums
     *
     * @param matrix - матрица
     * @param sums   - массив объектов класса RolColSum
     * @return - CompletableFuture<Void>
     */
    public static CompletableFuture<Void> countSumOfCertainRowAndColumn(int[][] matrix, Sums[] sums, int index) {
        return CompletableFuture.runAsync(
                () -> {
                    for (int k = 0; k < matrix[0].length; k++) {
                        sums[index].rowSum += matrix[index][k];
                        sums[index].colSum += matrix[k][index];
                    }
                }
        );
    }
}