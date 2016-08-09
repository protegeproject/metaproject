package edu.stanford.protege.metaproject;

import edu.stanford.protege.metaproject.api.ConfigurationManager;
import edu.stanford.protege.metaproject.api.PolicyFactory;
import edu.stanford.protege.metaproject.api.Serializer;
import edu.stanford.protege.metaproject.impl.ConfigurationManagerImpl;
import edu.stanford.protege.metaproject.impl.PolicyFactoryImpl;
import edu.stanford.protege.metaproject.serialization.DefaultJsonSerializer;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public final class Manager {

    /**
     * Get the configuration manager
     *
     * @return Configuration manager
     */
    public static ConfigurationManager getConfigurationManager() {
        return new ConfigurationManagerImpl(getFactory(), getSerializer());
    }

    /**
     * Get a factory for creating metaproject-related objects
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
    public static Serializer<?> getSerializer() {
        return new DefaultJsonSerializer();
    }
}
