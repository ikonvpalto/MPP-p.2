package org.kvpbldsck;

import picocli.CommandLine;
import picocli.CommandLine.Model.CommandSpec;
import picocli.CommandLine.Option;
import picocli.CommandLine.ParameterException;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.Spec;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.Callable;

public class FindFilesCommandLineCommand implements Callable<Integer> {

    private File searchingDirectory;

    @Option(names = { "-t", "--threads" }, description = "Count of thread witch will be used for find files. By default set to cpu cores amount.")
    @Min(value = 1, message = "Threads count should not be less then 1")
    @Max(value = 24, message = "Threads count should not be greater then 24")
    private int threadsCount = Runtime.getRuntime().availableProcessors();

    @Parameters(index = "1", description = "Searching string")
    @NotEmpty(message = "Searching string should not be empty")
    private String searchingString;

    @Parameters(index = "0", description = "Directory where to search")
    @NotEmpty(message = "Searching directory should not be empty")
    public void setSearchingDirectory(File value) {
        if (!value.exists()) {
            throw new ParameterException(spec.commandLine(), "Searching directory should exists");
        }
        if (!value.isDirectory()) {
            throw new ParameterException(spec.commandLine(), "Searching directory should be valid directory");
        }
        searchingDirectory = value;
    }

    @Spec
    CommandSpec spec;

    public Integer call() {
        System.out.printf("Trying to find files in directory %s that contains %s; threads count: %d%n", searchingDirectory.getAbsolutePath(), searchingString, threadsCount);

        List<Path> files;

        try {
            files = new FindFilesCommand(threadsCount, searchingDirectory, searchingString).findFiles();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Can't find files: " + e.getMessage());
            return 1;
        }

        System.out.println("--------------------------");
        System.out.println("Founded files:");
        for (Path file : files) {
            System.out.println(file.toString());
        }

        System.out.printf("%d files have found%n", files.size());

        return 0;
    }

    public static void main(String... args) {
        int exitCode = new CommandLine(new FindFilesCommandLineCommand())
                .execute(args);
        System.exit(exitCode);
    }
}
