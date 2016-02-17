package edu.stanford.protege.metaproject.impl;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import edu.stanford.protege.metaproject.api.*;

import java.util.Optional;
import java.util.UUID;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A generator of entity IRIs based on the given IRI prefix, and an entity name that is
 * a universally unique identifier.
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public final class UuidEntityIriGenerator implements EntityIriGenerator {
    private final EntityIriPrefix iriPrefix;

    /**
     * Constructor
     *
     * @param iriPrefix Entity IRI prefix
     */
    public UuidEntityIriGenerator(EntityIriPrefix iriPrefix) {
        this.iriPrefix = checkNotNull(iriPrefix);
    }

    @Override
    public EntityIriPrefix getEntityIriPrefix() {
        return iriPrefix;
    }

    @Override
    public EntityIri getNextClassIri() {
        return new EntityIriImpl(iriPrefix, getRandomUuidName());
    }

    @Override
    public EntityIri getNextObjectPropertyIri() {
        return new EntityIriImpl(iriPrefix, getRandomUuidName());
    }

    @Override
    public EntityIri getNextDataPropertyIri() {
        return new EntityIriImpl(iriPrefix, getRandomUuidName());
    }

    @Override
    public EntityIri getNextAnnotationPropertyIri() {
        return new EntityIriImpl(iriPrefix, getRandomUuidName());
    }

    @Override
    public EntityIri getNextIndividualIri() {
        return new EntityIriImpl(iriPrefix, getRandomUuidName());
    }

    @Override
    public Optional<EntityIriStatus> getEntityIriStatus() {
        return Optional.empty();
    }

    /**
     * Get an entity name that is a random UUID
     *
     * @return UUID entity name
     */
    private EntityName getRandomUuidName() {
        return new EntityNameImpl(EntityNamePrefixImpl.EMPTY, new EntityNameSuffixImpl(getRandomUUID()));
    }

    /**
     * Convenience method to get a random UUID
     *
     * @return Random string UUID
     */
    private String getRandomUUID() {
        return UUID.randomUUID().toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UuidEntityIriGenerator that = (UuidEntityIriGenerator) o;
        return Objects.equal(iriPrefix, that.iriPrefix);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(iriPrefix);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("iriPrefix", iriPrefix)
                .toString();
    }
}
