package edu.stanford.protege.metaproject.api.impl;

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
public final class AccessControlClientConfiguration implements ClientConfiguration, Serializable {
    private static final long serialVersionUID = 3417184970074020244L;
    private final int synchronisationDelay;
    private final ImmutableSet<? extends JComponent> disabledUIElements;

    /**
     * Constructor
     *
     * @param synchronisationDelay  Synchronisation delay for configurations
     * @param disabledUIElements    Set of disabled UI elements
     */
    public AccessControlClientConfiguration(int synchronisationDelay, Set<? extends JComponent> disabledUIElements) {
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
}
