package org.kvpbldsck.clicommands;

import com.google.inject.Inject;
import org.kvpbldsck.clicommands.mixins.UserAddressOptionalIdMixin;
import org.kvpbldsck.models.UserAddress;
import org.kvpbldsck.repository.UserAddressRepository;
import picocli.CommandLine.Command;
import picocli.CommandLine.Mixin;

import java.util.concurrent.Callable;

@Command(name = "get", description = "Get one or all of user addresses")
public final class GetAddressCommand implements Callable<Integer> {

    @Mixin
    private UserAddressOptionalIdMixin userAddressId;

    private UserAddressRepository userAddressRepository;

    @Inject
    public GetAddressCommand(UserAddressRepository userAddressRepository) {
        this.userAddressRepository = userAddressRepository;
    }

    @Override
    public Integer call() {
        return userAddressId.getId().isPresent()
                ? getSingleSubcommand(userAddressId.getId().get())
                : getAllSubcommand();
    }

    private int getAllSubcommand() {
        System.out.println("Get all");

        var users = userAddressRepository.read();

        System.out.printf("Total users: %d%n", users.size());
        for (var user: users) {
            printUser(user);
        }

        return 0;
    }

    public Integer getSingleSubcommand(int id) {
        System.out.printf("Get, id: %d%n", id);

        var user = userAddressRepository.read(id);

        if (user.isEmpty()) {
            return 1;
        }

        printUser(user.get());
        return 0;
    }

    private void printUser(UserAddress user) {
        System.out.printf(
                "Id: %d, last name: %s, first name: %s, address: %s, phone: %s%n",
                user.id(),
                user.lastName(),
                user.firstName(),
                user.address(),
                user.phone());
    }
}
