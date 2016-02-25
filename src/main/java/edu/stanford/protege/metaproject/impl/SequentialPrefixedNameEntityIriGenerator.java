package edu.stanford.protege.metaproject.impl;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import edu.stanford.protege.metaproject.api.*;
import edu.stanford.protege.metaproject.api.exception.MalformedInputException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A generator for (ascending) sequential suffixes that are prefixed with
 * (optionally) specified prefixes - the default prefix is an empty string
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class SequentialPrefixedNameEntityIriGenerator implements PrefixedNameEntityIriGenerator {
    private EntityNameSuffix classNameSuffix, objectPropertyNameSuffix, dataPropertyNameSuffix, annotationPropertyNameSuffix, individualNameSuffix;
    private EntityNamePrefix classNamePrefix, objectPropertyNamePrefix, dataPropertyNamePrefix, annotationPropertyNamePrefix, individualNamePrefix;
    private EntityIriPrefix iriPrefix;

    // lists of suffixes and prefixes, for convenience of subroutines such as checking clashes
    private List<EntityNameSuffix> suffixes = Arrays.asList(classNameSuffix, objectPropertyNameSuffix, dataPropertyNameSuffix, annotationPropertyNameSuffix, individualNameSuffix);
    private List<EntityNamePrefix> prefixes = Arrays.asList(classNamePrefix, objectPropertyNamePrefix, dataPropertyNamePrefix, annotationPropertyNamePrefix, individualNamePrefix);

    /**
     * Package-private constructor; use builder
     *
     * @param iriPrefix Entiry IRI prefix
     * @param classNamePrefix Class name prefix
     * @param objectPropertyNamePrefix    Object property name prefix
     * @param dataPropertyNamePrefix  Data property name prefix
     * @param annotationPropertyNamePrefix    Annotation property name prefix
     * @param individualNamePrefix    Individual name prefix
     * @param classNameSuffix   Class name suffix
     * @param objectPropertyNameSuffix  Object property name suffix
     * @param dataPropertyNameSuffix    Data property name suffix
     * @param annotationPropertyNameSuffix  Annotation property name suffix
     * @param individualNameSuffix  Individual name suffix
     */
    SequentialPrefixedNameEntityIriGenerator(EntityIriPrefix iriPrefix, EntityNamePrefix classNamePrefix, EntityNamePrefix objectPropertyNamePrefix, EntityNamePrefix dataPropertyNamePrefix,
                                             EntityNamePrefix annotationPropertyNamePrefix, EntityNamePrefix individualNamePrefix, Optional<EntityNameSuffix> classNameSuffix,
                                             Optional<EntityNameSuffix> objectPropertyNameSuffix, Optional<EntityNameSuffix> dataPropertyNameSuffix,
                                             Optional<EntityNameSuffix> annotationPropertyNameSuffix, Optional<EntityNameSuffix> individualNameSuffix)
    {
        this.iriPrefix = checkNotNull(iriPrefix);
        this.classNamePrefix = checkNotNull(classNamePrefix);
        this.objectPropertyNamePrefix = checkNotNull(objectPropertyNamePrefix);
        this.dataPropertyNamePrefix = checkNotNull(dataPropertyNamePrefix);
        this.annotationPropertyNamePrefix = checkNotNull(annotationPropertyNamePrefix);
        this.individualNamePrefix = checkNotNull(individualNamePrefix);

        this.classNameSuffix = (classNameSuffix.isPresent() ? checkFormat(checkNotNull(classNameSuffix.get())) : null);
        this.objectPropertyNameSuffix = (objectPropertyNameSuffix.isPresent() ? checkFormat(checkNotNull(objectPropertyNameSuffix.get())) : null);
        this.dataPropertyNameSuffix = (dataPropertyNameSuffix.isPresent() ? checkFormat(checkNotNull(dataPropertyNameSuffix.get())) : null);
        this.annotationPropertyNameSuffix = (annotationPropertyNameSuffix.isPresent() ? checkFormat(checkNotNull(annotationPropertyNameSuffix.get())) : null);
        this.individualNameSuffix = (individualNameSuffix.isPresent() ? checkFormat(checkNotNull(individualNameSuffix.get())) : null);
    }

    /**
     * Verify that the given suffix is well formatted, i.e., a positive integer
     *
     * @param suffix    Entity name suffix
     * @return Entity name suffix reference
     * @throws MalformedInputException  if the name suffix is not a positive integer
     */
    private EntityNameSuffix checkFormat(EntityNameSuffix suffix) throws MalformedInputException {
        if(!isPositiveInteger(suffix.get())) {
            throw new MalformedInputException("The name suffix must be a positive integer (supplied value: " + suffix.get() + ")");
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
    public EntityIriPrefix getEntityIriPrefix() {
        return iriPrefix;
    }

    @Override
    public EntityIri getNextClassIri() {
        classNameSuffix = checkState(classNameSuffix);
        int intName = Integer.parseInt(classNameSuffix.get());
        if(isPrefixClashing(classNamePrefix)) {
            intName = findHighestEntityNameSuffix();
        }
        intName++;
        classNameSuffix = new EntityNameSuffixImpl(Integer.toString(intName));
        return new EntityIriImpl(iriPrefix, getName(classNamePrefix, classNameSuffix));
    }

    @Override
    public EntityIri getNextObjectPropertyIri() {
        objectPropertyNameSuffix = checkState(objectPropertyNameSuffix);
        int intName = Integer.parseInt(objectPropertyNameSuffix.get());
        if(isPrefixClashing(classNamePrefix)) {
            intName = findHighestEntityNameSuffix();
        }
        intName++;
        objectPropertyNameSuffix = new EntityNameSuffixImpl(Integer.toString(intName));
        return new EntityIriImpl(iriPrefix, getName(objectPropertyNamePrefix, objectPropertyNameSuffix));
    }

    @Override
    public EntityIri getNextDataPropertyIri() {
        dataPropertyNameSuffix = checkState(dataPropertyNameSuffix);
        int intName = Integer.parseInt(dataPropertyNameSuffix.get());
        if(isPrefixClashing(classNamePrefix)) {
            intName = findHighestEntityNameSuffix();
        }
        intName++;
        dataPropertyNameSuffix = new EntityNameSuffixImpl(Integer.toString(intName));
        return new EntityIriImpl(iriPrefix, getName(dataPropertyNamePrefix, dataPropertyNameSuffix));
    }

    @Override
    public EntityIri getNextAnnotationPropertyIri() {
        annotationPropertyNameSuffix = checkState(annotationPropertyNameSuffix);
        int intName = Integer.parseInt(annotationPropertyNameSuffix.get());
        if(isPrefixClashing(annotationPropertyNamePrefix)) {
            intName = findHighestEntityNameSuffix();
        }
        intName++;
        annotationPropertyNameSuffix = new EntityNameSuffixImpl(Integer.toString(intName));
        return new EntityIriImpl(iriPrefix, getName(annotationPropertyNamePrefix, annotationPropertyNameSuffix));
    }

    @Override
    public EntityIri getNextIndividualIri() {
        individualNameSuffix = checkState(individualNameSuffix);
        int intName = Integer.parseInt(individualNameSuffix.get());
        if(isPrefixClashing(classNamePrefix)) {
            intName = findHighestEntityNameSuffix();
        }
        intName++;
        individualNameSuffix = new EntityNameSuffixImpl(Integer.toString(intName));
        return new EntityIriImpl(iriPrefix, getName(individualNamePrefix, individualNameSuffix));
    }

    /**
     * Convenience method to get an entity name given a prefix and suffix
     *
     * @param prefix    Entity  name prefix
     * @param suffix    Entity name suffix
     * @return Entity name
     */
    private EntityName getName(EntityNamePrefix prefix, EntityNameSuffix suffix) {
        return new EntityNameImpl(prefix, suffix);
    }


    /**
     * Check initialization state of a given term name; if not initialized then set to 0
     *
     * @param termName    Term name
     */
    private EntityNameSuffix checkState(EntityNameSuffix termName) {
        if(termName == null) {
            return new EntityNameSuffixImpl(Integer.toString(0));
        }
        else {
            return termName;
        }
    }

    /**
     * Verify whether the given prefix exists more than once in the list of OWL entity name prefixes,
     * which indicates a clash when generating a term name
     *
     * @param prefix    Prefix
     * @return true if given prefix exists more than once, false otherwise
     */
    private boolean isPrefixClashing(EntityNamePrefix prefix) {
        List<EntityNamePrefix> copy = new ArrayList<>(prefixes);
        copy.remove(prefix);
        return copy.contains(prefix);
    }

    /**
     * Find the highest entity name suffix among all name suffixes
     *
     * @return Highest entity name suffix among name suffixes
     */
    private int findHighestEntityNameSuffix() {
        int highest = 0;
        for(EntityNameSuffix nameSuffix : suffixes) {
            int id = Integer.parseInt(nameSuffix.get());
            if(id > highest) {
                highest = id;
            }
        }
        return highest;
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

    /**
     * Get the current (i.e., the last generated) class name suffix
     *
     * @return Last generated (class) term name suffix
     */
    public EntityNameSuffix getClassNameSuffix() {
        return classNameSuffix;
    }

    /**
     * Get the current (i.e., the last generated) object property name suffix
     *
     * @return Last generated (object property) term name suffix
     */
    public EntityNameSuffix getObjectPropertyNameSuffix() {
        return objectPropertyNameSuffix;
    }

    /**
     * Get the current (i.e., the last generated) data property name suffix
     *
     * @return Last generated (data property) term name suffix
     */
    public EntityNameSuffix getDataPropertyNameSuffix() {
        return dataPropertyNameSuffix;
    }

    /**
     * Get the current (i.e., the last generated) annotation property name suffix
     *
     * @return Last generated (annotation property) term name suffix
     */
    public EntityNameSuffix getAnnotationPropertyNameSuffix() {
        return annotationPropertyNameSuffix;
    }

    /**
     * Get the current (i.e., the last generated) individual name suffix
     *
     * @return Last generated (individual) term name suffix
     */
    public EntityNameSuffix getIndividualNameSuffix() {
        return individualNameSuffix;
    }

    @Override
    public Optional<EntityIriStatus> getEntityIriStatus() {
        return Optional.of(
                new EntityIriStatusImpl.Builder()
                        .setEntityIriPrefix(iriPrefix)
                        .setClassNamePrefix(classNamePrefix)
                        .setObjectPropertyNamePrefix(objectPropertyNamePrefix)
                        .setDataPropertyNamePrefix(dataPropertyNamePrefix)
                        .setAnnotationPropertyNamePrefix(annotationPropertyNamePrefix)
                        .setIndividualNamePrefix(individualNamePrefix)
                        .setClassNameSuffix(classNameSuffix)
                        .setObjectPropertyNameSuffix(objectPropertyNameSuffix)
                        .setDataPropertyNameSuffix(dataPropertyNameSuffix)
                        .setAnnotationPropertyNameSuffix(annotationPropertyNameSuffix)
                        .setIndividualNameSuffix(individualNameSuffix)
                        .createEntityIriStatus()
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SequentialPrefixedNameEntityIriGenerator that = (SequentialPrefixedNameEntityIriGenerator) o;
        return Objects.equal(classNameSuffix, that.classNameSuffix) &&
                Objects.equal(objectPropertyNameSuffix, that.objectPropertyNameSuffix) &&
                Objects.equal(dataPropertyNameSuffix, that.dataPropertyNameSuffix) &&
                Objects.equal(annotationPropertyNameSuffix, that.annotationPropertyNameSuffix) &&
                Objects.equal(individualNameSuffix, that.individualNameSuffix) &&
                Objects.equal(classNamePrefix, that.classNamePrefix) &&
                Objects.equal(objectPropertyNamePrefix, that.objectPropertyNamePrefix) &&
                Objects.equal(dataPropertyNamePrefix, that.dataPropertyNamePrefix) &&
                Objects.equal(annotationPropertyNamePrefix, that.annotationPropertyNamePrefix) &&
                Objects.equal(individualNamePrefix, that.individualNamePrefix) &&
                Objects.equal(iriPrefix, that.iriPrefix) &&
                Objects.equal(suffixes, that.suffixes) &&
                Objects.equal(prefixes, that.prefixes);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(classNameSuffix, objectPropertyNameSuffix, dataPropertyNameSuffix, annotationPropertyNameSuffix,
                individualNameSuffix, classNamePrefix, objectPropertyNamePrefix, dataPropertyNamePrefix, annotationPropertyNamePrefix,
                individualNamePrefix, iriPrefix, suffixes, prefixes);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("classNameSuffix", classNameSuffix)
                .add("objectPropertyNameSuffix", objectPropertyNameSuffix)
                .add("dataPropertyNameSuffix", dataPropertyNameSuffix)
                .add("annotationPropertyNameSuffix", annotationPropertyNameSuffix)
                .add("individualNameSuffix", individualNameSuffix)
                .add("classNamePrefix", classNamePrefix)
                .add("objectPropertyNamePrefix", objectPropertyNamePrefix)
                .add("dataPropertyNamePrefix", dataPropertyNamePrefix)
                .add("annotationPropertyNamePrefix", annotationPropertyNamePrefix)
                .add("individualNamePrefix", individualNamePrefix)
                .add("iriPrefix", iriPrefix)
                .add("suffixes", suffixes)
                .add("prefixes", prefixes)
                .toString();
    }

    /**
     * Builder for generator of entity IRIs with the given IRI prefix, and with prefixed names and sequential name suffixes
     */
    public static class Builder {
        private EntityIriPrefix iriPrefix;
        private EntityNameSuffix classNameSuffix, objectPropertyNameSuffix, dataPropertyNameSuffix, annotationPropertyNameSuffix, individualNameSuffix;
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
         * Set the prefix used for class suffixes
         *
         * @param classNamePrefix    Class name prefix
         * @return Builder
         */
        public Builder setClassNamePrefix(EntityNamePrefix classNamePrefix) {
            this.classNamePrefix = classNamePrefix;
            return this;
        }

        /**
         * Set the prefix used for object property suffixes
         *
         * @param objectPropertyNamePrefix    Object property name prefix
         * @return Builder
         */
        public Builder setObjectPropertyNamePrefix(EntityNamePrefix objectPropertyNamePrefix) {
            this.objectPropertyNamePrefix = objectPropertyNamePrefix;
            return this;
        }

        /**
         * Set the prefix used for data property suffixes
         *
         * @param dataPropertyNamePrefix    Data property name prefix
         * @return Builder
         */
        public Builder setDataPropertyNamePrefix(EntityNamePrefix dataPropertyNamePrefix) {
            this.dataPropertyNamePrefix = dataPropertyNamePrefix;
            return this;
        }

        /**
         * Set the prefix used for annotation property suffixes
         *
         * @param annotationPropertyNamePrefix    Annotation property name prefix
         * @return Builder
         */
        public Builder setAnnotationPropertyNamePrefix(EntityNamePrefix annotationPropertyNamePrefix) {
            this.annotationPropertyNamePrefix = annotationPropertyNamePrefix;
            return this;
        }

        /**
         * Set the prefix used for individual suffixes
         *
         * @param individualNamePrefix    Individual name prefix
         * @return Builder
         */
        public Builder setIndividualNamePrefix(EntityNamePrefix individualNamePrefix) {
            this.individualNamePrefix = individualNamePrefix;
            return this;
        }

        /**
         * Specify a starting class name suffix
         *
         * @param classNameSuffix   Class name suffix
         * @return Builder
         */
        public Builder setClassNameSuffix(EntityNameSuffix classNameSuffix) {
            this.classNameSuffix = classNameSuffix;
            return this;
        }

        /**
         * Specify a starting object property name suffix
         *
         * @param objectPropertyNameSuffix   Object property name suffix
         * @return Builder
         */
        public Builder setObjectPropertyNameSuffix(EntityNameSuffix objectPropertyNameSuffix) {
            this.objectPropertyNameSuffix = objectPropertyNameSuffix;
            return this;
        }

        /**
         * Specify a starting data property name suffix
         *
         * @param dataPropertyNameSuffix   Data property name suffix
         * @return Builder
         */
        public Builder setDataPropertyNameSuffix(EntityNameSuffix dataPropertyNameSuffix) {
            this.dataPropertyNameSuffix = dataPropertyNameSuffix;
            return this;
        }

        /**
         * Specify a starting annotation property name suffix
         *
         * @param annotationPropertyNameSuffix   Annotation property name suffix
         * @return Builder
         */
        public Builder setAnnotationPropertyNameSuffix(EntityNameSuffix annotationPropertyNameSuffix) {
            this.annotationPropertyNameSuffix = annotationPropertyNameSuffix;
            return this;
        }

        /**
         * Specify a starting individual name suffix
         *
         * @param individualNameSuffix   Individual name suffix
         * @return Builder
         */
        public Builder setIndividualNameSuffix(EntityNameSuffix individualNameSuffix) {
            this.individualNameSuffix = individualNameSuffix;
            return this;
        }

        /**
         * Create a generator instance
         *
         * @return Instance of SequentialPrefixedNameEntityIriGenerator
         */
        public SequentialPrefixedNameEntityIriGenerator createSequentialPrefixedNameEntityIriGenerator() {
            return new SequentialPrefixedNameEntityIriGenerator(iriPrefix, classNamePrefix, objectPropertyNamePrefix, dataPropertyNamePrefix,
                    annotationPropertyNamePrefix, individualNamePrefix, Optional.ofNullable(classNameSuffix),
                    Optional.ofNullable(objectPropertyNameSuffix), Optional.ofNullable(dataPropertyNameSuffix),
                    Optional.ofNullable(annotationPropertyNameSuffix), Optional.ofNullable(individualNameSuffix));
        }
    }
}
