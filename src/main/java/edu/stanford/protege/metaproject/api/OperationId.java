package edu.stanford.protege.metaproject.api;

/**
 * A representation of an operation identifier
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface OperationId extends AccessControlObjectId {

    default AccessControlObjectIdType getType() {
        return AccessControlObjectIdType.OPERATION;
    }

}