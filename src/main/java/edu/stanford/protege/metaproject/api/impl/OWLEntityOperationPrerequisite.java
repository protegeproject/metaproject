package edu.stanford.protege.metaproject.api.impl;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import edu.stanford.protege.metaproject.api.OperationPrerequisite;
import org.semanticweb.owlapi.model.OWLEntity;

import java.io.Serializable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A representation of an operation prerequisite that is an OWL entity
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public final class OWLEntityOperationPrerequisite implements OperationPrerequisite<OWLEntity>, Serializable {
    private static final long serialVersionUID = 2901374432754330453L;
    private final OWLEntity prerequisite;
    private final OperationPrerequisiteModifier modifier;

    /**
     * Constructor
     *
     * @param prerequisite  Operation prerequisite
     * @param modifier  Operation prerequisite modifier
     */
    public OWLEntityOperationPrerequisite(OWLEntity prerequisite, OperationPrerequisiteModifier modifier) {
        this.prerequisite = checkNotNull(prerequisite);
        this.modifier = checkNotNull(modifier);
    }

    public OWLEntity getPrerequisite() {
        return prerequisite;
    }

    public OperationPrerequisiteModifier getModifier() {
        return modifier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OWLEntityOperationPrerequisite that = (OWLEntityOperationPrerequisite) o;
        return Objects.equal(prerequisite, that.prerequisite) &&
                Objects.equal(modifier, that.modifier);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(prerequisite, modifier);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("prerequisite", prerequisite)
                .add("modifier", modifier)
                .toString();
    }
}
