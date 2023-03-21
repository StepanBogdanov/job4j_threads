package ru.job4j.pool;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class ParallelSearchTest {

    @Test
    public void whenDifferentDataTypes() {
        Object[] array = {1, 2, "one", 4.45, 6.789, "two", 3, 4, "three", 3.14, "four", "five", true, 5};
        int ind = ParallelSearch.find(array, "five");
        assertThat(ind).isEqualTo(11);
    }

    @Test
    public void whenSmallArray() {
        Object[] array = {0, 1, 2, 3, 4, 5};
        int ind = ParallelSearch.find(array, 3);
        assertThat(ind).isEqualTo(3);
    }

    @Test
    public void whenLargeArray() {
        Object[] array = new Object[100];
        for (int i = 0; i < 100; i++) {
            array[i] = i;
        }
        int ind = ParallelSearch.find(array, 50);
        assertThat(ind).isEqualTo(50);
    }

    @Test
    public void whenValueIsAbsent() {
        Object[] array = {0, 1, 2, 3, 4, 5};
        int ind = ParallelSearch.find(array, 9);
        assertThat(ind).isEqualTo(-1);
    }
}