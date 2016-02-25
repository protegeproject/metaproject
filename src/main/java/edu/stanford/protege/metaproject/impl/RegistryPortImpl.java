package edu.stanford.protege.metaproject.impl;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import edu.stanford.protege.metaproject.api.RegistryPort;

import java.io.Serializable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class RegistryPortImpl implements RegistryPort, Serializable {
    private static final long serialVersionUID = 5491727352411673714L;
    private final Integer registryPort;

    /**
     * Constructor
     *
     * @param registryPort  RMI registry port number
     */
    public RegistryPortImpl(Integer registryPort) {
        this.registryPort = checkNotNull(registryPort);
    }

    @Override
    public Integer get() {
        return registryPort;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegistryPortImpl that = (RegistryPortImpl) o;
        return Objects.equal(registryPort, that.registryPort);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(registryPort);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("registryPort", registryPort)
                .toString();
    }
}
