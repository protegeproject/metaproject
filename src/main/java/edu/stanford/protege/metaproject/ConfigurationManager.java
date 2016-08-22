package edu.stanford.protege.metaproject;

import edu.stanford.protege.metaproject.api.ConfigurationLoader;
import edu.stanford.protege.metaproject.api.ConfigurationWriter;
import edu.stanford.protege.metaproject.api.PolicyFactory;
import edu.stanford.protege.metaproject.api.Serializer;
import edu.stanford.protege.metaproject.impl.ConfigurationLoaderImpl;
import edu.stanford.protege.metaproject.impl.ConfigurationWriterImpl;
import edu.stanford.protege.metaproject.impl.PolicyFactoryImpl;
import edu.stanford.protege.metaproject.serialization.DefaultJsonSerializer;

import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.ThreadSafe;

/**
 * @author Rafael Gonçalves <br>
 * Center for Biomedical Informatics Research <br>
 * Stanford University
 */
@Immutable
@ThreadSafe
public final class ConfigurationManager {

    /**
     * Get a factory for creating policy-related objects
     *
     * @return Factory
     */
    public static PolicyFactory getFactory() {
        return new PolicyFactoryImpl();
    }

    /**
     * Get the default configuration serializer
     *
     * @return Serializer
     */
    private static Serializer getSerializer() {
        return new DefaultJsonSerializer();
    }

    /**
     * Get the configuration loader
     *
     * @return Configuration loader
     */
    public static ConfigurationLoader getConfigurationLoader() {
        return new ConfigurationLoaderImpl(getSerializer());
    }

    /**
     * Get the configuration writer
     *
     * @return Configuration writer
     */
    public static ConfigurationWriter getConfigurationWriter() {
        return new ConfigurationWriterImpl(getSerializer());
    }
}
