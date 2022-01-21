package org.kvpbldsck;

import com.google.inject.Guice;
import org.kvpbldsck.clicommands.RootCommand;
import picocli.CommandLine;

public final class Main {
    public static void main(String[] args) {
        var commandLine = prepare();
//        test(commandLine);
        int exitCode = commandLine.execute(args);
        System.exit(exitCode);
    }

    private static CommandLine prepare() {
        var dependencyInjector = Guice.createInjector(new DiModule());
        var picocliDiFactory = new PicocliDiFactory(dependencyInjector);
        return new CommandLine(new RootCommand(), picocliDiFactory);
    }

    private static void test(CommandLine commandLine) {
        exec(commandLine, "help");
        exec(commandLine, "help", "add");
        exec(commandLine, "help", "update");
        exec(commandLine, "help", "get");
        exec(commandLine, "help", "delete");

        exec(commandLine, "add", "Rodriguez", "James", "6420 Central Ave, Saint Petersburg, Florida(FL), 33707", "(727) 381-6284");
        exec(commandLine, "get");

        exec(commandLine, "update", "9", "Martinez", "Mary", "137 Turkey Camp Rd E, East Lynn, West Virginia(WV), 25512", "(304) 849-4619");
        exec(commandLine, "get");

        exec(commandLine, "get", "9");

        exec(commandLine, "delete", "9");
        exec(commandLine, "get");
    }

    private static void exec(CommandLine commandLine, String... args) {
        System.out.println(commandLine.execute(args));
    }
}
