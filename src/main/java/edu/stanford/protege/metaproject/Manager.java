package edu.stanford.protege.metaproject;

import edu.stanford.protege.metaproject.api.ConfigurationManager;
import edu.stanford.protege.metaproject.api.MetaprojectFactory;
import edu.stanford.protege.metaproject.api.Serializer;
import edu.stanford.protege.metaproject.impl.ConfigurationManagerImpl;
import edu.stanford.protege.metaproject.impl.MetaprojectFactoryImpl;
import edu.stanford.protege.metaproject.serialization.DefaultJsonSerializer;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public final class Manager {
    private static MetaprojectFactory factory;
    private static ConfigurationManager configurationManager;
    private static Serializer<?> serializer;

    /**
     * Get a factory for creating metaproject-related objects
     *
     * @return Factory
     */
    public static MetaprojectFactory getFactory() {
        if(factory == null) {
            factory = new MetaprojectFactoryImpl();
        }
        return factory;
    }

    /**
     * Get the configuration manager
     *
     * @return Configuration manager
     */
    public static ConfigurationManager getConfigurationManager() {
        if(configurationManager == null) {
            configurationManager = new ConfigurationManagerImpl(getSerializer());
        }
        return configurationManager;
    }

    /**
     * Get the default configuration serializer
     *
     * @return Serializer
     */
    public static Serializer<?> getSerializer() {
        if(serializer == null) {
            serializer = new DefaultJsonSerializer();
        }
        return serializer;
    }
}
