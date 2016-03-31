package edu.stanford.protege.metaproject.api;

import edu.stanford.protege.metaproject.api.exception.ProjectNotInPolicyException;
import edu.stanford.protege.metaproject.api.exception.UnknownAccessControlObjectIdException;
import edu.stanford.protege.metaproject.api.exception.UserNotInPolicyException;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface PolicyAgent {

    /**
     * Check whether the specified operation is allowed for the given user
     *
     * @param operationId Operation identifier
     * @param projectId   Project identifier
     * @param userId  User identifier
     * @return true if user is allowed to carry out the specified operation, false otherwise
     * @throws UnknownAccessControlObjectIdException Unknown access control object exception
     * @throws UserNotInPolicyException UserId not registered in the access control policy
     * @throws ProjectNotInPolicyException  Project not registered in the access control policy
     */
    boolean isOperationAllowed(OperationId operationId, ProjectId projectId, UserId userId)
            throws UnknownAccessControlObjectIdException, UserNotInPolicyException, ProjectNotInPolicyException;

}
