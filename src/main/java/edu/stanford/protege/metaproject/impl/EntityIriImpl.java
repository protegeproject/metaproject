package edu.stanford.protege.metaproject.impl;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import edu.stanford.protege.metaproject.api.EntityIri;
import edu.stanford.protege.metaproject.api.EntityIriPrefix;
import edu.stanford.protege.metaproject.api.EntityName;
import org.semanticweb.owlapi.model.IRI;

import java.io.Serializable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public final class EntityIriImpl implements EntityIri, Serializable {
    private static final long serialVersionUID = 3003022371186987293L;
    private final EntityIriPrefix prefix;
    private final EntityName name;

    /**
     * Constructor
     *
     * @param prefix    Entity IRI prefix
     * @param name    Entity name
     */
    public EntityIriImpl(EntityIriPrefix prefix, EntityName name) {
        this.prefix = checkNotNull(prefix);
        this.name = checkNotNull(name);
    }

    @Override
    public IRI get() {
        return IRI.create(prefix.get(), name.get());
    }

    @Override
    public EntityIriPrefix getIriPrefix() {
        return prefix;
    }

    @Override
    public EntityName getEntityName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityIriImpl that = (EntityIriImpl) o;
        return Objects.equal(prefix, that.prefix) &&
                Objects.equal(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(prefix, name);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("prefix", prefix)
                .add("name", name)
                .toString();
    }
}
