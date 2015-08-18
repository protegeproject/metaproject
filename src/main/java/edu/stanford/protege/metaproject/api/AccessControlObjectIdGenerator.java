package edu.stanford.protege.metaproject.api;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface AccessControlObjectIdGenerator extends IdGenerator {

    UserId getUserId();

    RoleId getRoleId();

    ProjectId getProjectId();

    OperationId getOperationId();

}
