package ru.job4j.io;

import java.io.*;

public final class SaveToFile {
    private final File file;

    public SaveToFile(File file) {
        this.file = file;
    }

    public void saveContent(String content) throws IOException {
        try (BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file))) {
            out.write(content.getBytes());
        }
    }
}
