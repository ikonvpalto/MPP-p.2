package org.kvpbldsck.clicommands;

import com.google.inject.Inject;
import org.kvpbldsck.clicommands.mixins.UserAddressDataMixin;
import org.kvpbldsck.clicommands.mixins.UserAddressIdMixin;
import org.kvpbldsck.models.UserAddress;
import org.kvpbldsck.repository.UserAddressRepository;
import picocli.CommandLine.Command;
import picocli.CommandLine.Mixin;

import java.util.concurrent.Callable;

@Command(name = "update", description = "Update existing user address")
public final class UpdateAddressCommand implements Callable<Integer> {

    @Mixin
    private UserAddressIdMixin userAddressId;

    @Mixin
    private UserAddressDataMixin userAddressData;

    private UserAddressRepository userAddressRepository;

    @Inject
    public UpdateAddressCommand(UserAddressRepository userAddressRepository) {
        this.userAddressRepository = userAddressRepository;
    }

    @Override
    public Integer call() {
        System.out.printf(
                "Update, id: %d, last name: %s, first name: %s, address: %s, phone: %s%n",
                userAddressId.getId(),
                userAddressData.getLastName(),
                userAddressData.getFirstName(),
                userAddressData.getAddress(),
                userAddressData.getPhone());

        UserAddress userAddress = new UserAddress(
                userAddressId.getId(),
                userAddressData.getLastName(),
                userAddressData.getFirstName(),
                userAddressData.getAddress(),
                userAddressData.getPhone());

        int updatesCount = userAddressRepository.update(userAddress);

        return updatesCount == 1 ? 0 : 1;
    }
}
