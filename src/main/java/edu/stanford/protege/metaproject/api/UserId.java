package edu.stanford.protege.metaproject.api;

/**
 * A representation of a user identifier
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface UserId extends AccessControlObjectId {

    default AccessControlObjectIdType getType() {
        return AccessControlObjectIdType.USER;
    }

}