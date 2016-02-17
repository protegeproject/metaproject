package edu.stanford.protege.metaproject.impl;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import edu.stanford.protege.metaproject.api.EntityIriPrefix;
import edu.stanford.protege.metaproject.api.EntityIriStatus;
import edu.stanford.protege.metaproject.api.EntityNamePrefix;
import edu.stanford.protege.metaproject.api.EntityNameSuffix;

import java.io.Serializable;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public final class EntityIriStatusImpl implements EntityIriStatus, Serializable {
    private static final long serialVersionUID = -2628232781208856743L;
    private final EntityIriPrefix iriPrefix;
    private final EntityNamePrefix classNamePrefix, objectPropertyNamePrefix, dataPropertyNamePrefix, annotationPropertyNamePrefix, individualNamePrefix;
    private final EntityNameSuffix classNameSuffix, objectPropertyNameSuffix, dataPropertyNameSuffix, annotationPropertyNameSuffix, individualNameSuffix;

    /**
     * Package-private constructor; use builder
     *
     * @param iriPrefix Entity IRI prefix
     * @param classNamePrefix Class name prefix
     * @param objectPropertyNamePrefix    Object property name prefix
     * @param dataPropertyNamePrefix  Data property name prefix
     * @param annotationPropertyNamePrefix    Annotation property name prefix
     * @param individualNamePrefix    Individual name prefix
     * @param classNameSuffix Class name suffix
     * @param objectPropertyNameSuffix    Object property name suffix
     * @param dataPropertyNameSuffix  Data property name suffix
     * @param annotationPropertyNameSuffix    Annotation property name suffix
     * @param individualNameSuffix    Individual name suffix
     */
    EntityIriStatusImpl(EntityIriPrefix iriPrefix, Optional<EntityNamePrefix> classNamePrefix, Optional<EntityNamePrefix> objectPropertyNamePrefix,
                        Optional<EntityNamePrefix> dataPropertyNamePrefix, Optional<EntityNamePrefix> annotationPropertyNamePrefix, Optional<EntityNamePrefix> individualNamePrefix,
                        Optional<EntityNameSuffix> classNameSuffix, Optional<EntityNameSuffix> objectPropertyNameSuffix, Optional<EntityNameSuffix> dataPropertyNameSuffix,
                        Optional<EntityNameSuffix> annotationPropertyNameSuffix, Optional<EntityNameSuffix> individualNameSuffix)
    {
        this.iriPrefix = checkNotNull(iriPrefix);
        this.classNamePrefix = (classNamePrefix.isPresent() ? checkNotNull(classNamePrefix.get()) : null);
        this.objectPropertyNamePrefix = (objectPropertyNamePrefix.isPresent() ? checkNotNull(objectPropertyNamePrefix.get()) : null);
        this.dataPropertyNamePrefix = (dataPropertyNamePrefix.isPresent() ? checkNotNull(dataPropertyNamePrefix.get()) : null);
        this.annotationPropertyNamePrefix = (annotationPropertyNamePrefix.isPresent() ? checkNotNull(annotationPropertyNamePrefix.get()) : null);
        this.individualNamePrefix = (individualNamePrefix.isPresent() ? checkNotNull(individualNamePrefix.get()) : null);

        this.classNameSuffix = (classNameSuffix.isPresent() ? checkNotNull(classNameSuffix.get()) : null);
        this.objectPropertyNameSuffix = (objectPropertyNameSuffix.isPresent() ? checkNotNull(objectPropertyNameSuffix.get()) : null);
        this.dataPropertyNameSuffix = (dataPropertyNameSuffix.isPresent() ? checkNotNull(dataPropertyNameSuffix.get()) : null);
        this.annotationPropertyNameSuffix = (annotationPropertyNameSuffix.isPresent() ? checkNotNull(annotationPropertyNameSuffix.get()) : null);
        this.individualNameSuffix = (individualNameSuffix.isPresent() ? checkNotNull(individualNameSuffix.get()) : null);
    }

    @Override
    public EntityIriPrefix getEntityIriPrefix() {
        return iriPrefix;
    }

    @Override
    public Optional<EntityNamePrefix> getClassNamePrefix() {
        return Optional.ofNullable(classNamePrefix);
    }

    @Override
    public Optional<EntityNamePrefix> getObjectPropertyNamePrefix() {
        return Optional.ofNullable(objectPropertyNamePrefix);
    }

    @Override
    public Optional<EntityNamePrefix> getDataPropertyNamePrefix() {
        return Optional.ofNullable(dataPropertyNamePrefix);
    }

    @Override
    public Optional<EntityNamePrefix> getAnnotationPropertyNamePrefix() {
        return Optional.ofNullable(annotationPropertyNamePrefix);
    }

    @Override
    public Optional<EntityNamePrefix> getIndividualNamePrefix() {
        return Optional.ofNullable(individualNamePrefix);
    }

    @Override
    public Optional<EntityNameSuffix> getClassNameSuffix() {
        return Optional.ofNullable(classNameSuffix);
    }

    @Override
    public Optional<EntityNameSuffix> getObjectPropertyNameSuffix() {
        return Optional.ofNullable(objectPropertyNameSuffix);
    }

    @Override
    public Optional<EntityNameSuffix> getDataPropertyNameSuffix() {
        return Optional.ofNullable(dataPropertyNameSuffix);
    }

    @Override
    public Optional<EntityNameSuffix> getAnnotationPropertyNameSuffix() {
        return Optional.ofNullable(annotationPropertyNameSuffix);
    }

    @Override
    public Optional<EntityNameSuffix> getIndividualNameSuffix() {
        return Optional.ofNullable(individualNameSuffix);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityIriStatusImpl that = (EntityIriStatusImpl) o;
        return Objects.equal(iriPrefix, that.iriPrefix) &&
                Objects.equal(classNamePrefix, that.classNamePrefix) &&
                Objects.equal(objectPropertyNamePrefix, that.objectPropertyNamePrefix) &&
                Objects.equal(dataPropertyNamePrefix, that.dataPropertyNamePrefix) &&
                Objects.equal(annotationPropertyNamePrefix, that.annotationPropertyNamePrefix) &&
                Objects.equal(individualNamePrefix, that.individualNamePrefix) &&
                Objects.equal(classNameSuffix, that.classNameSuffix) &&
                Objects.equal(objectPropertyNameSuffix, that.objectPropertyNameSuffix) &&
                Objects.equal(dataPropertyNameSuffix, that.dataPropertyNameSuffix) &&
                Objects.equal(annotationPropertyNameSuffix, that.annotationPropertyNameSuffix) &&
                Objects.equal(individualNameSuffix, that.individualNameSuffix);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(iriPrefix, classNamePrefix, objectPropertyNamePrefix, dataPropertyNamePrefix, annotationPropertyNamePrefix,
                individualNamePrefix, classNameSuffix, objectPropertyNameSuffix, dataPropertyNameSuffix, annotationPropertyNameSuffix, individualNameSuffix);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("iriPrefix", iriPrefix)
                .add("classNamePrefix", classNamePrefix)
                .add("objectPropertyNamePrefix", objectPropertyNamePrefix)
                .add("dataPropertyNamePrefix", dataPropertyNamePrefix)
                .add("annotationPropertyNamePrefix", annotationPropertyNamePrefix)
                .add("individualNamePrefix", individualNamePrefix)
                .add("classNameSuffix", classNameSuffix)
                .add("objectPropertyNameSuffix", objectPropertyNameSuffix)
                .add("dataPropertyNameSuffix", dataPropertyNameSuffix)
                .add("annotationPropertyNameSuffix", annotationPropertyNameSuffix)
                .add("individualNameSuffix", individualNameSuffix)
                .toString();
    }

    /**
     * Builder for entity IRI status instances
     */
    public static class Builder {
        private EntityIriPrefix iriPrefix;
        private EntityNamePrefix classNamePrefix, objectPropertyNamePrefix, dataPropertyNamePrefix, annotationPropertyNamePrefix, individualNamePrefix;
        private EntityNameSuffix classNameSuffix, objectPropertyNameSuffix, dataPropertyNameSuffix, annotationPropertyNameSuffix, individualNameSuffix;

        public Builder setEntityIriPrefix(EntityIriPrefix iriPrefix) {
            this.iriPrefix = iriPrefix;
            return this;
        }

        public Builder setClassNamePrefix(EntityNamePrefix classNamePrefix) {
            this.classNamePrefix = classNamePrefix;
            return this;
        }

        public Builder setObjectPropertyNamePrefix(EntityNamePrefix objectPropertyNamePrefix) {
            this.objectPropertyNamePrefix = objectPropertyNamePrefix;
            return this;
        }

        public Builder setDataPropertyNamePrefix(EntityNamePrefix dataPropertyNamePrefix) {
            this.dataPropertyNamePrefix = dataPropertyNamePrefix;
            return this;
        }

        public Builder setAnnotationPropertyNamePrefix(EntityNamePrefix annotationPropertyNamePrefix) {
            this.annotationPropertyNamePrefix = annotationPropertyNamePrefix;
            return this;
        }

        public Builder setIndividualNamePrefix(EntityNamePrefix individualNamePrefix) {
            this.individualNamePrefix = individualNamePrefix;
            return this;
        }

        public Builder setClassNameSuffix(EntityNameSuffix classNameSuffix) {
            this.classNameSuffix = classNameSuffix;
            return this;
        }

        public Builder setObjectPropertyNameSuffix(EntityNameSuffix objectPropertyNameSuffix) {
            this.objectPropertyNameSuffix = objectPropertyNameSuffix;
            return this;
        }

        public Builder setDataPropertyNameSuffix(EntityNameSuffix dataPropertyNameSuffix) {
            this.dataPropertyNameSuffix = dataPropertyNameSuffix;
            return this;
        }

        public Builder setAnnotationPropertyNameSuffix(EntityNameSuffix annotationPropertyNameSuffix) {
            this.annotationPropertyNameSuffix = annotationPropertyNameSuffix;
            return this;
        }

        public Builder setIndividualNameSuffix(EntityNameSuffix individualNameSuffix) {
            this.individualNameSuffix = individualNameSuffix;
            return this;
        }

        public EntityIriStatusImpl createEntityIriStatus() {
            return new EntityIriStatusImpl(iriPrefix, Optional.ofNullable(classNamePrefix), Optional.ofNullable(objectPropertyNamePrefix), Optional.ofNullable(dataPropertyNamePrefix),
                    Optional.ofNullable(annotationPropertyNamePrefix), Optional.ofNullable(individualNamePrefix), Optional.ofNullable(classNameSuffix), Optional.ofNullable(objectPropertyNameSuffix),
                    Optional.ofNullable(dataPropertyNameSuffix), Optional.ofNullable(annotationPropertyNameSuffix), Optional.ofNullable(individualNameSuffix));
        }
    }
}
