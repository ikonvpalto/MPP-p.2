package org.kvpbldsck.clicommands;

import picocli.CommandLine.Command;
import picocli.CommandLine.HelpCommand;

@Command(subcommands = {
        AddAddressCommand.class,
        DeleteAddressCommand.class,
        GetAddressCommand.class,
        UpdateAddressCommand.class,
        HelpCommand.class
})
public final class RootCommand {
}
