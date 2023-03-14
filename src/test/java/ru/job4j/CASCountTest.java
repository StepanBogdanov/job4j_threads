package ru.job4j;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class CASCountTest {

    @Test
    public void whenIncrementThenGet() {
        CASCount count = new CASCount();
        count.increment();
        assertThat(count.get()).isEqualTo(1);
    }

    @Test
    public void whenIncrementTwiceThanGet() {
        CASCount count = new CASCount();
        count.increment();
        count.increment();
        assertThat(count.get()).isEqualTo(2);
    }

}