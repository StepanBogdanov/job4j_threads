package ru.job4j.io;

import java.io.*;
import java.util.function.Predicate;

public final class ParseFile {
    private final File file;

    public ParseFile(File file) {
        this.file = file;
    }

    public synchronized File getFile() {
        return file;
    }

    private String content(Predicate<Character> filter) throws IOException {
        String output;
        try (BufferedReader in = new BufferedReader(new FileReader(file))) {
            output = in.lines()
                    .flatMap(s -> s.chars().boxed())
                    .map(i -> Character.forDigit(i, 10))
                    .filter(filter)
                    .toString();
        }
        return output;
    }

    public String getContent() throws IOException {
        return content(c -> true);
    }

    public String getContentWithoutUnicode() throws IOException {
        return content(c -> (int) c < 0x80);
    }
}