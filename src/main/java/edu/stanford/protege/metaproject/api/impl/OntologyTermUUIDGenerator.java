package edu.stanford.protege.metaproject.api.impl;

import edu.stanford.protege.metaproject.api.*;

import java.util.Optional;
import java.util.UUID;

/**
 * A generator of universally unique identifiers
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public final class OntologyTermUUIDGenerator implements OntologyTermIdGenerator {
    private OntologyTermUUIDGenerator instance = null;

    /**
     * Constructor
     */
    private OntologyTermUUIDGenerator() { }

    /**
     * Get the singleton instance of the ontology term UUID generator
     *
     * @return Instance of ontology term UUID generator
     */
    public OntologyTermUUIDGenerator getInstance() {
        if(instance == null) {
            instance = new OntologyTermUUIDGenerator();
        }
        return instance;
    }

    @Override
    public OntologyTermId getNextClassId() {
        return new OntologyTermIdImpl(getEmptyPrefix(), getRandomUUIDSuffix());
    }

    @Override
    public OntologyTermId getNextObjectPropertyId() {
        return new OntologyTermIdImpl(getEmptyPrefix(), getRandomUUIDSuffix());
    }

    @Override
    public OntologyTermId getNextDataPropertyId() {
        return new OntologyTermIdImpl(getEmptyPrefix(), getRandomUUIDSuffix());
    }

    @Override
    public OntologyTermId getNextAnnotationPropertyId() {
        return new OntologyTermIdImpl(getEmptyPrefix(), getRandomUUIDSuffix());
    }

    @Override
    public OntologyTermId getNextIndividualId() {
        return new OntologyTermIdImpl(getEmptyPrefix(), getRandomUUIDSuffix());
    }

    @Override
    public Optional<OntologyTermIdStatus> getCurrentOntologyTermIdStatus() {
        return Optional.empty();
    }

    /**
     * Get an empty identifier prefix
     *
     * @return Identifier prefix
     */
    private OntologyTermIdPrefix getEmptyPrefix() {
        return new OntologyTermIdPrefixImpl("");
    }

    /**
     * Get an identifier suffix based on a random UUID
     *
     * @return Identifier suffix
     */
    private OntologyTermIdSuffix getRandomUUIDSuffix() {
        return new OntologyTermIdSuffixImpl(getRandomUUID());
    }

    /**
     * Convenience method to get a random UUID
     *
     * @return Random string UUID
     */
    private String getRandomUUID() {
        return UUID.randomUUID().toString();
    }
}
