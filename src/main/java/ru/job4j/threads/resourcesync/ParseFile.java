package ru.job4j.threads.resourcesync;

import java.io.*;
import java.util.function.IntPredicate;

public class ParseFile {
    private final File file;

    public ParseFile(File f) {
        this.file = f;
    }

    public synchronized File getFile() {
        return file;
    }

    public String getContent(IntPredicate filter) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (var reader = new BufferedReader(new FileReader(file.getAbsolutePath()))) {
            int data;
            while ((data = reader.read()) > 0) {
                if (filter.test(data)) {
                    sb.append((char) data);
                }
            }
        }
        return sb.toString();
    }
}