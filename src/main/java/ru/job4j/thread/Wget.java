package ru.job4j.thread;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;

public class Wget implements Runnable {
    private final String url;
    private final int speed;
    private final String path;

    public Wget(String url, int speed, String path) {
        this.url = url;
        this.speed = speed;
        this.path = path;
    }

    @Override
    public void run() {
        String fileName = url.substring(url.lastIndexOf('/') + 1);
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(path + fileName)) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            int downloadData = 0;
            long start = System.currentTimeMillis();
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                downloadData += bytesRead;
                fileOutputStream.write(dataBuffer, 0, bytesRead);
                if (downloadData >= speed) {
                    long executeTime = System.currentTimeMillis() - start;
                    if (executeTime < 1000) {
                        Thread.sleep(1000 - executeTime);
                    }
                    downloadData = 0;
                    start = System.currentTimeMillis();
                }
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
        String path = args[2];
        Thread wget = new Thread(new Wget(url, speed, path));
        wget.start();
        wget.join();
    }

    private static void validate(String[] args) {
        if (args.length < 3) {
            throw new IllegalArgumentException("Введите параметры: URL, максимальная скорость загрузки (Б/с),"
                    + "путь для загрузки.");
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
            throw new IllegalArgumentException("Введите максимальную скорость загрузки (Б/с)");
        }
        if (!new File(args[2]).isDirectory()) {
            throw new IllegalArgumentException("Введите корректный путь для зазрузки файла");
        }
    }
}