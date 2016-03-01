package edu.stanford.protege.metaproject.impl;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import edu.stanford.protege.metaproject.api.ChangeModality;
import edu.stanford.protege.metaproject.api.OperationRestriction;
import org.semanticweb.owlapi.model.AxiomType;

import java.io.Serializable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A representation of an axiom type operation restriction. This can be used to
 * restrict an axiom change to particular axiom types, and furthermore whether
 * the specific axiom type can be added, removed or both.
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public final class AxiomTypeRestriction implements OperationRestriction<AxiomType>, Serializable {
    private static final long serialVersionUID = 1986282697837261393L;
    private final AxiomType axiomType;
    private final ChangeModality modality;

    /**
     * Constructor
     *
     * @param axiomType  Axiom type
     * @param modality  Restriction modality
     */
    public AxiomTypeRestriction(AxiomType axiomType, ChangeModality modality) {
        this.axiomType = checkNotNull(axiomType);
        this.modality = checkNotNull(modality);
    }

    @Override
    public AxiomType getRestriction() {
        return axiomType;
    }

    @Override
    public ChangeModality getModality() {
        return modality;
    }

    @Override
    public boolean isAxiomRestriction() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AxiomTypeRestriction that = (AxiomTypeRestriction) o;
        return Objects.equal(axiomType, that.axiomType) &&
                Objects.equal(modality, that.modality);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(axiomType, modality);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("axiomType", axiomType)
                .add("modality", modality)
                .toString();
    }
}
