package edu.stanford.protege.metaproject.api.impl;

import edu.stanford.protege.metaproject.api.*;

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
public class OntologyTermPrefixedSequentialIdGenerator implements OntologyTermPrefixedIdGenerator {
    private IdSuffix classIdSuffix, objectPropertyIdSuffix, dataPropertyIdSuffix, annotationPropertyIdSuffix, individualIdSuffix;
    private IdPrefix classIdPrefix, objectPropertyIdPrefix, dataPropertyIdPrefix, annotationPropertyIdPrefix, individualIdPrefix;

    // lists of identifiers and prefixes, for convenience of subroutines such as checking prefix clashes
    private List<IdSuffix> identifiers = Arrays.asList(classIdSuffix, objectPropertyIdSuffix, dataPropertyIdSuffix, annotationPropertyIdSuffix, individualIdSuffix);
    private List<IdPrefix> prefixes = Arrays.asList(
            classIdPrefix, objectPropertyIdPrefix, dataPropertyIdPrefix, annotationPropertyIdPrefix, individualIdPrefix);

    /**
     * Package-private constructor; use builder
     *
     * @param classIdPrefix Class identifier prefix
     * @param objectPropertyIdPrefix    Object property identifier prefix
     * @param dataPropertyIdPrefix  Data property identifier prefix
     * @param annotationPropertyIdPrefix    Annotation property identifier prefix
     * @param individualIdPrefix    Individual identifier prefix
     * @param classIdSuffix   Class identifier (possibly null)
     * @param objectPropertyIdSuffix  Object property identifier (possibly null)
     * @param dataPropertyIdSuffix    Data property identifier (possibly null)
     * @param annotationPropertyIdSuffix  Annotation property identifier (possibly null)
     * @param individualIdSuffix  Individual identifier (possibly null)
     */
    OntologyTermPrefixedSequentialIdGenerator(IdPrefix classIdPrefix, IdPrefix objectPropertyIdPrefix, IdPrefix dataPropertyIdPrefix,
                                              IdPrefix annotationPropertyIdPrefix, IdPrefix individualIdPrefix, IdSuffix classIdSuffix, IdSuffix objectPropertyIdSuffix,
                                              IdSuffix dataPropertyIdSuffix, IdSuffix annotationPropertyIdSuffix, IdSuffix individualIdSuffix) {
        this.classIdPrefix = checkNotNull(classIdPrefix);
        this.objectPropertyIdPrefix = checkNotNull(objectPropertyIdPrefix);
        this.dataPropertyIdPrefix = checkNotNull(dataPropertyIdPrefix);
        this.annotationPropertyIdPrefix = checkNotNull(annotationPropertyIdPrefix);
        this.individualIdPrefix = checkNotNull(individualIdPrefix);
        this.classIdSuffix = classIdSuffix;
        this.objectPropertyIdSuffix = objectPropertyIdSuffix;
        this.dataPropertyIdSuffix = dataPropertyIdSuffix;
        this.annotationPropertyIdSuffix = annotationPropertyIdSuffix;
        this.individualIdSuffix = individualIdSuffix;
    }

    @Override
    public Id getNextClassId() {
        classIdSuffix = checkState(classIdSuffix);
        int intId = Integer.parseInt(classIdSuffix.get());
        if(isPrefixClashing(classIdPrefix)) {
            intId = findHighestIdentifier();
        }
        intId++;
        classIdSuffix = new OntologyTermIdSuffix(Integer.toString(intId));
        return getId(classIdPrefix, classIdSuffix);
    }

    @Override
    public Id getNextObjectPropertyId() {
        objectPropertyIdSuffix = checkState(objectPropertyIdSuffix);
        int intId = Integer.parseInt(objectPropertyIdSuffix.get());
        if(isPrefixClashing(classIdPrefix)) {
            intId = findHighestIdentifier();
        }
        intId++;
        objectPropertyIdSuffix = new OntologyTermIdSuffix(Integer.toString(intId));
        return getId(objectPropertyIdPrefix, objectPropertyIdSuffix);
    }

