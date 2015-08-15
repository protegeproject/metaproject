package edu.stanford.protege.metaproject.api.impl;

import edu.stanford.protege.metaproject.api.AccessControlObjectId;
import edu.stanford.protege.metaproject.api.AccessControlObjectIdGenerator;

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
    public AccessControlObjectUUIDGenerator getInstance() {
        if(instance == null) {
            instance = new AccessControlObjectUUIDGenerator();
        }
        return instance;
    }

    /**
     * Get a new random UUID identifier
     *
     * @return Random UUID identifier
     */
    public AccessControlObjectId getId() {
        return new AccessControlObjectIdImpl(UUID.randomUUID().toString());
    }
}
