package edu.stanford.protege.metaproject.impl;

import edu.stanford.protege.metaproject.api.ConfigurationWriter;
import edu.stanford.protege.metaproject.api.Serializer;
import edu.stanford.protege.metaproject.api.ServerConfiguration;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.ThreadSafe;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Rafael Gonçalves <br>
 * Center for Biomedical Informatics Research <br>
 * Stanford University
 */
@Immutable
@ThreadSafe
public final class ConfigurationWriterImpl implements ConfigurationWriter {
    @Nonnull private final Serializer serializer;

    /**
     * Constructor
     *
     * @param serializer    Configuration serializer
     */
    public ConfigurationWriterImpl(@Nonnull Serializer serializer) {
        this.serializer = checkNotNull(serializer);
    }

    @Override
    public synchronized void saveConfiguration(@Nonnull ServerConfiguration configuration, @Nonnull File outputFile) throws IOException {
        checkNotNull(outputFile, "Output configuration file must not be null");
        checkNotNull(configuration, "Server configuration must not be null");
        FileWriter fw = new FileWriter(outputFile);
        String json = serializer.write(configuration, ServerConfiguration.class);
        fw.write(json);
        fw.close();
    }
}
