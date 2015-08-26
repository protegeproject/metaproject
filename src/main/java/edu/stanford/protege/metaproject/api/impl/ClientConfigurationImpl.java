package edu.stanford.protege.metaproject.api.impl;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.collect.ImmutableSet;
import edu.stanford.protege.metaproject.api.ClientConfiguration;

import javax.swing.*;
import java.io.Serializable;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public final class ClientConfigurationImpl implements ClientConfiguration, Serializable {
    private static final long serialVersionUID = 3287120853262344737L;
    private final int synchronisationDelay;
    private final ImmutableSet<? extends JComponent> disabledUIElements;

    /**
     * Constructor
     *
     * @param synchronisationDelay  Synchronisation delay for configurations
     * @param disabledUIElements    Set of disabled UI elements
     */
    public ClientConfigurationImpl(int synchronisationDelay, Set<? extends JComponent> disabledUIElements) {
        this.synchronisationDelay = checkNotNull(synchronisationDelay);

        ImmutableSet<? extends JComponent> disabledUIElementsCopy = new ImmutableSet.Builder<JComponent>().addAll(checkNotNull(disabledUIElements)).build();
        this.disabledUIElements = checkNotNull(disabledUIElementsCopy);
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientConfigurationImpl that = (ClientConfigurationImpl) o;
        return Objects.equal(synchronisationDelay, that.synchronisationDelay) &&
                Objects.equal(disabledUIElements, that.disabledUIElements);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(synchronisationDelay, disabledUIElements);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("synchronisationDelay", synchronisationDelay)
                .add("disabledUIElements", disabledUIElements)
                .toString();
    }
}
