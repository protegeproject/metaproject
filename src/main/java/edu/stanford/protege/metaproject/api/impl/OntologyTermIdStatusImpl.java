package edu.stanford.protege.metaproject.api.impl;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import edu.stanford.protege.metaproject.api.IdPrefix;
import edu.stanford.protege.metaproject.api.IdSuffix;
import edu.stanford.protege.metaproject.api.OntologyTermIdStatus;

import java.io.Serializable;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public final class OntologyTermIdStatusImpl implements OntologyTermIdStatus, Serializable {
    private static final long serialVersionUID = -9007514337006498320L;
    private final IdPrefix classIdPrefix, objectPropertyIdPrefix, dataPropertyIdPrefix, annotationPropertyIdPrefix, individualIdPrefix;
    private final IdSuffix classIdSuffix, objectPropertyIdSuffix, dataPropertyIdSuffix, annotationPropertyIdSuffix, individualIdSuffix;

    /**
     * Package-private constructor; use builder
     *
     * @param classIdPrefix Class identifier prefix
     * @param objectPropertyIdPrefix    Object property identifier prefix
     * @param dataPropertyIdPrefix  Data property identifier prefix
     * @param annotationPropertyIdPrefix    Annotation property identifier prefix
     * @param individualIdPrefix    Individual identifier prefix
     * @param classIdSuffix Class identifier suffix
     * @param objectPropertyIdSuffix    Object property identifier suffix
     * @param dataPropertyIdSuffix  Data property identifier suffix
     * @param annotationPropertyIdSuffix    Annotation property identifier suffix
     * @param individualIdSuffix    Individual identifier suffix
     */
    OntologyTermIdStatusImpl(Optional<IdPrefix> classIdPrefix, Optional<IdPrefix> objectPropertyIdPrefix, Optional<IdPrefix> dataPropertyIdPrefix,
                                    Optional<IdPrefix> annotationPropertyIdPrefix, Optional<IdPrefix> individualIdPrefix,
                                    Optional<IdSuffix> classIdSuffix, Optional<IdSuffix> objectPropertyIdSuffix, Optional<IdSuffix> dataPropertyIdSuffix,
                                    Optional<IdSuffix> annotationPropertyIdSuffix, Optional<IdSuffix> individualIdSuffix)
    {
        this.classIdPrefix = (classIdPrefix.isPresent() ? checkNotNull(classIdPrefix.get()) : null);
        this.objectPropertyIdPrefix = (objectPropertyIdPrefix.isPresent() ? checkNotNull(objectPropertyIdPrefix.get()) : null);
        this.dataPropertyIdPrefix = (dataPropertyIdPrefix.isPresent() ? checkNotNull(dataPropertyIdPrefix.get()) : null);
        this.annotationPropertyIdPrefix = (annotationPropertyIdPrefix.isPresent() ? checkNotNull(annotationPropertyIdPrefix.get()) : null);
        this.individualIdPrefix = (individualIdPrefix.isPresent() ? checkNotNull(individualIdPrefix.get()) : null);

        this.classIdSuffix = (classIdSuffix.isPresent() ? checkNotNull(classIdSuffix.get()) : null);
        this.objectPropertyIdSuffix = (objectPropertyIdSuffix.isPresent() ? checkNotNull(objectPropertyIdSuffix.get()) : null);
        this.dataPropertyIdSuffix = (dataPropertyIdSuffix.isPresent() ? checkNotNull(dataPropertyIdSuffix.get()) : null);
        this.annotationPropertyIdSuffix = (annotationPropertyIdSuffix.isPresent() ? checkNotNull(annotationPropertyIdSuffix.get()) : null);
        this.individualIdSuffix = (individualIdSuffix.isPresent() ? checkNotNull(individualIdSuffix.get()) : null);
    }

    @Override
    public Optional<IdPrefix> getClassIdPrefix() {
        return Optional.ofNullable(classIdPrefix);
    }

    @Override
    public Optional<IdPrefix> getObjectPropertyIdPrefix() {
        return Optional.ofNullable(objectPropertyIdPrefix);
    }

    @Override
    public Optional<IdPrefix> getDataPropertyIdPrefix() {
        return Optional.ofNullable(dataPropertyIdPrefix);
    }

    @Override
    public Optional<IdPrefix> getAnnotationPropertyIdPrefix() {
        return Optional.ofNullable(annotationPropertyIdPrefix);
    }

    @Override
    public Optional<IdPrefix> getIndividualIdPrefix() {
        return Optional.ofNullable(individualIdPrefix);
    }

    @Override
    public Optional<IdSuffix> getClassIdSuffix() {
        return Optional.ofNullable(classIdSuffix);
    }

    @Override
    public Optional<IdSuffix> getObjectPropertyIdSuffix() {
        return Optional.ofNullable(objectPropertyIdSuffix);
    }

    @Override
    public Optional<IdSuffix> getDataPropertyIdSuffix() {
        return Optional.ofNullable(dataPropertyIdSuffix);
    }

    @Override
    public Optional<IdSuffix> getAnnotationPropertyIdSuffix() {
        return Optional.ofNullable(annotationPropertyIdSuffix);
    }

    @Override
    public Optional<IdSuffix> getIndividualIdSuffix() {
        return Optional.ofNullable(individualIdSuffix);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OntologyTermIdStatusImpl that = (OntologyTermIdStatusImpl) o;
        return Objects.equal(classIdPrefix, that.classIdPrefix) &&
                Objects.equal(objectPropertyIdPrefix, that.objectPropertyIdPrefix) &&
                Objects.equal(dataPropertyIdPrefix, that.dataPropertyIdPrefix) &&
                Objects.equal(annotationPropertyIdPrefix, that.annotationPropertyIdPrefix) &&
                Objects.equal(individualIdPrefix, that.individualIdPrefix) &&
                Objects.equal(classIdSuffix, that.classIdSuffix) &&
                Objects.equal(objectPropertyIdSuffix, that.objectPropertyIdSuffix) &&
                Objects.equal(dataPropertyIdSuffix, that.dataPropertyIdSuffix) &&
                Objects.equal(annotationPropertyIdSuffix, that.annotationPropertyIdSuffix) &&
                Objects.equal(individualIdSuffix, that.individualIdSuffix);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(classIdPrefix, objectPropertyIdPrefix, dataPropertyIdPrefix, annotationPropertyIdPrefix,
                individualIdPrefix, classIdSuffix, objectPropertyIdSuffix, dataPropertyIdSuffix, annotationPropertyIdSuffix, individualIdSuffix);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("classIdPrefix", classIdPrefix)
                .add("objectPropertyIdPrefix", objectPropertyIdPrefix)
                .add("dataPropertyIdPrefix", dataPropertyIdPrefix)
                .add("annotationPropertyIdPrefix", annotationPropertyIdPrefix)
                .add("individualIdPrefix", individualIdPrefix)
                .add("classIdSuffix", classIdSuffix)
                .add("objectPropertyIdSuffix", objectPropertyIdSuffix)
                .add("dataPropertyIdSuffix", dataPropertyIdSuffix)
                .add("annotationPropertyIdSuffix", annotationPropertyIdSuffix)
                .add("individualIdSuffix", individualIdSuffix)
                .toString();
    }


    /**
     * Builder for ontology term identifier status instances
     */
    public static class Builder {
        private IdSuffix classIdSuffix, objectPropertyIdSuffix, dataPropertyIdSuffix, annotationPropertyIdSuffix, individualIdSuffix;
        private IdPrefix classIdPrefix, objectPropertyIdPrefix, dataPropertyIdPrefix, annotationPropertyIdPrefix, individualIdPrefix;

        public Builder setClassIdPrefix(IdPrefix classIdPrefix) {
            this.classIdPrefix = classIdPrefix;
            return this;
        }

        public Builder setObjectPropertyIdPrefix(IdPrefix objectPropertyIdPrefix) {
            this.objectPropertyIdPrefix = objectPropertyIdPrefix;
            return this;
        }

        public Builder setDataPropertyIdPrefix(IdPrefix dataPropertyIdPrefix) {
            this.dataPropertyIdPrefix = dataPropertyIdPrefix;
            return this;
        }

        public Builder setAnnotationPropertyIdPrefix(IdPrefix annotationPropertyIdPrefix) {
            this.annotationPropertyIdPrefix = annotationPropertyIdPrefix;
            return this;
        }

        public Builder setIndividualIdPrefix(IdPrefix individualIdPrefix) {
            this.individualIdPrefix = individualIdPrefix;
            return this;
        }

        public Builder setClassIdSuffix(IdSuffix classIdSuffix) {
            this.classIdSuffix = classIdSuffix;
            return this;
        }

        public Builder setObjectPropertyIdSuffix(IdSuffix objectPropertyIdSuffix) {
            this.objectPropertyIdSuffix = objectPropertyIdSuffix;
            return this;
        }

        public Builder setDataPropertyIdSuffix(IdSuffix dataPropertyIdSuffix) {
            this.dataPropertyIdSuffix = dataPropertyIdSuffix;
            return this;
        }

        public Builder setAnnotationPropertyIdSuffix(IdSuffix annotationPropertyIdSuffix) {
            this.annotationPropertyIdSuffix = annotationPropertyIdSuffix;
            return this;
        }

        public Builder setIndividualIdSuffix(IdSuffix individualIdSuffix) {
            this.individualIdSuffix = individualIdSuffix;
            return this;
        }

        public OntologyTermIdStatusImpl createOntologyTermIdStatus() {
            return new OntologyTermIdStatusImpl(Optional.ofNullable(classIdPrefix), Optional.ofNullable(objectPropertyIdPrefix), Optional.ofNullable(dataPropertyIdPrefix),
                    Optional.ofNullable(annotationPropertyIdPrefix), Optional.ofNullable(individualIdPrefix), Optional.ofNullable(classIdSuffix), Optional.ofNullable(objectPropertyIdSuffix),
                    Optional.ofNullable(dataPropertyIdSuffix), Optional.ofNullable(annotationPropertyIdSuffix), Optional.ofNullable(individualIdSuffix));
        }
    }
}
