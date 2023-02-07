package ru.job4j.synch;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;


public class SingleLockListTest {

    @Test
    public void whenIt() {
        var init = new ArrayList<Integer>();
        SingleLockList<Integer> list = new SingleLockList<>(init);
        list.add(1);
        var it = list.iterator();
        list.add(2);
        assertThat(it.next()).isEqualTo(1);
    }

    @Test
    public void whenAdd() throws InterruptedException {
        var init = new ArrayList<Integer>();
        SingleLockList<Integer> list = new SingleLockList<>(init);
        Thread first = new Thread(() -> list.add(1));
        Thread second = new Thread(() -> list.add(2));
        first.start();
        second.start();
        first.join();
        second.join();
        Set<Integer> rsl = new TreeSet<>();
        list.iterator().forEachRemaining(rsl::add);
        assertThat(rsl).containsAll(Set.of(1, 2));
    }
}