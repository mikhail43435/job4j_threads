package ru.job4j.threads.pools.forksearch;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelSearch extends RecursiveTask<Integer> {

    private final Object[] array;
    private final int start;
    private final int end;
    private final Object value;

    public ParallelSearch(Object[] array, int start, int end, Object value) {
        this.array = array;
        this.start = start;
        this.end = end;
        this.value = value;
    }

    @Override
    protected Integer compute() {
        if (start > end
                || start < 0
                || start > array.length - 1
                || end > array.length - 1) {
            throw new IllegalArgumentException("Ошибка в параметрах начала и конца поиска: "
                    + System.lineSeparator()
                    + "парамерт start: " + start
                    + System.lineSeparator()
                    + "парамерт end: " + end);
        }
        if (start - end < 10) {
            for (int i = start; i <= end; i++) {
                if (array[i].equals(value)) {
                    return i;
                }
            }
            return -1;
        }

        if (start >= end) {
            return array[start].equals(value) ? start : -1;
        }
        int mid = (start + end) / 2;
        ParallelSearch leftSearch = new ParallelSearch(array, start, mid, value);
        ParallelSearch rightSearch = new ParallelSearch(array, mid + 1, end, value);
        leftSearch.fork();
        rightSearch.fork();
        Integer leftResult = leftSearch.join();
        Integer rightResult = rightSearch.join();
        return Math.max(leftResult, rightResult);
    }

    public static Integer search(Object[] array, int start, int end, Object value) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(new ParallelSearch(array, start, end, value));
    }
}