package edu.stanford.protege.metaproject.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A generator for (ascending) sequential identifiers that are prefixed with
 * (optionally) specified prefixes - the default prefix is an empty string
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class SequentialPrefixedIdGenerator extends PrefixedIdGenerator implements TermIdGenerator {
    private TermId classId, objectPropertyId, dataPropertyId, annotationPropertyId, individualId;
    private TermIdPrefix classIdPrefix, objectPropertyIdPrefix, dataPropertyIdPrefix, annotationPropertyIdPrefix, individualIdPrefix;

    // lists of identifiers and prefixes, for convenience of subroutines such as checking prefix clashes
    private List<TermId> identifiers = Arrays.asList(classId, objectPropertyId, dataPropertyId, annotationPropertyId, individualId);
    private List<TermIdPrefix> prefixes = Arrays.asList(
            classIdPrefix, objectPropertyIdPrefix, dataPropertyIdPrefix, annotationPropertyIdPrefix, individualIdPrefix);

    /**
     * Package-private constructor; use builder
     *
     * @param classIdPrefix Class identifier prefix
     * @param objectPropertyIdPrefix    Object property identifier prefix
     * @param dataPropertyIdPrefix  Data property identifier prefix
     * @param annotationPropertyIdPrefix    Annotation property identifier prefix
     * @param individualIdPrefix    Individual identifier prefix
     * @param classId   Class identifier (possibly null)
     * @param objectPropertyId  Object property identifier (possibly null)
     * @param dataPropertyId    Data property identifier (possibly null)
     * @param annotationPropertyId  Annotation property identifier (possibly null)
     * @param individualId  Individual identifier (possibly null)
     */
    SequentialPrefixedIdGenerator(TermIdPrefix classIdPrefix, TermIdPrefix objectPropertyIdPrefix, TermIdPrefix dataPropertyIdPrefix,
                                         TermIdPrefix annotationPropertyIdPrefix, TermIdPrefix individualIdPrefix, TermId classId, TermId objectPropertyId,
                                         TermId dataPropertyId, TermId annotationPropertyId, TermId individualId) {
        this.classIdPrefix = checkNotNull(classIdPrefix);
        this.objectPropertyIdPrefix = checkNotNull(objectPropertyIdPrefix);
        this.dataPropertyIdPrefix = checkNotNull(dataPropertyIdPrefix);
        this.annotationPropertyIdPrefix = checkNotNull(annotationPropertyIdPrefix);
        this.individualIdPrefix = checkNotNull(individualIdPrefix);
        this.classId = classId;
        this.objectPropertyId = objectPropertyId;
        this.dataPropertyId = dataPropertyId;
        this.annotationPropertyId = annotationPropertyId;
        this.individualId = individualId;
    }

    @Override
    public TermId getNextClassId() {
        classId = checkState(classId, classIdPrefix);
        int intId = getIdentifierMinusPrefix(classId, classIdPrefix);
        if(isPrefixClashing(classIdPrefix)) {
            intId = findHighestIdentifier();
        }
        intId++;
        classId = new TermId(classIdPrefix.getPrefix() + intId);
        return classId;
    }

    @Override
    public TermId getNextObjectPropertyId() {
        objectPropertyId = checkState(objectPropertyId, objectPropertyIdPrefix);
        int intId = getIdentifierMinusPrefix(objectPropertyId, objectPropertyIdPrefix);
        if(isPrefixClashing(classIdPrefix)) {
            intId = findHighestIdentifier();
        }
        intId++;
        objectPropertyId = new TermId(objectPropertyIdPrefix.getPrefix() + intId);
        return objectPropertyId;
    }

    @Override
    public TermId getNextDataPropertyId() {
        dataPropertyId = checkState(dataPropertyId, dataPropertyIdPrefix);
        int intId = getIdentifierMinusPrefix(dataPropertyId, dataPropertyIdPrefix);
        if(isPrefixClashing(classIdPrefix)) {
            intId = findHighestIdentifier();
        }
        dataPropertyId = new TermId(dataPropertyIdPrefix.getPrefix() + intId);
        return dataPropertyId;
    }

    @Override
    public TermId getNextAnnotationPropertyId() {
        annotationPropertyId = checkState(annotationPropertyId, annotationPropertyIdPrefix);
        int intId = getIdentifierMinusPrefix(annotationPropertyId, annotationPropertyIdPrefix);
        if(isPrefixClashing(annotationPropertyIdPrefix)) {
            intId = findHighestIdentifier();
        }
        intId++;
        annotationPropertyId = new TermId(annotationPropertyIdPrefix.getPrefix() + intId);
        return annotationPropertyId;
    }

    @Override
    public TermId getNextIndividualId() {
        individualId = checkState(individualId, individualIdPrefix);
        int intId = getIdentifierMinusPrefix(individualId, individualIdPrefix);
        if(isPrefixClashing(classIdPrefix)) {
            intId = findHighestIdentifier();
        }
        intId++;
        individualId = new TermId(individualIdPrefix.getPrefix() + intId);
        return individualId;
    }

    /**
     * Check initialization state of a given term, and if not initialized then set to 0
     *
     * @param termId    Term identifier
     */
    private TermId checkState(TermId termId, TermIdPrefix prefix) {
        if(termId == null) {
            return new TermId(prefix.getPrefix() + 0);
        }
        else {
            return termId;
        }
    }

    /**
     * Get the current (i.e., the last generated) class identifier
     *
     * @return Last generated (class) term identifier
     */
    public TermId getClassId() {
        return classId;
    }

    /**
     * Get the current (i.e., the last generated) object property identifier
     *
     * @return Last generated (object property) term identifier
     */
    public TermId getObjectPropertyId() {
        return objectPropertyId;
    }

    /**
     * Get the current (i.e., the last generated) data property identifier
     *
     * @return Last generated (data property) term identifier
     */
    public TermId getDataPropertyId() {
        return dataPropertyId;
    }

    /**
     * Get the current (i.e., the last generated) annotation property identifier
     *
     * @return Last generated (annotation property) term identifier
     */
    public TermId getAnnotationPropertyId() {
        return annotationPropertyId;
    }

    /**
     * Get the current (i.e., the last generated) individual identifier
     *
     * @return Last generated (individual) term identifier
     */
    public TermId getIndividualId() {
        return individualId;
    }

    /**
     * Verify whether the given prefix exists more than once in the list of OWL entity identifier prefixes,
     * which indicates a clash when generating a term identifier
     *
     * @param prefix    Prefix
     * @return true if given prefix exists more than once, false otherwise
     */
    private boolean isPrefixClashing(TermIdPrefix prefix) {
        List<TermIdPrefix> copy = new ArrayList(prefixes);
        copy.remove(prefix);
        if(copy.contains(prefix)) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Find the highest identifier number among all term identifiers
     *
     * @return Highest identifier number among term identifiers
     */
    private int findHighestIdentifier() {
        int highest = 0;
        for(TermId termId : identifiers) {
            int id = Integer.parseInt(termId.getId());
            if(id > highest) {
                highest = id;
            }
        }
        return highest;
    }

    /**
     * Get the identifier minus the prefix as established in this
     *
     * @param termId    Term identifier
     * @param prefix    Term identifier prefix
     * @return Integer identifier
     */
    private int getIdentifierMinusPrefix(TermId termId, TermIdPrefix prefix) {
        return Integer.parseInt(termId.getId().replace(prefix.getPrefix(),""));
    }

    /**
     * @author Rafael Gonçalves <br>
     * Stanford Center for Biomedical Informatics Research
     */
    public static class SequentialPrefixedIdGeneratorBuilder {
        private TermIdPrefix classIdPrefix;
        private TermIdPrefix objectPropertyIdPrefix;
        private TermIdPrefix dataPropertyIdPrefix;
        private TermIdPrefix annotationPropertyIdPrefix;
        private TermIdPrefix individualIdPrefix;
        private TermId classId;
        private TermId objectPropertyId;
        private TermId dataPropertyId;
        private TermId annotationPropertyId;
        private TermId individualId;

        /**
         * Set the prefix used for class identifiers
         *
         * @param classIdPrefix    Class identifier prefix
         * @return Builder
         */
        public SequentialPrefixedIdGeneratorBuilder setClassIdPrefix(TermIdPrefix classIdPrefix) {
            this.classIdPrefix = classIdPrefix;
            return this;
        }

        /**
         * Set the prefix used for object property identifiers
         *
         * @param objectPropertyIdPrefix    Object property identifier prefix
         * @return Builder
         */
        public SequentialPrefixedIdGeneratorBuilder setObjectPropertyIdPrefix(TermIdPrefix objectPropertyIdPrefix) {
            this.objectPropertyIdPrefix = objectPropertyIdPrefix;
            return this;
        }

        /**
         * Set the prefix used for data property identifiers
         *
         * @param dataPropertyIdPrefix    Data property identifier prefix
         * @return Builder
         */
        public SequentialPrefixedIdGeneratorBuilder setDataPropertyIdPrefix(TermIdPrefix dataPropertyIdPrefix) {
            this.dataPropertyIdPrefix = dataPropertyIdPrefix;
            return this;
        }

        /**
         * Set the prefix used for annotation property identifiers
         *
         * @param annotationPropertyIdPrefix    Annotation property identifier prefix
         * @return Builder
         */
        public SequentialPrefixedIdGeneratorBuilder setAnnotationPropertyIdPrefix(TermIdPrefix annotationPropertyIdPrefix) {
            this.annotationPropertyIdPrefix = annotationPropertyIdPrefix;
            return this;
        }

        /**
         * Set the prefix used for individual identifiers
         *
         * @param individualIdPrefix    Individual identifier prefix
         * @return Builder
         */
        public SequentialPrefixedIdGeneratorBuilder setIndividualIdPrefix(TermIdPrefix individualIdPrefix) {
            this.individualIdPrefix = individualIdPrefix;
            return this;
        }

        /**
         * Specify a starting class identifier, which should be formatted either without a prefix,
         * or with the same prefix that is passed on to the builder
         *
         * @param classId   Class identifier
         * @return Builder
         */
        public SequentialPrefixedIdGeneratorBuilder setClassId(TermId classId) {
            this.classId = classId;
            return this;
        }

        /**
         * Specify a starting object property identifier, which should be formatted either without a prefix,
         * or with the same prefix that is passed on to the builder
         *
         * @param objectPropertyId   Object property identifier
         * @return Builder
         */
        public SequentialPrefixedIdGeneratorBuilder setObjectPropertyId(TermId objectPropertyId) {
            this.objectPropertyId = objectPropertyId;
            return this;
        }

        /**
         * Specify a starting data property identifier, which should be formatted either without a prefix,
         * or with the same prefix that is passed on to the builder
         *
         * @param dataPropertyId   Data property identifier
         * @return Builder
         */
        public SequentialPrefixedIdGeneratorBuilder setDataPropertyId(TermId dataPropertyId) {
            this.dataPropertyId = dataPropertyId;
            return this;
        }

        /**
         * Specify a starting annotation property identifier, which should be formatted either without a prefix,
         * or with the same prefix that is passed on to the builder
         *
         * @param annotationPropertyId   Annotation property identifier
         * @return Builder
         */
        public SequentialPrefixedIdGeneratorBuilder setAnnotationPropertyId(TermId annotationPropertyId) {
            this.annotationPropertyId = annotationPropertyId;
            return this;
        }

        /**
         * Specify a starting individual identifier, which should be formatted either without a prefix,
         * or with the same prefix that is passed on to the builder
         *
         * @param individualId   Individual identifier
         * @return Builder
         */
        public SequentialPrefixedIdGeneratorBuilder setIndividualId(TermId individualId) {
            this.individualId = individualId;
            return this;
        }

        /**
         * Create a generator instance
         *
         * @return Instance of SequentialPrefixedIdGenerator
         */
        public SequentialPrefixedIdGenerator createSequentialPrefixedIdGenerator() {
            return new SequentialPrefixedIdGenerator(classIdPrefix, objectPropertyIdPrefix, dataPropertyIdPrefix,
                    annotationPropertyIdPrefix, individualIdPrefix, classId, objectPropertyId, dataPropertyId, annotationPropertyId, individualId);
        }
    }
}
