package org.kvpbldsck.clicommands;

import com.google.inject.Inject;
import org.kvpbldsck.clicommands.mixins.UserAddressDataMixin;
import org.kvpbldsck.models.UserAddress;
import org.kvpbldsck.repository.UserAddressRepository;
import picocli.CommandLine.Command;
import picocli.CommandLine.Mixin;

import java.util.concurrent.Callable;

@Command(name = "add", description = "Add new user address")
public final class AddAddressCommand implements Callable<Integer> {

    @Mixin
    private UserAddressDataMixin userAddressData;

    private UserAddressRepository userAddressRepository;

    @Inject
    public AddAddressCommand(UserAddressRepository userAddressRepository) {
        this.userAddressRepository = userAddressRepository;
    }

    @Override
    public Integer call() {
        System.out.printf(
                "Add, last name: %s, first name: %s, address: %s, phone: %s%n",
                userAddressData.getLastName(),
                userAddressData.getFirstName(),
                userAddressData.getAddress(),
                userAddressData.getPhone());

        UserAddress userAddress = new UserAddress(
                0,
                userAddressData.getLastName(),
                userAddressData.getFirstName(),
                userAddressData.getAddress(),
                userAddressData.getPhone());

        int insertsCount = userAddressRepository.create(userAddress);

        return insertsCount == 1 ? 0 : 1;
    }
}
