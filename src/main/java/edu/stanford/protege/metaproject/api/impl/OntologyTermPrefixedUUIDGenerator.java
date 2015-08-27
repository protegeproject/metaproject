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
    private final OntologyTermIdPrefix classIdPrefix, objectPropertyIdPrefix, dataPropertyIdPrefix, annotationPropertyIdPrefix, individualIdPrefix;

    /**
     * Package-private constructor; use builder
     *
     * @param classIdPrefix Class identifier prefix
     * @param objectPropertyIdPrefix    Object property identifier prefix
     * @param dataPropertyIdPrefix  Data property identifier prefix
     * @param annotationPropertyIdPrefix    Annotation property identifier prefix
     * @param individualIdPrefix    Individual identifier prefix
     */
    OntologyTermPrefixedUUIDGenerator(OntologyTermIdPrefix classIdPrefix, OntologyTermIdPrefix objectPropertyIdPrefix, OntologyTermIdPrefix dataPropertyIdPrefix,
                                      OntologyTermIdPrefix annotationPropertyIdPrefix, OntologyTermIdPrefix individualIdPrefix) {
        this.classIdPrefix = checkNotNull(classIdPrefix);
        this.objectPropertyIdPrefix = checkNotNull(objectPropertyIdPrefix);
        this.dataPropertyIdPrefix = checkNotNull(dataPropertyIdPrefix);
        this.annotationPropertyIdPrefix = checkNotNull(annotationPropertyIdPrefix);
        this.individualIdPrefix = checkNotNull(individualIdPrefix);
    }

    @Override
    public OntologyTermId getNextClassId() {
        return new OntologyTermIdImpl(classIdPrefix, getIdSuffix());
    }

    @Override
    public OntologyTermId getNextObjectPropertyId() {
        return new OntologyTermIdImpl(objectPropertyIdPrefix, getIdSuffix());
    }

    @Override
    public OntologyTermId getNextDataPropertyId() {
        return new OntologyTermIdImpl(dataPropertyIdPrefix, getIdSuffix());
    }

    @Override
    public OntologyTermId getNextAnnotationPropertyId() {
        return new OntologyTermIdImpl(annotationPropertyIdPrefix, getIdSuffix());
    }

    @Override
    public OntologyTermId getNextIndividualId() {
        return new OntologyTermIdImpl(individualIdPrefix, getIdSuffix());
    }

    /**
     * Convenience method to get a random UUID prefixed with the given term identifier prefix
     *
     * @return String identifier
     */
    private OntologyTermIdSuffix getIdSuffix() {
        return new OntologyTermIdSuffixImpl(UUID.randomUUID().toString());
    }

    @Override
    public OntologyTermIdPrefix getClassIdPrefix() {
        return classIdPrefix;
    }

    @Override
    public OntologyTermIdPrefix getObjectPropertyIdPrefix() {
        return objectPropertyIdPrefix;
    }

    @Override
    public OntologyTermIdPrefix getDataPropertyIdPrefix() {
        return dataPropertyIdPrefix;
    }

    @Override
    public OntologyTermIdPrefix getAnnotationPropertyIdPrefix() {
        return annotationPropertyIdPrefix;
    }

    @Override
    public OntologyTermIdPrefix getIndividualIdPrefix() {
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
        private OntologyTermIdPrefix classIdPrefix, objectPropertyIdPrefix, dataPropertyIdPrefix, annotationPropertyIdPrefix, individualIdPrefix;

        /**
         * Set the class identifier prefix
         *
         * @param classIdPrefix Class identifier prefix
         * @return Builder
         */
        public Builder setClassIdPrefix(OntologyTermIdPrefix classIdPrefix) {
            this.classIdPrefix = classIdPrefix;
            return this;
        }

        /**
         * Set the object property identifier prefix
         * @param objectPropertyIdPrefix    Object property identifier prefix
         * @return Builder
         */
        public Builder setObjectPropertyIdPrefix(OntologyTermIdPrefix objectPropertyIdPrefix) {
            this.objectPropertyIdPrefix = objectPropertyIdPrefix;
            return this;
        }

        /**
         * Set the data property identifier prefix
         *
         * @param dataPropertyIdPrefix  Data property identifier prefix
         * @return Builder
         */
        public Builder setDataPropertyIdPrefix(OntologyTermIdPrefix dataPropertyIdPrefix) {
            this.dataPropertyIdPrefix = dataPropertyIdPrefix;
            return this;
        }

        /**
         * Set the annotation property identifier prefix
         *
         * @param annotationPropertyIdPrefix    Annotation property identifier prefix
         * @return Builder
         */
        public Builder setAnnotationPropertyIdPrefix(OntologyTermIdPrefix annotationPropertyIdPrefix) {
            this.annotationPropertyIdPrefix = annotationPropertyIdPrefix;
            return this;
        }

        /**
         * Set the individual identifier prefix
         * @param individualIdPrefix    Individual identifier prefix
         * @return Builder
         */
        public Builder setIndividualIdPrefix(OntologyTermIdPrefix individualIdPrefix) {
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
