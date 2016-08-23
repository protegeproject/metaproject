package edu.stanford.protege.metaproject.impl;

import edu.stanford.protege.metaproject.api.ConfigurationLoader;
import edu.stanford.protege.metaproject.api.Serializer;
import edu.stanford.protege.metaproject.api.ServerConfiguration;
import edu.stanford.protege.metaproject.api.exception.ObjectConversionException;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.ThreadSafe;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.Reader;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Rafael Gonçalves <br>
 * Center for Biomedical Informatics Research <br>
 * Stanford University
 */
@Immutable
@ThreadSafe
public final class ConfigurationLoaderImpl implements ConfigurationLoader {
    @Nonnull private final Serializer serializer;

    /**
     * Constructor
     *
     * @param serializer    Configuration serializer
     */
    public ConfigurationLoaderImpl(@Nonnull Serializer serializer) {
        this.serializer = checkNotNull(serializer);
    }

    @Override
    @Nonnull
    public synchronized ServerConfiguration loadConfiguration(@Nonnull File f) throws FileNotFoundException, ObjectConversionException {
        checkNotNull(f, "Input configuration file must not be null");
        return new ConfigurationBuilder(checkNotNull(serializer.parse(f, ServerConfiguration.class)))
                .createServerConfiguration();
    }

    @Override
    @Nonnull
    public synchronized ServerConfiguration loadConfiguration(@Nonnull Reader reader) throws ObjectConversionException {
        checkNotNull(reader, "Input reader must not be null");
        return new ConfigurationBuilder(checkNotNull(serializer.parse(reader, ServerConfiguration.class)))
                .createServerConfiguration();
    }
}
