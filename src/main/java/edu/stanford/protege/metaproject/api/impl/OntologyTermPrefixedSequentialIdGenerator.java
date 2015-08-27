package edu.stanford.protege.metaproject.api.impl;

import edu.stanford.protege.metaproject.api.*;
import edu.stanford.protege.metaproject.api.exception.MalformedInputException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A generator for (ascending) sequential identifiers that are prefixed with
 * (optionally) specified prefixes - the default prefix is an empty string
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class OntologyTermPrefixedSequentialIdGenerator implements OntologyTermPrefixedIdGenerator {
    private OntologyTermIdSuffix classIdSuffix, objectPropertyIdSuffix, dataPropertyIdSuffix, annotationPropertyIdSuffix, individualIdSuffix;
    private OntologyTermIdPrefix classIdPrefix, objectPropertyIdPrefix, dataPropertyIdPrefix, annotationPropertyIdPrefix, individualIdPrefix;

    // lists of identifiers and prefixes, for convenience of subroutines such as checking prefix clashes
    private List<OntologyTermIdSuffix> identifiers = Arrays.asList(classIdSuffix, objectPropertyIdSuffix, dataPropertyIdSuffix, annotationPropertyIdSuffix, individualIdSuffix);
    private List<OntologyTermIdPrefix> prefixes = Arrays.asList(classIdPrefix, objectPropertyIdPrefix, dataPropertyIdPrefix, annotationPropertyIdPrefix, individualIdPrefix);

    /**
     * Package-private constructor; use builder
     *
     * @param classIdPrefix Class identifier prefix
     * @param objectPropertyIdPrefix    Object property identifier prefix
     * @param dataPropertyIdPrefix  Data property identifier prefix
     * @param annotationPropertyIdPrefix    Annotation property identifier prefix
     * @param individualIdPrefix    Individual identifier prefix
     * @param classIdSuffix   Class identifier suffix
     * @param objectPropertyIdSuffix  Object property identifier suffix
     * @param dataPropertyIdSuffix    Data property identifier suffix
     * @param annotationPropertyIdSuffix  Annotation property identifier suffix
     * @param individualIdSuffix  Individual identifier suffix
     */
    OntologyTermPrefixedSequentialIdGenerator(OntologyTermIdPrefix classIdPrefix, OntologyTermIdPrefix objectPropertyIdPrefix, OntologyTermIdPrefix dataPropertyIdPrefix, OntologyTermIdPrefix annotationPropertyIdPrefix,
                                              OntologyTermIdPrefix individualIdPrefix, Optional<OntologyTermIdSuffix> classIdSuffix, Optional<OntologyTermIdSuffix> objectPropertyIdSuffix,
                                              Optional<OntologyTermIdSuffix> dataPropertyIdSuffix, Optional<OntologyTermIdSuffix> annotationPropertyIdSuffix, Optional<OntologyTermIdSuffix> individualIdSuffix)
    {
        this.classIdPrefix = checkNotNull(classIdPrefix);
        this.objectPropertyIdPrefix = checkNotNull(objectPropertyIdPrefix);
        this.dataPropertyIdPrefix = checkNotNull(dataPropertyIdPrefix);
        this.annotationPropertyIdPrefix = checkNotNull(annotationPropertyIdPrefix);
        this.individualIdPrefix = checkNotNull(individualIdPrefix);

        this.classIdSuffix = (classIdSuffix.isPresent() ? checkFormat(checkNotNull(classIdSuffix.get())) : null);
        this.objectPropertyIdSuffix = (objectPropertyIdSuffix.isPresent() ? checkFormat(checkNotNull(objectPropertyIdSuffix.get())) : null);
        this.dataPropertyIdSuffix = (dataPropertyIdSuffix.isPresent() ? checkFormat(checkNotNull(dataPropertyIdSuffix.get())) : null);
        this.annotationPropertyIdSuffix = (annotationPropertyIdSuffix.isPresent() ? checkFormat(checkNotNull(annotationPropertyIdSuffix.get())) : null);
        this.individualIdSuffix = (individualIdSuffix.isPresent() ? checkFormat(checkNotNull(individualIdSuffix.get())) : null);
    }

    /**
     * Verify that the given suffix is well formatted
     *
     * @param suffix    Identifier suffix reference
     * @return Suffix reference
     * @throws MalformedInputException  if the identifier suffix is not a positive integer
     */
    private OntologyTermIdSuffix checkFormat(OntologyTermIdSuffix suffix) throws MalformedInputException {
        if(!isPositiveInteger(suffix.get())) {
            throw new MalformedInputException("The identifier suffix must be a positive integer (supplied value: " + suffix.get() + ")");
        }
        return suffix;
    }

    /**
     * Check whether the given string represents a positive integer
     *
     * @param str   String
     * @return true if string represents is a positive integer, false otherwise
     */
    private boolean isPositiveInteger(String str) {
        int length = str.length(), i = 0;
        if(str.equals("") || length == 0) {
            return false;
        }
        if(str.charAt(0) == '-') {
            return false;
        }
        for(; i < length; i++) {
            char c = str.charAt(i);
            if(c <= '/' || c >= ':') {
                return false;
            }
        }
        return true;
    }

    @Override
    public OntologyTermId getNextClassId() {
        classIdSuffix = checkState(classIdSuffix);
        int intId = Integer.parseInt(classIdSuffix.get());
        if(isPrefixClashing(classIdPrefix)) {
            intId = findHighestIdentifier();
        }
        intId++;
        classIdSuffix = new OntologyTermIdSuffixImpl(Integer.toString(intId));
        return getId(classIdPrefix, classIdSuffix);
    }

    @Override
    public OntologyTermId getNextObjectPropertyId() {
        objectPropertyIdSuffix = checkState(objectPropertyIdSuffix);
        int intId = Integer.parseInt(objectPropertyIdSuffix.get());
        if(isPrefixClashing(classIdPrefix)) {
            intId = findHighestIdentifier();
        }
        intId++;
        objectPropertyIdSuffix = new OntologyTermIdSuffixImpl(Integer.toString(intId));
        return getId(objectPropertyIdPrefix, objectPropertyIdSuffix);
    }

    @Override
    public OntologyTermId getNextDataPropertyId() {
        dataPropertyIdSuffix = checkState(dataPropertyIdSuffix);
        int intId = Integer.parseInt(dataPropertyIdSuffix.get());
        if(isPrefixClashing(classIdPrefix)) {
            intId = findHighestIdentifier();
        }
        intId++;
        dataPropertyIdSuffix = new OntologyTermIdSuffixImpl(Integer.toString(intId));
        return getId(dataPropertyIdPrefix, dataPropertyIdSuffix);
    }

    @Override
    public OntologyTermId getNextAnnotationPropertyId() {
        annotationPropertyIdSuffix = checkState(annotationPropertyIdSuffix);
        int intId = Integer.parseInt(annotationPropertyIdSuffix.get());
        if(isPrefixClashing(annotationPropertyIdPrefix)) {
            intId = findHighestIdentifier();
        }
        intId++;
        annotationPropertyIdSuffix = new OntologyTermIdSuffixImpl(Integer.toString(intId));
        return getId(annotationPropertyIdPrefix, annotationPropertyIdSuffix);
    }

    @Override
    public OntologyTermId getNextIndividualId() {
        individualIdSuffix = checkState(individualIdSuffix);
        int intId = Integer.parseInt(individualIdSuffix.get());
        if(isPrefixClashing(classIdPrefix)) {
            intId = findHighestIdentifier();
        }
        intId++;
        individualIdSuffix = new OntologyTermIdSuffixImpl(Integer.toString(intId));
        return getId(individualIdPrefix, individualIdSuffix);
    }

    /**
     * Convenience method to get an ontology term identifier given its prefix and suffix
     *
     * @param prefix    Ontology term identifier prefix
     * @param suffix    Ontology term identifier suffix
     * @return Ontology term identifier
     */
    private OntologyTermId getId(OntologyTermIdPrefix prefix, OntologyTermIdSuffix suffix) {
        return new OntologyTermIdImpl(prefix, suffix);
    }


    /**
     * Check initialization state of a given term identifier, and if not initialized then set to 0
     *
     * @param termId    Term identifier
     */
    private OntologyTermIdSuffix checkState(OntologyTermIdSuffix termId) {
        if(termId == null) {
            return new OntologyTermIdSuffixImpl(Integer.toString(0));
        }
        else {
            return termId;
        }
    }

    /**
     * Verify whether the given prefix exists more than once in the list of OWL entity identifier prefixes,
     * which indicates a clash when generating a term identifier
     *
     * @param prefix    Prefix
     * @return true if given prefix exists more than once, false otherwise
     */
    private boolean isPrefixClashing(OntologyTermIdPrefix prefix) {
        List<OntologyTermIdPrefix> copy = new ArrayList<>(prefixes);
        copy.remove(prefix);
        return copy.contains(prefix);
    }

    /**
     * Find the highest identifier number among all term identifiers
     *
     * @return Highest identifier number among term identifiers
     */
    private int findHighestIdentifier() {
        int highest = 0;
        for(OntologyTermIdSuffix termId : identifiers) {
            int id = Integer.parseInt(termId.get());
            if(id > highest) {
                highest = id;
            }
        }
        return highest;
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

    /**
     * Get the current (i.e., the last generated) class identifier suffix
     *
     * @return Last generated (class) term identifier suffix
     */
    public OntologyTermIdSuffix getClassIdSuffix() {
        return classIdSuffix;
    }

    /**
     * Get the current (i.e., the last generated) object property identifier suffix
     *
     * @return Last generated (object property) term identifier suffix
     */
    public OntologyTermIdSuffix getObjectPropertyIdSuffix() {
        return objectPropertyIdSuffix;
    }

    /**
     * Get the current (i.e., the last generated) data property identifier suffix
     *
     * @return Last generated (data property) term identifier suffix
     */
    public OntologyTermIdSuffix getDataPropertyIdSuffix() {
        return dataPropertyIdSuffix;
    }

    /**
     * Get the current (i.e., the last generated) annotation property identifier suffix
     *
     * @return Last generated (annotation property) term identifier suffix
     */
    public OntologyTermIdSuffix getAnnotationPropertyIdSuffix() {
        return annotationPropertyIdSuffix;
    }

    /**
     * Get the current (i.e., the last generated) individual identifier suffix
     *
     * @return Last generated (individual) term identifier suffix
     */
    public OntologyTermIdSuffix getIndividualIdSuffix() {
        return individualIdSuffix;
    }

    @Override
    public Optional<OntologyTermIdStatus> getCurrentOntologyTermIdStatus() {
        return Optional.of(
                new OntologyTermIdStatusImpl.Builder()
                        .setClassIdPrefix(classIdPrefix)
                        .setObjectPropertyIdPrefix(objectPropertyIdPrefix)
                        .setDataPropertyIdPrefix(dataPropertyIdPrefix)
                        .setAnnotationPropertyIdPrefix(annotationPropertyIdPrefix)
                        .setIndividualIdPrefix(individualIdPrefix)
                        .setClassIdSuffix(classIdSuffix)
                        .setObjectPropertyIdSuffix(objectPropertyIdSuffix)
                        .setDataPropertyIdSuffix(dataPropertyIdSuffix)
                        .setAnnotationPropertyIdSuffix(annotationPropertyIdSuffix)
                        .setIndividualIdSuffix(individualIdSuffix)
                        .createOntologyTermIdStatus()
        );
    }

    /**
     * @author Rafael Gonçalves <br>
     * Stanford Center for Biomedical Informatics Research
     */
    public static class Builder {
        private OntologyTermIdSuffix classIdSuffix, objectPropertyIdSuffix, dataPropertyIdSuffix, annotationPropertyIdSuffix, individualIdSuffix;
        private OntologyTermIdPrefix classIdPrefix, objectPropertyIdPrefix, dataPropertyIdPrefix, annotationPropertyIdPrefix, individualIdPrefix;

        /**
         * Set the prefix used for class identifiers
         *
         * @param classIdPrefix    Class identifier prefix
         * @return Builder
         */
        public Builder setClassIdPrefix(OntologyTermIdPrefix classIdPrefix) {
            this.classIdPrefix = classIdPrefix;
            return this;
        }

        /**
         * Set the prefix used for object property identifiers
         *
         * @param objectPropertyIdPrefix    Object property identifier prefix
         * @return Builder
         */
        public Builder setObjectPropertyIdPrefix(OntologyTermIdPrefix objectPropertyIdPrefix) {
            this.objectPropertyIdPrefix = objectPropertyIdPrefix;
            return this;
        }

        /**
         * Set the prefix used for data property identifiers
         *
         * @param dataPropertyIdPrefix    Data property identifier prefix
         * @return Builder
         */
        public Builder setDataPropertyIdPrefix(OntologyTermIdPrefix dataPropertyIdPrefix) {
            this.dataPropertyIdPrefix = dataPropertyIdPrefix;
            return this;
        }

        /**
         * Set the prefix used for annotation property identifiers
         *
         * @param annotationPropertyIdPrefix    Annotation property identifier prefix
         * @return Builder
         */
        public Builder setAnnotationPropertyIdPrefix(OntologyTermIdPrefix annotationPropertyIdPrefix) {
            this.annotationPropertyIdPrefix = annotationPropertyIdPrefix;
            return this;
        }

        /**
         * Set the prefix used for individual identifiers
         *
         * @param individualIdPrefix    Individual identifier prefix
         * @return Builder
         */
        public Builder setIndividualIdPrefix(OntologyTermIdPrefix individualIdPrefix) {
            this.individualIdPrefix = individualIdPrefix;
            return this;
        }

        /**
         * Specify a starting class identifier suffix
         *
         * @param classIdSuffix   Class identifier suffix
         * @return Builder
         */
        public Builder setClassIdSuffix(OntologyTermIdSuffix classIdSuffix) {
            this.classIdSuffix = classIdSuffix;
            return this;
        }

        /**
         * Specify a starting object property identifier suffix
         *
         * @param objectPropertyIdSuffix   Object property identifier suffix
         * @return Builder
         */
        public Builder setObjectPropertyIdSuffix(OntologyTermIdSuffix objectPropertyIdSuffix) {
            this.objectPropertyIdSuffix = objectPropertyIdSuffix;
            return this;
        }

        /**
         * Specify a starting data property identifier suffix
         *
         * @param dataPropertyIdSuffix   Data property identifier suffix
         * @return Builder
         */
        public Builder setDataPropertyIdSuffix(OntologyTermIdSuffix dataPropertyIdSuffix) {
            this.dataPropertyIdSuffix = dataPropertyIdSuffix;
            return this;
        }

        /**
         * Specify a starting annotation property identifier suffix
         *
         * @param annotationPropertyIdSuffix   Annotation property identifier suffix
         * @return Builder
         */
        public Builder setAnnotationPropertyIdSuffix(OntologyTermIdSuffix annotationPropertyIdSuffix) {
            this.annotationPropertyIdSuffix = annotationPropertyIdSuffix;
            return this;
        }

        /**
         * Specify a starting individual identifier suffix
         *
         * @param individualIdSuffix   Individual identifier suffix
         * @return Builder
         */
        public Builder setIndividualIdSuffix(OntologyTermIdSuffix individualIdSuffix) {
            this.individualIdSuffix = individualIdSuffix;
            return this;
        }

        /**
         * Create a generator instance
         *
         * @return Instance of SequentialPrefixedIdGenerator
         */
        public OntologyTermPrefixedSequentialIdGenerator createSequentialPrefixedIdGenerator() {
            return new OntologyTermPrefixedSequentialIdGenerator(classIdPrefix, objectPropertyIdPrefix, dataPropertyIdPrefix, annotationPropertyIdPrefix, individualIdPrefix,
                    Optional.ofNullable(classIdSuffix), Optional.ofNullable(objectPropertyIdSuffix), Optional.ofNullable(dataPropertyIdSuffix),
                    Optional.ofNullable(annotationPropertyIdSuffix), Optional.ofNullable(individualIdSuffix));
        }
    }
}
