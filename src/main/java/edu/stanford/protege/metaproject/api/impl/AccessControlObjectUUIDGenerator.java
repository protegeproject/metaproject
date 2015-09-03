package edu.stanford.protege.metaproject.api.impl;

import com.google.common.base.MoreObjects;
import edu.stanford.protege.metaproject.api.*;

import java.util.UUID;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public final class AccessControlObjectUUIDGenerator implements AccessControlObjectIdGenerator {
    private static AccessControlObjectUUIDGenerator instance = null;

    /**
     * Constructor
     */
    private AccessControlObjectUUIDGenerator() {}

    /**
     * Get singleton instance of this UUID generator
     *
     * @return Instance of the UUID generator
     */
    public static AccessControlObjectUUIDGenerator getInstance() {
        if(instance == null) {
            instance = new AccessControlObjectUUIDGenerator();
        }
        return instance;
    }

    /**
     * Get a new random UUID user identifier
     *
     * @return Random UUID user identifier
     */
    @Override
    public UserId getUserId() {
        return new UserIdImpl(newUUID());
    }

    /**
     * Get a new random UUID role identifier
     *
     * @return Random UUID role identifier
     */
    @Override
    public RoleId getRoleId() {
        return new RoleIdImpl(newUUID());
    }

    /**
     * Get a new random UUID project identifier
     *
     * @return Random UUID project identifier
     */
    @Override
    public ProjectId getProjectId() {
        return new ProjectIdImpl(newUUID());
    }

    /**
     * Get a new random UUID operation identifier
     *
     * @return Random UUID operation identifier
     */
    @Override
    public OperationId getOperationId() {
        return new OperationIdImpl(newUUID());
    }

    /**
     * Convenience method to generate a new random UUID
     *
     * @return The string representing the generated UUID
     */
    private String newUUID() {
        return UUID.randomUUID().toString();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .toString();
    }
}
