package ru.job4j.concurrent;

public class ThreadState {
    public static void main(String[] args) {
        Thread first = new Thread(
                () -> {
                    System.out.printf("%s is working%n", Thread.currentThread().getName());
                }
        );
        Thread second = new Thread(
                () -> {
                    System.out.printf("%s is working%n", Thread.currentThread().getName());
                }
        );
        System.out.printf("%s: %s%n", first.getName(), first.getState());
        System.out.printf("%s: %s%n", second.getName(), second.getState());
        first.start();
        second.start();
        while (first.getState() != Thread.State.TERMINATED || first.getState() != Thread.State.TERMINATED) {
            System.out.printf("%s: %s%n", first.getName(), first.getState());
            System.out.printf("%s: %s%n", second.getName(), second.getState());
        }
        System.out.printf("%s: %s%n", first.getName(), first.getState());
        System.out.printf("%s: %s%n", second.getName(), second.getState());
    }
}
