package edu.stanford.protege.metaproject.api;

/**
 * A representation of a role identifier
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface RoleId extends AccessControlObjectId {

    default AccessControlObjectIdType getType() {
        return AccessControlObjectIdType.ROLE;
    }

}