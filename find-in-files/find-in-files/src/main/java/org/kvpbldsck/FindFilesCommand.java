package org.kvpbldsck;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class FindFilesCommand {

    private final ExecutorService executorService;
    private final File searchingDirectory;
    private final String searchingString;

    public FindFilesCommand(int threadsCount, File searchingDirectory, String searchingString) {
        this.executorService = Executors.newFixedThreadPool(threadsCount);
        this.searchingDirectory = searchingDirectory;
        this.searchingString = searchingString;
    }

    public List<Path> findFiles() throws IOException {
        var filesSearchTasks = startCheckingEveryFileInDirectory();
        var foundedFiles = collectResult(filesSearchTasks);

        shutdownAndAwaitTermination(executorService);

        return foundedFiles;
    }

    private List<Path> collectResult(List<Future<Optional<Path>>> filesSearchTasks) {
        return filesSearchTasks
                .stream()
                .map(this::unpackTask)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    private List<Future<Optional<Path>>> startCheckingEveryFileInDirectory() throws IOException {
        return Files
                .walk(searchingDirectory.toPath(), Integer.MAX_VALUE)
                .filter(path -> Files.isRegularFile(path) && Files.isReadable(path))
                .map(p -> executorService.submit(new CheckFileContainsString(p, searchingString)))
                .collect(Collectors.toList());
    }

    private void shutdownAndAwaitTermination(ExecutorService pool) {
        pool.shutdown();
        assert executorService.isShutdown() && !executorService.isTerminated();

        try {
            if (!pool.awaitTermination(10, TimeUnit.MINUTES)) {
                pool.shutdownNow();

                if (!pool.awaitTermination(60, TimeUnit.SECONDS))
                    System.err.println("Pool did not terminate");
            }
        } catch (InterruptedException ie) {
            pool.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    private Optional<Path> unpackTask(Future<Optional<Path>> task) {
        try {
            System.out.println("unpack");
            return task.get();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }

        return Optional.empty();
    }

}
