package org.kvpbldsck;

import com.google.inject.ConfigurationException;
import com.google.inject.Injector;
import picocli.CommandLine;

public final class PicocliDiFactory implements CommandLine.IFactory {

    private final Injector injector;

    public PicocliDiFactory(Injector injector) {
        this.injector = injector;
    }

    @Override
    public <K> K create(Class<K> cls) throws Exception {
        try {
            return injector.getInstance(cls);
        } catch (ConfigurationException ex) {
            System.err.printf("Fallback for class %s", cls.getName());
            return CommandLine
                    .defaultFactory()
                    .create(cls);
        }
    }
}
