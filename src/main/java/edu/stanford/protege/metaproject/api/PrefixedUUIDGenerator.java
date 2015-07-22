package edu.stanford.protege.metaproject.api;

import java.util.UUID;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A generator of prefixed universally unique identifiers
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public final class PrefixedUUIDGenerator extends PrefixedIdGenerator implements TermIdGenerator {
    private final TermIdPrefix classIdPrefix, objectPropertyIdPrefix, dataPropertyIdPrefix, annotationPropertyIdPrefix, individualIdPrefix;

    /**
     * Package-private constructor; use builder
     *
     * @param classIdPrefix Class identifier prefix
     * @param objectPropertyIdPrefix    Object property identifier prefix
     * @param dataPropertyIdPrefix  Data property identifier prefix
     * @param annotationPropertyIdPrefix    Annotation property identifier prefix
     * @param individualIdPrefix    Individual identifier prefix
     */
    PrefixedUUIDGenerator(TermIdPrefix classIdPrefix, TermIdPrefix objectPropertyIdPrefix, TermIdPrefix dataPropertyIdPrefix,
                          TermIdPrefix annotationPropertyIdPrefix, TermIdPrefix individualIdPrefix) {
        this.classIdPrefix = checkNotNull(classIdPrefix);
        this.objectPropertyIdPrefix = checkNotNull(objectPropertyIdPrefix);
        this.dataPropertyIdPrefix = checkNotNull(dataPropertyIdPrefix);
        this.annotationPropertyIdPrefix = checkNotNull(annotationPropertyIdPrefix);
        this.individualIdPrefix = checkNotNull(individualIdPrefix);
    }

    @Override
    public TermId getNextClassId() {
        return new TermId(getId(classIdPrefix));
    }

    @Override
    public TermId getNextObjectPropertyId() {
        return new TermId(getId(objectPropertyIdPrefix));
    }

    @Override
    public TermId getNextDataPropertyId() {
        return new TermId(getId(dataPropertyIdPrefix));
    }

    @Override
    public TermId getNextAnnotationPropertyId() {
        return new TermId(getId(annotationPropertyIdPrefix));
    }

    @Override
    public TermId getNextIndividualId() {
        return new TermId(getId(individualIdPrefix));
    }

    /**
     * Convenience method to get a random UUID prefixed with the given term identifier prefix
     *
     * @param prefix    Term identifier prefix
     * @return String identifier
     */
    private String getId(TermIdPrefix prefix) {
        return prefix.getPrefix() + UUID.randomUUID().toString();
    }

    /**
     * @author Rafael Gonçalves <br>
     * Stanford Center for Biomedical Informatics Research
     */
    public static class PrefixedUUIDGeneratorBuilder {
        private TermIdPrefix classIdPrefix;
        private TermIdPrefix objectPropertyIdPrefix;
        private TermIdPrefix dataPropertyIdPrefix;
        private TermIdPrefix annotationPropertyIdPrefix;
        private TermIdPrefix individualIdPrefix;

        /**
         * Set the class identifier prefix
         *
         * @param classIdPrefix Class identifier prefix
         * @return Builder
         */
        public PrefixedUUIDGeneratorBuilder setClassIdPrefix(TermIdPrefix classIdPrefix) {
            this.classIdPrefix = classIdPrefix;
            return this;
        }

        /**
         * Set the object property identifier prefix
         * @param objectPropertyIdPrefix    Object property identifier prefix
         * @return Builder
         */
        public PrefixedUUIDGeneratorBuilder setObjectPropertyIdPrefix(TermIdPrefix objectPropertyIdPrefix) {
            this.objectPropertyIdPrefix = objectPropertyIdPrefix;
            return this;
        }

        /**
         * Set the data property identifier prefix
         *
         * @param dataPropertyIdPrefix  Data property identifier prefix
         * @return Builder
         */
        public PrefixedUUIDGeneratorBuilder setDataPropertyIdPrefix(TermIdPrefix dataPropertyIdPrefix) {
            this.dataPropertyIdPrefix = dataPropertyIdPrefix;
            return this;
        }

        /**
         * Set the annotation property identifier prefix
         *
         * @param annotationPropertyIdPrefix    Annotation property identifier prefix
         * @return Builder
         */
        public PrefixedUUIDGeneratorBuilder setAnnotationPropertyIdPrefix(TermIdPrefix annotationPropertyIdPrefix) {
            this.annotationPropertyIdPrefix = annotationPropertyIdPrefix;
            return this;
        }

        /**
         * Set the individual identifier prefix
         * @param individualIdPrefix    Individual identifier prefix
         * @return Builder
         */
        public PrefixedUUIDGeneratorBuilder setIndividualIdPrefix(TermIdPrefix individualIdPrefix) {
            this.individualIdPrefix = individualIdPrefix;
            return this;
        }

        /**
         * Create a UUID generator instance
         *
         * @return Instance of PrefixedUUIDGenerator
         */
        public PrefixedUUIDGenerator createPrefixedUUIDGenerator() {
            return new PrefixedUUIDGenerator(classIdPrefix, objectPropertyIdPrefix, dataPropertyIdPrefix, annotationPropertyIdPrefix, individualIdPrefix);
        }
    }
}
