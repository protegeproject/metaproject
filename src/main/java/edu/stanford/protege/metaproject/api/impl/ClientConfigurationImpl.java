package edu.stanford.protege.metaproject.api.impl;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import edu.stanford.protege.metaproject.api.ClientConfiguration;
import edu.stanford.protege.metaproject.api.Policy;

import javax.swing.*;
import java.io.Serializable;
import java.util.Map;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public final class ClientConfigurationImpl implements ClientConfiguration, Serializable {
    private static final long serialVersionUID = 4399619301416799478L;
    private final Policy policy;
    private final int synchronisationDelay;
    private final ImmutableSet<? extends JComponent> disabledUIElements;
    private final ImmutableMap<String,String> properties;

    /**
     * Constructor
     *
     * @param policy    Access control policy
     * @param synchronisationDelay  Synchronisation delay for configurations (in seconds)
     * @param disabledUIElements    Set of disabled UI elements
     * @param properties    Map of additional string properties
     */
    public ClientConfigurationImpl(Policy policy, int synchronisationDelay, Set<? extends JComponent> disabledUIElements, Map<String,String> properties) {
        this.policy = checkNotNull(policy);
        this.synchronisationDelay = checkNotNull(synchronisationDelay);

        ImmutableSet<? extends JComponent> disabledUIElementsCopy = new ImmutableSet.Builder<JComponent>().addAll(checkNotNull(disabledUIElements)).build();
        this.disabledUIElements = checkNotNull(disabledUIElementsCopy);

        ImmutableMap<String,String> immutableMap = new ImmutableMap.Builder<String, String>().putAll(checkNotNull(properties)).build();
        this.properties = checkNotNull(immutableMap);
    }

    @Override
    public Policy getPolicy() {
        return policy;
    }

    @Override
    public int getSynchronisationDelay() {
        return synchronisationDelay;
    }

    @Override
    public Set<? extends JComponent> getDisabledUIElements() {
        return disabledUIElements;
    }

    @Override
    public Map<String, String> getProperties() {
        return properties;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientConfigurationImpl that = (ClientConfigurationImpl) o;
        return Objects.equal(synchronisationDelay, that.synchronisationDelay) &&
                Objects.equal(policy, that.policy) &&
                Objects.equal(disabledUIElements, that.disabledUIElements) &&
                Objects.equal(properties, that.properties);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(policy, synchronisationDelay, disabledUIElements, properties);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("policy", policy)
                .add("synchronisationDelay", synchronisationDelay)
                .add("disabledUIElements", disabledUIElements)
                .add("properties", properties)
                .toString();
    }
}
