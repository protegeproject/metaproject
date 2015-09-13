package edu.stanford.protege.metaproject.api.impl;

import com.google.common.base.MoreObjects;
import edu.stanford.protege.metaproject.api.*;

import java.util.UUID;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public final class AccessControlObjectUuidGenerator implements AccessControlObjectIdGenerator {

    /**
     * Constructor
     */
    public AccessControlObjectUuidGenerator() { }

    /**
     * Get a new random UUID user identifier
     *
     * @return Random UUID user identifier
     */
    @Override
    public UserId getUserId() {
        return new UserIdImpl(getNewUuid());
    }

    /**
     * Get a new random UUID role identifier
     *
     * @return Random UUID role identifier
     */
    @Override
    public RoleId getRoleId() {
        return new RoleIdImpl(getNewUuid());
    }

    /**
     * Get a new random UUID project identifier
     *
     * @return Random UUID project identifier
     */
    @Override
    public ProjectId getProjectId() {
        return new ProjectIdImpl(getNewUuid());
    }

    /**
     * Get a new random UUID operation identifier
     *
     * @return Random UUID operation identifier
     */
    @Override
    public OperationId getOperationId() {
        return new OperationIdImpl(getNewUuid());
    }

    /**
     * Convenience method to generate a new random UUID
     *
     * @return The string representing the generated UUID
     */
    private String getNewUuid() {
        return UUID.randomUUID().toString();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .toString();
    }
}
