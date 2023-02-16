package ru.job4j;

import org.junit.jupiter.api.Test;
import ru.job4j.buffer.SimpleBlockingQueue;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class SimpleBlockingQueueTest {

    @Test
    void whenPollThenOffer() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>();
        List<Integer> values = new ArrayList<>();
        Thread producer = new Thread(() -> {
            queue.offer(123);
        });
        Thread consumer = new Thread(() -> {
            try {
                values.add(queue.poll());
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        });
        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
        assertThat(values.get(0)).isEqualTo(123);
    }
}