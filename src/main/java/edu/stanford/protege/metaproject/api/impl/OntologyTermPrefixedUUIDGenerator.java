package edu.stanford.protege.metaproject.api.impl;

import edu.stanford.protege.metaproject.api.*;

import java.util.Optional;
import java.util.UUID;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A generator of prefixed universally unique identifiers
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public final class OntologyTermPrefixedUUIDGenerator implements OntologyTermPrefixedIdGenerator {
    private final IdPrefix classIdPrefix, objectPropertyIdPrefix, dataPropertyIdPrefix, annotationPropertyIdPrefix, individualIdPrefix;

    /**
     * Package-private constructor; use builder
     *
     * @param classIdPrefix Class identifier prefix
     * @param objectPropertyIdPrefix    Object property identifier prefix
     * @param dataPropertyIdPrefix  Data property identifier prefix
     * @param annotationPropertyIdPrefix    Annotation property identifier prefix
     * @param individualIdPrefix    Individual identifier prefix
     */
    OntologyTermPrefixedUUIDGenerator(IdPrefix classIdPrefix, IdPrefix objectPropertyIdPrefix, IdPrefix dataPropertyIdPrefix,
                                      IdPrefix annotationPropertyIdPrefix, IdPrefix individualIdPrefix) {
        this.classIdPrefix = checkNotNull(classIdPrefix);
        this.objectPropertyIdPrefix = checkNotNull(objectPropertyIdPrefix);
        this.dataPropertyIdPrefix = checkNotNull(dataPropertyIdPrefix);
        this.annotationPropertyIdPrefix = checkNotNull(annotationPropertyIdPrefix);
        this.individualIdPrefix = checkNotNull(individualIdPrefix);
    }

    @Override
    public Id getNextClassId() {
        return new OntologyTermIdImpl(classIdPrefix, getId());
    }

    @Override
    public Id getNextObjectPropertyId() {
        return new OntologyTermIdImpl(objectPropertyIdPrefix, getId());
    }

    @Override
    public Id getNextDataPropertyId() {
        return new OntologyTermIdImpl(dataPropertyIdPrefix, getId());
    }

    @Override
    public Id getNextAnnotationPropertyId() {
        return new OntologyTermIdImpl(annotationPropertyIdPrefix, getId());
    }

    @Override
    public Id getNextIndividualId() {
        return new OntologyTermIdImpl(individualIdPrefix, getId());
    }

    /**
     * Convenience method to get a random UUID prefixed with the given term identifier prefix
     *
     * @return String identifier
     */
    private IdSuffix getId() {
        return new OntologyTermIdSuffix(UUID.randomUUID().toString());
    }

    @Override
    public IdPrefix getClassIdPrefix() {
        return classIdPrefix;
    }

    @Override
    public IdPrefix getObjectPropertyIdPrefix() {
        return objectPropertyIdPrefix;
    }

    @Override
    public IdPrefix getDataPropertyIdPrefix() {
        return dataPropertyIdPrefix;
    }

    @Override
    public IdPrefix getAnnotationPropertyIdPrefix() {
        return annotationPropertyIdPrefix;
    }

    @Override
    public IdPrefix getIndividualIdPrefix() {
        return individualIdPrefix;
    }

    @Override
    public Optional<OntologyTermIdStatus> getCurrentOntologyTermIdStatus() {
        return null;
    }

    /**
     * @author Rafael Gonçalves <br>
     * Stanford Center for Biomedical Informatics Research
     */
    public static class Builder {
        private IdPrefix classIdPrefix, objectPropertyIdPrefix, dataPropertyIdPrefix, annotationPropertyIdPrefix, individualIdPrefix;

        /**
         * Set the class identifier prefix
         *
         * @param classIdPrefix Class identifier prefix
         * @return Builder
         */
        public Builder setClassIdPrefix(IdPrefix classIdPrefix) {
            this.classIdPrefix = classIdPrefix;
            return this;
        }

        /**
         * Set the object property identifier prefix
         * @param objectPropertyIdPrefix    Object property identifier prefix
         * @return Builder
         */
        public Builder setObjectPropertyIdPrefix(IdPrefix objectPropertyIdPrefix) {
            this.objectPropertyIdPrefix = objectPropertyIdPrefix;
            return this;
        }

        /**
         * Set the data property identifier prefix
         *
         * @param dataPropertyIdPrefix  Data property identifier prefix
         * @return Builder
         */
        public Builder setDataPropertyIdPrefix(IdPrefix dataPropertyIdPrefix) {
            this.dataPropertyIdPrefix = dataPropertyIdPrefix;
            return this;
        }

        /**
         * Set the annotation property identifier prefix
         *
         * @param annotationPropertyIdPrefix    Annotation property identifier prefix
         * @return Builder
         */
        public Builder setAnnotationPropertyIdPrefix(IdPrefix annotationPropertyIdPrefix) {
            this.annotationPropertyIdPrefix = annotationPropertyIdPrefix;
            return this;
        }

        /**
         * Set the individual identifier prefix
         * @param individualIdPrefix    Individual identifier prefix
         * @return Builder
         */
        public Builder setIndividualIdPrefix(IdPrefix individualIdPrefix) {
            this.individualIdPrefix = individualIdPrefix;
            return this;
        }

        /**
         * Create a UUID generator instance
         *
         * @return Instance of PrefixedUUIDGenerator
         */
        public OntologyTermPrefixedUUIDGenerator createPrefixedUUIDGenerator() {
            return new OntologyTermPrefixedUUIDGenerator(classIdPrefix, objectPropertyIdPrefix, dataPropertyIdPrefix, annotationPropertyIdPrefix, individualIdPrefix);
        }
    }
}
