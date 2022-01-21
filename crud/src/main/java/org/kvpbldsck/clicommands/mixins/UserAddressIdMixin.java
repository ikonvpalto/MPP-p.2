package org.kvpbldsck.clicommands.mixins;

import picocli.CommandLine.Parameters;

import javax.validation.constraints.Min;

public final class UserAddressIdMixin {
    @Parameters(description = "User ID")
    @Min(value = 1, message = "Id should be a valid integer greater than 0")
    private int id;

    public int getId() {
        return id;
    }
}

