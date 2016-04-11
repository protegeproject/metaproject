package edu.stanford.protege.metaproject.api;

/**
 * Marker interface for a metaproject object such as user, role, project, etc.
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface MetaprojectObject<T extends MetaprojectObjectId> extends HasId<T> {

}