    @Override
    public Id getNextDataPropertyId() {
        dataPropertyIdSuffix = checkState(dataPropertyIdSuffix);
        int intId = Integer.parseInt(dataPropertyIdSuffix.get());
        if (isPrefixClashing(classIdPrefix)) {
            intId = findHighestIdentifier();
        }
        dataPropertyIdSuffix = new OntologyTermIdSuffix(Integer.toString(intId));
        return getId(dataPropertyIdPrefix, dataPropertyIdSuffix);
    }

    @Override
    public Id getNextAnnotationPropertyId() {
        annotationPropertyIdSuffix = checkState(annotationPropertyIdSuffix);
        int intId = Integer.parseInt(annotationPropertyIdSuffix.get());
        if(isPrefixClashing(annotationPropertyIdPrefix)) {
            intId = findHighestIdentifier();
        }
        intId++;
        annotationPropertyIdSuffix = new OntologyTermIdSuffix(Integer.toString(intId));
        return getId(annotationPropertyIdPrefix, annotationPropertyIdSuffix);
    }

    @Override
    public Id getNextIndividualId() {
        individualIdSuffix = checkState(individualIdSuffix);
        int intId = Integer.parseInt(individualIdSuffix.get());
        if(isPrefixClashing(classIdPrefix)) {
            intId = findHighestIdentifier();
        }
        intId++;
        individualIdSuffix = new OntologyTermIdSuffix(Integer.toString(intId));
        return getId(individualIdPrefix, individualIdSuffix);
    }

    /**
     * Convenience method to get an ontology term identifier given its prefix and suffix
     *
     * @param prefix    Ontology term identifier prefix
     * @param suffix    Ontology term identifier suffix
     * @return Ontology term identifier
     */
    private Id getId(IdPrefix prefix, IdSuffix suffix) {
        return new OntologyTermIdImpl(prefix, suffix);
    }


