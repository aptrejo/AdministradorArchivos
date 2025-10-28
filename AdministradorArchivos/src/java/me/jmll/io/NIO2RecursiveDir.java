package me.jmll.io;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

public class NIO2RecursiveDir {
    public List<Path> walkdir(Path rootPath) throws IOException {
        List<Path> files = new ArrayList<>();
        if (rootPath == null || !Files.exists(rootPath)) return files;

        Files.walkFileTree(rootPath, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                files.add(file);
                return FileVisitResult.CONTINUE;
            }
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
                files.add(dir);
                return FileVisitResult.CONTINUE;
            }
        });
        return files;
    }
}
