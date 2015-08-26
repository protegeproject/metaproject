package edu.stanford.protege.metaproject.api;

import edu.stanford.protege.metaproject.api.exception.RoleNotFoundException;

import java.util.Set;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface RoleManager extends Manager {

    void add(Role... roles);

    void remove(Role... role) throws RoleNotFoundException;

    Set<Role> getRoles();

    Role getRole(RoleId roleId) throws RoleNotFoundException;

    void changeRoleName(RoleId role, Name roleName) throws RoleNotFoundException;

    void changeRoleDescription(RoleId roleId, Description roleDescription) throws RoleNotFoundException;

    void addProject(RoleId roleId, ProjectId projectId) throws RoleNotFoundException;

    void addProjects(RoleId roleId, Set<ProjectId> projectIdSet) throws RoleNotFoundException;

    void removeProject(RoleId roleId, ProjectId project) throws RoleNotFoundException;

    void removeProjects(RoleId roleId, Set<ProjectId> projectIds) throws RoleNotFoundException;

    void addOperation(RoleId roleId, OperationId operationId) throws RoleNotFoundException;

    void addOperations(RoleId roleId, Set<OperationId> operationIds) throws RoleNotFoundException;

    void removeOperation(RoleId roleId, OperationId operationId) throws RoleNotFoundException;

    void removeOperations(RoleId roleId, Set<OperationId> operationIds) throws RoleNotFoundException;

}
