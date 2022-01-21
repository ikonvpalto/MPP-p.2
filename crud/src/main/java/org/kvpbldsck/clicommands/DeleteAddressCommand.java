package org.kvpbldsck.clicommands;

import com.google.inject.Inject;
import org.kvpbldsck.clicommands.mixins.UserAddressIdMixin;
import org.kvpbldsck.repository.UserAddressRepository;
import picocli.CommandLine.Command;
import picocli.CommandLine.Mixin;

import java.util.concurrent.Callable;

@Command(name = "delete", description = "Delete existing user address")
public final class DeleteAddressCommand implements Callable<Integer> {

    @Mixin
    private UserAddressIdMixin userAddressId;

    private UserAddressRepository userAddressRepository;

    @Inject
    public DeleteAddressCommand(UserAddressRepository userAddressRepository) {
        this.userAddressRepository = userAddressRepository;
    }

    @Override
    public Integer call() {
        System.out.printf(
                "Delete, id: %d%n",
                userAddressId.getId());

        int deletesCount = userAddressRepository.delete(userAddressId.getId());

        return deletesCount == 1 ? 0 : 1;
    }
}
