package edu.stanford.protege.metaproject.api.impl;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import edu.stanford.protege.metaproject.api.OperationPrerequisite;
import org.semanticweb.owlapi.model.IRI;

import java.io.Serializable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A representation of an operation prerequisite (e.g., presence or absence of some OWL entity)
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public final class OperationPrerequisiteImpl implements OperationPrerequisite, Serializable {
    private static final long serialVersionUID = -1121403279037406776L;
    private final IRI prerequisite;
    private final Modifier modifier;

    /**
     * Constructor
     *
     * @param prerequisite  Prerequisite OWL entity IRI
     * @param modifier  Operation prerequisite modifier
     */
    public OperationPrerequisiteImpl(IRI prerequisite, Modifier modifier) {
        this.prerequisite = checkNotNull(prerequisite);
        this.modifier = checkNotNull(modifier);
    }

    public IRI getPrerequisiteIRI() {
        return prerequisite;
    }

    public Modifier getModifier() {
        return modifier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OperationPrerequisiteImpl that = (OperationPrerequisiteImpl) o;
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
