package edu.stanford.protege.metaproject.api;

import org.semanticweb.owlapi.model.IRI;

/**
 * A representation of an ontology entity IRI, composed of the IRI prefix and the entity name
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface EntityIri {

    /**
     * Get the full IRI of the entity
     *
     * @return IRI
     */
    IRI get();

    /**
     * Get the entity IRI prefix
     *
     * @return Entity IRI prefix
     */
    EntityIriPrefix getIriPrefix();

    /**
     * Get the entity name
     *
     * @return Entity name
     */
    EntityName getEntityName();

}
