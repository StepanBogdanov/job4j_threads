package ru.job4j.pool;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelSearch<V> extends RecursiveTask<Integer> {

    private final Object[] array;
    private final V value;
    private int from;
    private int to;

    public ParallelSearch(Object[] array, V value, int from, int to) {
        this.array = array;
        this.value = value;
        this.from = from;
        this.to = to;
    }

    @Override
    protected Integer compute() {
        if (to - from < 10) {
            return linearSearch(array, value);
        }
        int mid = (from + to) / 2;
        ParallelSearch<V> leftSearch = new ParallelSearch(array, value, from, mid);
        ParallelSearch<V> rightSearch = new ParallelSearch(array, value, mid + 1, to);
        leftSearch.fork();
        rightSearch.fork();
        int left = leftSearch.join();
        int right = rightSearch.join();
        if (left != -1) {
            return left;
        } else if (right != -1) {
            return right;
        } else {
            return -1;
        }
    }

    public static <V> Integer find(Object[] array, V value) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(new ParallelSearch<V>(array, value, 0, array.length - 1));
    }

    private int linearSearch(Object[] array, V value) {
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(value)) {
                return i;
            }
        }
        return -1;
    }
}
