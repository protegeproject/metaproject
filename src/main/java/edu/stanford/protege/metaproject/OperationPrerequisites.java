package edu.stanford.protege.metaproject;

/**
 * Requisites for an operation (e.g., existence of some class)
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface OperationPrerequisites {

    /**
     * Get operation requisites
     *
     * @return Operation requisites
     */
    Object getRequisites();

}