    /**
     * Check initialization state of a given term identifier, and if not initialized then set to 0
     *
     * @param termId    Term identifier
     */
    private IdSuffix checkState(IdSuffix termId) {
        if(termId == null) {
            return new OntologyTermIdSuffix(Integer.toString(0));
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
    private boolean isPrefixClashing(IdPrefix prefix) {
        List<IdPrefix> copy = new ArrayList(prefixes);
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
        for(IdSuffix termId : identifiers) {
            int id = Integer.parseInt(termId.get());
            if(id > highest) {
                highest = id;
            }
        }
        return highest;
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

    /**
     * Get the current (i.e., the last generated) class identifier suffix
     *
     * @return Last generated (class) term identifier suffix
     */
    public IdSuffix getClassIdSuffix() {
        return classIdSuffix;
    }

    /**
     * Get the current (i.e., the last generated) object property identifier suffix
     *
     * @return Last generated (object property) term identifier suffix
     */
    public IdSuffix getObjectPropertyIdSuffix() {
        return objectPropertyIdSuffix;
    }

    /**
     * Get the current (i.e., the last generated) data property identifier suffix
     *
     * @return Last generated (data property) term identifier suffix
     */
    public IdSuffix getDataPropertyIdSuffix() {
        return dataPropertyIdSuffix;
    }

    /**
     * Get the current (i.e., the last generated) annotation property identifier suffix
     *
     * @return Last generated (annotation property) term identifier suffix
     */
    public IdSuffix getAnnotationPropertyIdSuffix() {
        return annotationPropertyIdSuffix;
    }

    /**
     * Get the current (i.e., the last generated) individual identifier suffix
     *
     * @return Last generated (individual) term identifier suffix
     */
    public IdSuffix getIndividualIdSuffix() {
        return individualIdSuffix;
    }


    /**
     * @author Rafael Gonçalves <br>
     * Stanford Center for Biomedical Informatics Research
     */
    public static class SequentialPrefixedIdGeneratorBuilder {
        private IdPrefix classIdPrefix;
        private IdPrefix objectPropertyIdPrefix;
        private IdPrefix dataPropertyIdPrefix;
        private IdPrefix annotationPropertyIdPrefix;
        private IdPrefix individualIdPrefix;
        private IdSuffix classIdSuffix;
        private IdSuffix objectPropertyIdSuffix;
        private IdSuffix dataPropertyIdSuffix;
        private IdSuffix annotationPropertyIdSuffix;
        private IdSuffix individualIdSuffix;

        /**
         * Set the prefix used for class identifiers
         *
         * @param classIdPrefix    Class identifier prefix
         * @return Builder
         */
        public SequentialPrefixedIdGeneratorBuilder setClassIdPrefix(IdPrefix classIdPrefix) {
            this.classIdPrefix = classIdPrefix;
            return this;
        }

        /**
         * Set the prefix used for object property identifiers
         *
         * @param objectPropertyIdPrefix    Object property identifier prefix
         * @return Builder
         */
        public SequentialPrefixedIdGeneratorBuilder setObjectPropertyIdPrefix(IdPrefix objectPropertyIdPrefix) {
            this.objectPropertyIdPrefix = objectPropertyIdPrefix;
            return this;
        }

        /**
         * Set the prefix used for data property identifiers
         *
         * @param dataPropertyIdPrefix    Data property identifier prefix
         * @return Builder
         */
        public SequentialPrefixedIdGeneratorBuilder setDataPropertyIdPrefix(IdPrefix dataPropertyIdPrefix) {
            this.dataPropertyIdPrefix = dataPropertyIdPrefix;
            return this;
        }

        /**
         * Set the prefix used for annotation property identifiers
         *
         * @param annotationPropertyIdPrefix    Annotation property identifier prefix
         * @return Builder
         */
        public SequentialPrefixedIdGeneratorBuilder setAnnotationPropertyIdPrefix(IdPrefix annotationPropertyIdPrefix) {
            this.annotationPropertyIdPrefix = annotationPropertyIdPrefix;
            return this;
        }

        /**
         * Set the prefix used for individual identifiers
         *
         * @param individualIdPrefix    Individual identifier prefix
         * @return Builder
         */
        public SequentialPrefixedIdGeneratorBuilder setIndividualIdPrefix(IdPrefix individualIdPrefix) {
            this.individualIdPrefix = individualIdPrefix;
            return this;
        }

        /**
         * Specify a starting class identifier suffix
         *
         * @param classIdSuffix   Class identifier suffix
         * @return Builder
         */
        public SequentialPrefixedIdGeneratorBuilder setClassIdSuffix(IdSuffix classIdSuffix) {
            this.classIdSuffix = classIdSuffix;
            return this;
        }

        /**
         * Specify a starting object property identifier suffix
         *
         * @param objectPropertyIdSuffix   Object property identifier suffix
         * @return Builder
         */
        public SequentialPrefixedIdGeneratorBuilder setObjectPropertyIdSuffix(IdSuffix objectPropertyIdSuffix) {
            this.objectPropertyIdSuffix = objectPropertyIdSuffix;
            return this;
        }

        /**
         * Specify a starting data property identifier suffix
         *
         * @param dataPropertyIdSuffix   Data property identifier suffix
         * @return Builder
         */
        public SequentialPrefixedIdGeneratorBuilder setDataPropertyIdSuffix(IdSuffix dataPropertyIdSuffix) {
            this.dataPropertyIdSuffix = dataPropertyIdSuffix;
            return this;
        }

        /**
         * Specify a starting annotation property identifier suffix
         *
         * @param annotationPropertyIdSuffix   Annotation property identifier suffix
         * @return Builder
         */
        public SequentialPrefixedIdGeneratorBuilder setAnnotationPropertyIdSuffix(IdSuffix annotationPropertyIdSuffix) {
            this.annotationPropertyIdSuffix = annotationPropertyIdSuffix;
            return this;
        }

        /**
         * Specify a starting individual identifier suffix
         *
         * @param individualIdSuffix   Individual identifier suffix
         * @return Builder
         */
        public SequentialPrefixedIdGeneratorBuilder setIndividualIdSuffix(IdSuffix individualIdSuffix) {
            this.individualIdSuffix = individualIdSuffix;
            return this;
        }

        /**
         * Create a generator instance
         *
         * @return Instance of SequentialPrefixedIdGenerator
         */
        public OntologyTermPrefixedSequentialIdGenerator createSequentialPrefixedIdGenerator() {
            return new OntologyTermPrefixedSequentialIdGenerator(classIdPrefix, objectPropertyIdPrefix, dataPropertyIdPrefix,
                    annotationPropertyIdPrefix, individualIdPrefix, classIdSuffix, objectPropertyIdSuffix, dataPropertyIdSuffix, annotationPropertyIdSuffix, individualIdSuffix);
        }
    }
}
