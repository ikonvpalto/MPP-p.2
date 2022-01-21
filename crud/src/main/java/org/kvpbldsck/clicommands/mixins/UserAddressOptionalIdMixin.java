package org.kvpbldsck.clicommands.mixins;

import picocli.CommandLine.Parameters;

import javax.validation.constraints.Min;
import java.util.Optional;

public final class UserAddressOptionalIdMixin {
    @Parameters(description = "User ID", arity = "0..1", descriptionKey = "desc key", prompt = "prompt")
    @Min(value = 1, message = "Id should be a valid integer greater than 0")
    private Integer id = null;

    public Optional<Integer> getId() {
        return Optional.ofNullable(id);
    }
}
