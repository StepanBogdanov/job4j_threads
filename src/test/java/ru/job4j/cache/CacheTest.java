package ru.job4j.cache;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class CacheTest {

    @Test
    public void whenAddIfKeyIsAbsent() {
        Cache cache = new Cache();
        Base model = new Base(1, 0);
        model.setName("name1");
        cache.add(model);
        assertThat(cache.get(1).getName()).isEqualTo("name1");
    }

    @Test
    public void whenAddIfKeyIsPresent() {
        Cache cache = new Cache();
        Base model1 = new Base(1, 0);
        Base model2 = new Base(1, 0);
        model1.setName("name1");
        model2.setName("name2");
        cache.add(model1);
        cache.add(model2);
        assertThat(cache.get(1).getName()).isEqualTo("name1");
    }

    @Test
    public void whenUpdateIfVersionsEquals() {
        Cache cache = new Cache();
        Base model1 = new Base(1, 0);
        Base model2 = new Base(1, 0);
        model1.setName("name1");
        model2.setName("name2");
        cache.add(model1);
        cache.update(model2);
        assertThat(cache.get(1).getName()).isEqualTo("name2");
    }

    @Test
    public void whenUpdateIfVersionsNotEquals() {
        Cache cache = new Cache();
        Base model1 = new Base(1, 0);
        Base model2 = new Base(1, 1);
        model1.setName("name1");
        model2.setName("name2");
        cache.add(model1);
        assertThatThrownBy(() -> cache.update(model2)).isInstanceOf(OptimisticException.class);
    }
}