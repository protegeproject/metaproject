package edu.stanford.protege.metaproject.api;

import edu.stanford.protege.metaproject.api.exception.*;

import java.io.File;
import java.util.Map;
import java.util.Set;

/**
 * @author Rafael Gonçalves <br>
 * Center for Biomedical Informatics Research <br>
 * Stanford University
 */
public interface ConfigurationTransformer {

    /* server settings */

    /**
     * Set the server host to the given one
     *
     * @param host  Host
     */
    void setHost(Host host);

    /**
     * Set the server root directory
     *
     * @param root  Root directory
     */
    void setServerRoot(File root);

    /**
     * Set the custom property map in the configuration
     *
     * @param propertyMap   Property map
     */
    void setPropertyMap(Map<String,String> propertyMap);

    /**
     * Add a custom property to the configuration property map
     *
     * @param key   Property key
     * @param value Property value
     */
    void addProperty(String key, String value);

    /**
     * Remove the custom property with the given key from the configuration property map
     *
     * @param key   Property key
     */
    void removeProperty(String key);



    /* users */

    /**
     * Add the given user to the configuration
     *
     * @param user  New user
     * @throws IdAlreadyInUseException  User identifier already in use
     */
    void addUser(User user) throws IdAlreadyInUseException;

    /**
     * Remove the given user from the configuration
     *
     * @param user  User
     */
    void removeUser(User user);

    /**
     * Update the user that has the specified user identifier with the given user
     *
     * @param userId    User identifier
     * @param user  User
     * @throws UnknownUserIdException   User identifier does not exist in the configuration
     */
    void setUser(UserId userId, User user) throws UnknownUserIdException;

    /**
     * Set the set of users in the configuration
     *
     * @param users Set of users
     */
    void setUsers(Set<User> users);

    /**
     * Change the display name of the given user
     *
     * @param userId    User identifier
     * @param userName  New name
     * @throws UnknownUserIdException   User identifier does not exist in the configuration
     */
    void setUserName(UserId userId, Name userName) throws UnknownUserIdException;

    /**
     * Change the email address of a user
     *
     * @param userId  User identifier
     * @param emailAddress New email address
     * @throws UnknownUserIdException   User identifier does not exist in the configuration
     */
    void setUserEmailAddress(UserId userId, EmailAddress emailAddress) throws UnknownUserIdException;



    /* projects */

    /**
     * Add the given project to the configuration
     *
     * @param project   New project
     * @throws IdAlreadyInUseException  Project identifier already in use
     */
    void addProject(Project project) throws IdAlreadyInUseException;

    /**
     * Remove the given project from the configuration
     *
     * @param project   Project
     */
    void removeProject(Project project);

    /**
     * Update the project with the specified project identifier with the given project
     *
     * @param projectId Project identifier
     * @param project   Project
     * @throws UnknownProjectIdException    Project identifier does not exist in the configuration
     */
    void setProject(ProjectId projectId, Project project) throws UnknownProjectIdException;

    /**
     * Set the set of projects in the configuration
     *
     * @param projects  Set of projects
     */
    void setProjects(Set<Project> projects);

    /**
     * Change the name of the given project
     *
     * @param projectId   Project identifier
     * @param projectName   New project name
     * @throws UnknownProjectIdException    Project identifier does not exist in the configuration
     */
    void setProjectName(ProjectId projectId, Name projectName) throws UnknownProjectIdException;

    /**
     * Change the description of the given project
     *
     * @param projectId   Project identifier
     * @param projectDescription    New project description
     * @throws UnknownProjectIdException    Project identifier does not exist in the configuration
     */
    void setProjectDescription(ProjectId projectId, Description projectDescription) throws UnknownProjectIdException;

    /**
     * Change the owner of the specified project
     *
     * @param projectId   Project identifier
     * @param userId    New owner user identifier
     * @throws UnknownProjectIdException    Project identifier does not exist in the configuration
     */
    void setProjectOwner(ProjectId projectId, UserId userId) throws UnknownProjectIdException;

    /**
     * Change the file location of the specified project
     *
     * @param projectId Project identifier
     * @param file   Project file
     * @throws UnknownProjectIdException    Project identifier does not exist in the configuration
     */
    void setProjectFile(ProjectId projectId, File file) throws UnknownProjectIdException;

    /**
     * Set the options for this project
     *
     * @param projectId Project identifier
     * @param projectOptions    Project options
     * @throws UnknownProjectIdException    Project identifier does not exist in the configuration
     */
    void setProjectOptions(ProjectId projectId, ProjectOptions projectOptions) throws UnknownProjectIdException;



    /* roles */

    /**
     * Add the given role to the configuration
     *
     * @param role  New role
     * @throws IdAlreadyInUseException  Role identifier already in use
     */
    void addRole(Role role) throws IdAlreadyInUseException;

    /**
     * Remove the given role from the configuration
     *
     * @param role  Role to be removed
     */
    void removeRole(Role role);

    /**
     * Update the role that has the specified role identifier with the given role
     *
     * @param roleId    Role identifier
     * @param role  Role
     * @throws UnknownRoleIdException   Role identifier does not exist in the configuration
     */
    void setRole(RoleId roleId, Role role) throws UnknownRoleIdException;

    /**
     * Set the set of roles in the configuration
     *
     * @param roles Set of roles
     */
    void setRoles(Set<Role> roles);

