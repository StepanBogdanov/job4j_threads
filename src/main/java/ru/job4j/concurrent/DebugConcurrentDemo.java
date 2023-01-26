package ru.job4j.concurrent;

public class DebugConcurrentDemo {
    public static void main(String[] args) {
        String name = "Первый поток";
        String name1 = "Второй поток";
        Thread t1 = new Thread(() -> {
            try {
                for (int i = 3; i > 0; i--) {
                    System.out.println(Thread.currentThread().getName() + " : " + i);
                    Thread.sleep(10);
                }
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName() + " прерван ");
            }
            System.out.println(Thread.currentThread().getName() + " завершен.");
        }, name);
        Thread t2 = new Thread(() -> {
            try {
                for (int i = 10; i < 13; i++) {
                    System.out.println(Thread.currentThread().getName() + " : " + i);
                    Thread.sleep(10);
                }
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName() + " прерван ");
            }
            System.out.println(Thread.currentThread().getName() + " завершен.");
        }, name1);
        t1.start();
        t2.start();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            System.out.println("Главный поток прерван ");
        }
        System.out.println("Главный поток завершен.");
    }
}