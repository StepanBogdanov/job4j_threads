package ru.job4j.pool;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.*;
import static ru.job4j.pool.RolColSum.asyncSum;
import static ru.job4j.pool.RolColSum.sum;

class RolColSumTest {

    @Test
    public void whenSeqSum() {
        int[][] matrix = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        Sums[] sums = sum(matrix);
        assertThat(sums).isEqualTo(new Sums[] {
                new Sums(6, 12),
                new Sums(15, 15),
                new Sums(24, 18)
        });
    }

    @Test
    public void whenAsyncSum() throws ExecutionException, InterruptedException {
        int[][] matrix = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        Sums[] sums = asyncSum(matrix);
        assertThat(sums).isEqualTo(new Sums[] {
                new Sums(6, 12),
                new Sums(15, 15),
                new Sums(24, 18)
        });
    }

}