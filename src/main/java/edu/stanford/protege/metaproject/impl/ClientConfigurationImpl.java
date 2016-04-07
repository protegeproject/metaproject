package edu.stanford.protege.metaproject.impl;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import edu.stanford.protege.metaproject.api.ClientConfiguration;
import edu.stanford.protege.metaproject.api.Metaproject;

import java.io.Serializable;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class ClientConfigurationImpl implements ClientConfiguration, Serializable {
    private static final long serialVersionUID = -8099436087932458464L;
    private final Metaproject metaproject;
    private final int synchronisationDelay;
    private Map<String,String> properties;

    /**
     * Package-private constructor; use {@link ClientConfigurationBuilder}
     *
     * @param metaproject    Access control policy
     * @param synchronisationDelay  Synchronisation delay for configurations (in seconds)
     * @param properties    Map of additional properties
     */
    ClientConfigurationImpl(Metaproject metaproject, int synchronisationDelay, Map<String,String> properties) {
        this.metaproject = checkNotNull(metaproject);
        this.synchronisationDelay = checkNotNull(synchronisationDelay);
        this.properties = checkNotNull(properties);
    }

    @Override
    public Metaproject getMetaproject() {
        return metaproject;
    }

    @Override
    public int getSynchronisationDelay() {
        return synchronisationDelay;
    }

    @Override
    public Map<String, String> getProperties() {
        return properties;
    }

    @Override
    public void addProperty(String key, String value) {
        properties.put(checkNotNull(key), checkNotNull(value));
    }

    @Override
    public void removeProperty(String key) {
        properties.remove(checkNotNull(key));
    }

    @Override
    public String getProperty(String key) {
        return properties.get(key);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientConfigurationImpl that = (ClientConfigurationImpl) o;
        return Objects.equal(synchronisationDelay, that.synchronisationDelay) &&
                Objects.equal(metaproject, that.metaproject) &&
                Objects.equal(properties, that.properties);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(metaproject, synchronisationDelay, properties);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("metaproject", metaproject)
                .add("synchronisationDelay", synchronisationDelay)
                .add("properties", properties)
                .toString();
    }
}
