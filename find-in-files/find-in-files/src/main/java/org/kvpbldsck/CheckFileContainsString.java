package org.kvpbldsck;

import java.io.UncheckedIOException;
import java.nio.charset.MalformedInputException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.concurrent.Callable;

public class CheckFileContainsString implements Callable<Optional<Path>> {

    private final Path filePath;
    private final String searchingString;

    public CheckFileContainsString(Path filePath, String searchingString) {
        this.filePath = filePath;
        this.searchingString = searchingString;
    }

    @Override
    public Optional<Path> call() throws Exception {
        boolean isContains;

        try {
            var linesStream = Files
                    .lines(filePath, StandardCharsets.UTF_8);

            isContains = linesStream
                    .anyMatch(l -> l.contains(searchingString));

            linesStream.close();
        } catch (MalformedInputException | UncheckedIOException e) {
            System.out.printf("File %s doesn't have utf-8 charset, so it will be not included in search", filePath.toString());
            return Optional.empty();
        }

        if (isContains) {
            System.out.printf("File %s contains %s%n", filePath, searchingString);
            return Optional.of(filePath);
        } else {
            System.out.printf("File %s doesn't contain %s%n", filePath, searchingString);
            return Optional.empty();
        }
    }
}
