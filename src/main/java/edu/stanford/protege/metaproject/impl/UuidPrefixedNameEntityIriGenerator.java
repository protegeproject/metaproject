package edu.stanford.protege.metaproject.impl;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import edu.stanford.protege.metaproject.api.*;

import java.util.Optional;
import java.util.UUID;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A generator of entity IRIs using the specified IRI prefix. The entity names
 * have a UUID suffix and the given prefixes for each entity type
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public final class UuidPrefixedNameEntityIriGenerator implements PrefixedNameEntityIriGenerator {
    private final EntityIriPrefix iriPrefix;
    private final EntityNamePrefix classNamePrefix, objectPropertyNamePrefix, dataPropertyNamePrefix, annotationPropertyNamePrefix, individualNamePrefix;

    /**
     * Package-private constructor; use builder
     *
     * @param iriPrefix Entity IRI prefix
     * @param classNamePrefix Class name prefix
     * @param objectPropertyNamePrefix    Object property name prefix
     * @param dataPropertyNamePrefix  Data property name prefix
     * @param annotationPropertyNamePrefix    Annotation property name prefix
     * @param individualNamePrefix    Individual name prefix
     */
    UuidPrefixedNameEntityIriGenerator(EntityIriPrefix iriPrefix, EntityNamePrefix classNamePrefix, EntityNamePrefix objectPropertyNamePrefix,
                                       EntityNamePrefix dataPropertyNamePrefix, EntityNamePrefix annotationPropertyNamePrefix, EntityNamePrefix individualNamePrefix) {
        this.iriPrefix = checkNotNull(iriPrefix);
        this.classNamePrefix = checkNotNull(classNamePrefix);
        this.objectPropertyNamePrefix = checkNotNull(objectPropertyNamePrefix);
        this.dataPropertyNamePrefix = checkNotNull(dataPropertyNamePrefix);
        this.annotationPropertyNamePrefix = checkNotNull(annotationPropertyNamePrefix);
        this.individualNamePrefix = checkNotNull(individualNamePrefix);
    }

    @Override
    public EntityIriPrefix getEntityIriPrefix() {
        return iriPrefix;
    }

    @Override
    public EntityIri getNextClassIri() {
        return new EntityIriImpl(iriPrefix, new EntityNameImpl(classNamePrefix, getNameSuffix()));
    }

    @Override
    public EntityIri getNextObjectPropertyIri() {
        return new EntityIriImpl(iriPrefix, new EntityNameImpl(objectPropertyNamePrefix, getNameSuffix()));
    }

    @Override
    public EntityIri getNextDataPropertyIri() {
        return new EntityIriImpl(iriPrefix, new EntityNameImpl(dataPropertyNamePrefix, getNameSuffix()));
    }

    @Override
    public EntityIri getNextAnnotationPropertyIri() {
        return new EntityIriImpl(iriPrefix, new EntityNameImpl(annotationPropertyNamePrefix, getNameSuffix()));
    }

    @Override
    public EntityIri getNextIndividualIri() {
        return new EntityIriImpl(iriPrefix, new EntityNameImpl(individualNamePrefix, getNameSuffix()));
    }

    /**
     * Convenience method to get a random UUID entity name suffix
     *
     * @return Entity name suffix
     */
    private EntityNameSuffix getNameSuffix() {
        return new EntityNameSuffixImpl(UUID.randomUUID().toString());
    }

    @Override
    public EntityNamePrefix getClassNamePrefix() {
        return classNamePrefix;
    }

    @Override
    public EntityNamePrefix getObjectPropertyNamePrefix() {
        return objectPropertyNamePrefix;
    }

    @Override
    public EntityNamePrefix getDataPropertyNamePrefix() {
        return dataPropertyNamePrefix;
    }

    @Override
    public EntityNamePrefix getAnnotationPropertyNamePrefix() {
        return annotationPropertyNamePrefix;
    }

    @Override
    public EntityNamePrefix getIndividualNamePrefix() {
        return individualNamePrefix;
    }

    @Override
    public Optional<EntityIriStatus> getEntityIriStatus() {
        return Optional.of(new EntityIriStatusImpl.Builder()
                .setEntityIriPrefix(iriPrefix)
                .setClassNamePrefix(classNamePrefix)
                .setObjectPropertyNamePrefix(objectPropertyNamePrefix)
                .setDataPropertyNamePrefix(dataPropertyNamePrefix)
                .setAnnotationPropertyNamePrefix(annotationPropertyNamePrefix)
                .setIndividualNamePrefix(individualNamePrefix)
                .createEntityIriStatus());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UuidPrefixedNameEntityIriGenerator that = (UuidPrefixedNameEntityIriGenerator) o;
        return Objects.equal(classNamePrefix, that.classNamePrefix) &&
                Objects.equal(objectPropertyNamePrefix, that.objectPropertyNamePrefix) &&
                Objects.equal(dataPropertyNamePrefix, that.dataPropertyNamePrefix) &&
                Objects.equal(annotationPropertyNamePrefix, that.annotationPropertyNamePrefix) &&
                Objects.equal(individualNamePrefix, that.individualNamePrefix);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(classNamePrefix, objectPropertyNamePrefix, dataPropertyNamePrefix, annotationPropertyNamePrefix, individualNamePrefix);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("classNamePrefix", classNamePrefix)
                .add("objectPropertyNamePrefix", objectPropertyNamePrefix)
                .add("dataPropertyNamePrefix", dataPropertyNamePrefix)
                .add("annotationPropertyNamePrefix", annotationPropertyNamePrefix)
                .add("individualNamePrefix", individualNamePrefix)
                .toString();
    }

    /**
     * @author Rafael Gonçalves <br>
     * Stanford Center for Biomedical Informatics Research
     */
    public static class Builder {
        private EntityIriPrefix iriPrefix;
        private EntityNamePrefix classNamePrefix, objectPropertyNamePrefix, dataPropertyNamePrefix, annotationPropertyNamePrefix, individualNamePrefix;

        /**
         * Set the entity IRI prefix
         *
         * @param iriPrefix Entity IRI prefix
         * @return Builder
         */
        public Builder setEntityIriPrefix(EntityIriPrefix iriPrefix) {
            this.iriPrefix = iriPrefix;
            return this;
        }

        /**
         * Set the class name prefix
         *
         * @param classNamePrefix Class name prefix
         * @return Builder
         */
        public Builder setClassNamePrefix(EntityNamePrefix classNamePrefix) {
            this.classNamePrefix = classNamePrefix;
            return this;
        }

        /**
         * Set the object property name prefix
         * @param objectPropertyNamePrefix    Object property name prefix
         * @return Builder
         */
        public Builder setObjectPropertyNamePrefix(EntityNamePrefix objectPropertyNamePrefix) {
            this.objectPropertyNamePrefix = objectPropertyNamePrefix;
            return this;
        }

        /**
         * Set the data property name prefix
         *
         * @param dataPropertyNamePrefix  Data property name prefix
         * @return Builder
         */
        public Builder setDataPropertyNamePrefix(EntityNamePrefix dataPropertyNamePrefix) {
            this.dataPropertyNamePrefix = dataPropertyNamePrefix;
            return this;
        }

        /**
         * Set the annotation property name prefix
         *
         * @param annotationPropertyNamePrefix    Annotation property name prefix
         * @return Builder
         */
        public Builder setAnnotationPropertyNamePrefix(EntityNamePrefix annotationPropertyNamePrefix) {
            this.annotationPropertyNamePrefix = annotationPropertyNamePrefix;
            return this;
        }

        /**
         * Set the individual name prefix
         * @param individualNamePrefix    Individual name prefix
         * @return Builder
         */
        public Builder setIndividualNamePrefix(EntityNamePrefix individualNamePrefix) {
            this.individualNamePrefix = individualNamePrefix;
            return this;
        }

        /**
         * Create a generator of entity IRIs where the entity names have the given prefixes, and the entity name
         * suffixes are UUIDs
         *
         * @return Instance of PrefixedUUIDGenerator
         */
        public UuidPrefixedNameEntityIriGenerator createPrefixedUUIDGenerator() {
            return new UuidPrefixedNameEntityIriGenerator(iriPrefix, classNamePrefix, objectPropertyNamePrefix, dataPropertyNamePrefix, annotationPropertyNamePrefix, individualNamePrefix);
        }
    }
}
