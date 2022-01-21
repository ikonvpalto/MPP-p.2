package org.kvpbldsck.repository;

import org.kvpbldsck.models.UserAddress;

import java.util.List;
import java.util.Optional;

public interface UserAddressRepository {

    int create(UserAddress userAddress);

    Optional<UserAddress> read(int id);
    List<UserAddress> read();

    int update(UserAddress userAddress);

    int delete(int id);

}
