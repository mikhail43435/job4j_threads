package ru.job4j.threads.resourcesync;

import java.io.*;

public class SaveFile {

    private final File file;

    public SaveFile(File f) {
        this.file = f;
    }

    public void saveContent(String content) throws IOException {
        try (var out = new BufferedWriter(new FileWriter(file.getAbsolutePath()))) {
            for (int i = 0; i < content.length(); i += 1) {
                out.write(content.charAt(i));
            }
        }
    }
}
