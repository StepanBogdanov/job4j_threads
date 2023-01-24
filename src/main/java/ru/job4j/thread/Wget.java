package ru.job4j.thread;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class Wget implements Runnable {
    private final String url;
    private final int speed;

    public Wget(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream("readme.txt")) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            long currentTime = System.currentTimeMillis();
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
                long executeTime = System.currentTimeMillis() - currentTime;
                if (executeTime < speed) {
                    Thread.sleep(speed - executeTime);
                }
                currentTime = System.currentTimeMillis();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        validate(args);
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        Thread wget = new Thread(new Wget(url, speed));
        wget.start();
        wget.join();
    }

    private static void validate(String[] args) {
        if (args.length < 2) {
            throw new IllegalArgumentException("Введите параметры: URL, максимальная скорость загрузки (кБ/с)");
        }
        try {
            new URL(args[0]);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("Неверный адрес URL");
        }
        try {
            int spped = Integer.parseInt(args[1]);
            if (spped <= 0) {
                throw new IllegalArgumentException("Скорость загрузки должна быть больше 0");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Введите максимальную скорость загрузки (кБ/с)");
        }
    }
}