    /**
     * Change the name of the given role to a new one
     *
     * @param roleId  Role identifier
     * @param roleName  New role name
     * @throws UnknownRoleIdException   Role identifier does not exist in the configuration
     */
    void setRoleName(RoleId roleId, Name roleName) throws UnknownRoleIdException;

    /**
     * Change the description of the given role to a new one
     *
     * @param roleId  Role identifier
     * @param roleDescription   New role description
     * @throws UnknownRoleIdException   Role identifier does not exist in the configuration
     */
    void setRoleDescription(RoleId roleId, Description roleDescription) throws UnknownRoleIdException;

    /**
     * Add one or more operations to the permitted operations of the given role
     *
     * @param roleId    Role identifier
     * @param operationIds   Operation identifier(s)
     * @throws UnknownRoleIdException   Role identifier does not exist in the configuration
     */
    void addOperationToRole(RoleId roleId, OperationId... operationIds) throws UnknownRoleIdException;

    /**
     * Remove one or more operations from the permitted operations of the given role
     *
     * @param roleId    Role identifier
     * @param operationIds   Operation identifier(s)
     * @throws UnknownRoleIdException   Role identifier does not exist in the configuration
     */
    void removeOperationFromRole(RoleId roleId, OperationId... operationIds) throws UnknownRoleIdException;



    /* operations */

    /**
     * Add the given operation to the configuration
     *
     * @param operation New operation
     * @throws IdAlreadyInUseException  Operation identifier already in use
     */
    void addOperation(Operation operation) throws IdAlreadyInUseException;

    /**
     * Remove the given operation from the configuration
     *
     * @param operation Operation
     */
    void removeOperation(Operation operation);

    /**
     * Update the operation that has the specified identifier with the given operation
     *
     * @param operationId   Operation identifier
     * @param operation Operation
     * @throws UnknownOperationIdException  Operation identifier does not exist in the configuration
     */
    void setOperation(OperationId operationId, Operation operation) throws UnknownOperationIdException;

    /**
     * Set the set of operations in the configuration
     *
     * @param operations    Set of operations
     */
    void setOperations(Set<Operation> operations);

    /**
     * Change the name of the given operation
     *
     * @param operationId Operation identifier
     * @param operationName New operation name
     * @throws UnknownOperationIdException  Operation identifier does not exist in the configuration
     */
    void setOperationName(OperationId operationId, Name operationName) throws UnknownOperationIdException;

    /**
     * Change the description of a given operation
     *
     * @param operationId Operation identifier
     * @param operationDescription  New operation description
     * @throws UnknownOperationIdException  Operation identifier does not exist in the configuration
     */
    void setOperationDescription(OperationId operationId, Description operationDescription) throws UnknownOperationIdException;



   /* authentication */

    /**
     * Register a user's authentication details in the authentication registry
     *
     * @param userId  User identifier
     * @param password  Password
     * @throws IdAlreadyInUseException   User is already registered
     */
    void registerUser(UserId userId, SaltedPasswordDigest password) throws IdAlreadyInUseException;

    /**
     * Remove user from the authentication registry (the user will not be able to login)
     *
     * @param userId  User identifier
     * @throws UserNotRegisteredException   User is not registered
     */
    void unregisterUser(UserId userId) throws UserNotRegisteredException;

    /**
     * Change password of a specified user to the given password
     *
     * @param userId  User identifier
     * @param password  New password
     * @throws UserNotRegisteredException   User is not registered
     */
    void changePassword(UserId userId, SaltedPasswordDigest password) throws UserNotRegisteredException;

    /**
     * Set the authentication details set
     *
     * @param authDetails   Set of authentication details
     */
    void setAuthenticationDetails(Set<AuthenticationDetails> authDetails);



    /* access control policy */

    /**
     * Add a user to the access control policy with the specified role(s) in the given project
     *
     * @param userId    User identifier
     * @param projectId    Project identifier
     * @param roleIds    Role identifier(s)
     */
    void addPolicy(UserId userId, ProjectId projectId, RoleId... roleIds);

    /**
     * Assign a role to one or more users in the access control policy of a specific project
     *
     * @param roleId    Role identifier
     * @param projectId    Project identifier
     * @param userIds   User identifier(s)
     */
    void addPolicy(RoleId roleId, ProjectId projectId, UserId... userIds);

    /**
     * Remove a role from the specified user and project in the access control policy
     *
     * @param userId    User identifier
     * @param projectId    Project identifier
     * @param roleId    Role identifier
     */
    void removePolicy(UserId userId, ProjectId projectId, RoleId roleId);

    /**
     * Remove all role assignments on the specified project by the given user
     *
     * @param userId    User identifier
     * @param projectId Project identifier
     */
    void removePolicy(UserId userId, ProjectId projectId);

    /**
     * Remove all policy entries for the user with the given identifier
     *
     * @param userId User identifier
     */
    void removePolicy(UserId userId);

    /**
     * Remove all users' role assignments involving the role with the specified identifier
     *
     * @param roleId    Role identifier
     */
    void removePolicy(RoleId roleId);

    /**
     * Remove all users' role assignments to the project with the given identifier
     *
     * @param projectId Project identifier
     */
    void removePolicy(ProjectId projectId);

    /**
     * Set the active access control policy
     *
     * @param policyMap Policy map
     */
    void setPolicy(Map<UserId, Map<ProjectId, Set<RoleId>>> policyMap);

}
