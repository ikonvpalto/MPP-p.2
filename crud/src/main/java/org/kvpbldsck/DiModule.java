package org.kvpbldsck;

import com.google.inject.AbstractModule;
import org.kvpbldsck.repository.DbConnectionEstablisher;
import org.kvpbldsck.repository.PostgresUserAddressRepository;
import org.kvpbldsck.repository.UserAddressRepository;

public final class DiModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(UserAddressRepository.class).to(PostgresUserAddressRepository.class);
        bind(DbConnectionEstablisher.class).toInstance(DbConnectionEstablisher.Create());
    }
}
