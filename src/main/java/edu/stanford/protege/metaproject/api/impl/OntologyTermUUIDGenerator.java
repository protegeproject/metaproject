package edu.stanford.protege.metaproject.api.impl;

import edu.stanford.protege.metaproject.api.Id;
import edu.stanford.protege.metaproject.api.OntologyTermIdGenerator;

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
    public Id getNextClassId() {
        return new OntologyTermIdImpl(new OntologyTermIdPrefix(""), new OntologyTermIdSuffix(getId()));
    }

    @Override
    public Id getNextObjectPropertyId() {
        return new OntologyTermIdImpl(new OntologyTermIdPrefix(""), new OntologyTermIdSuffix(getId()));
    }

    @Override
    public Id getNextDataPropertyId() {
        return new OntologyTermIdImpl(new OntologyTermIdPrefix(""), new OntologyTermIdSuffix(getId()));
    }

    @Override
    public Id getNextAnnotationPropertyId() {
        return new OntologyTermIdImpl(new OntologyTermIdPrefix(""), new OntologyTermIdSuffix(getId()));
    }

    @Override
    public Id getNextIndividualId() {
        return new OntologyTermIdImpl(new OntologyTermIdPrefix(""), new OntologyTermIdSuffix(getId()));
    }

    /**
     * Convenience method to get a random UUID
     *
     * @return String identifier
     */
    private String getId() {
        return UUID.randomUUID().toString();
    }
}
