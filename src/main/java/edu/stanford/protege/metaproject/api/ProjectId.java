package edu.stanford.protege.metaproject.api;

/**
 * A representation of a project identifier
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface ProjectId extends AccessControlObjectId {

    default AccessControlObjectIdType getType() {
        return AccessControlObjectIdType.PROJECT;
    }

}
