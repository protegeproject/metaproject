package edu.stanford.protege.metaproject.api;

import org.semanticweb.owlapi.model.IRI;

import java.util.Optional;
import java.util.Set;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface AccessControlObjectFactory {

    /**
     * Create a new project
     *
     * @param projectId Project identifier
     * @param name  Project name
     * @param description   Project description
     * @param address   Project location
     * @param ownerId   Project owner user identifier
     * @param admins    Set of user identifiers of administrators
     * @return New Project instance
     */
    Project createProject(ProjectId projectId, Name name, Description description, Address address, UserId ownerId, Set<UserId> admins);

    /**
     * Create a new role
     *
     * @param roleId    Role identifier
     * @param name  Role name
     * @param description   Role description
     * @param operations    Set of operations
     * @return New role instance
     */
    Role createRole(RoleId roleId, Name name, Description description, Set<OperationId> operations);

    /**
     * Create a new operation
     *
     * @param operationId   Operation identifier
     * @param name  Operation name
     * @param description   Operation description
     * @param operationType Operation type
     * @param prerequisites Set of operation prerequisites
     * @return New operation instance
     */
    Operation createOperation(OperationId operationId, Name name, Description description, OperationType operationType, Optional<Set<OperationPrerequisite>> prerequisites);

    /**
     * Create a user with the given user identifier, name and email address
     *
     * @param userId    User identifier
     * @param userName  User name
     * @param emailAddress  User email address
     * @return A user instance
     */
    User createUser(UserId userId, Name userName, EmailAddress emailAddress);

    /**
     * Create an operation prerequisite given the prerequisite IRI and the modifier
     *
     * @param prerequisite  Operation prerequisite IRI
     * @param modifier  Prerequisite presence modifier
     * @return Operation prerequisie instance
     */
    OperationPrerequisite createOperationPrerequisite(IRI prerequisite, OperationPrerequisite.Modifier modifier);

    /**
     * Create a name for an access control object
     *
     * @param name  Name
     * @return Name instance
     */
    Name createName(String name);

    /**
     * Create a description for an access control object
     *
     * @param description   Description
     * @return Description instance
     */
    Description createDescription(String description);

    /**
     * Create an address for an access control object
     *
     * @param address   Address
     * @return Address instance
     */
    Address createAddress(String address);

    /**
     * Create an email address name for an access control object
     *
     * @param emailAddress  Email address
     * @return Email address instance
     */
    EmailAddress createEmailAddress(String emailAddress);

    /**
     * Create an entity IRI prefix
     *
     * @param entityIriPrefix   Entity IRI prefix
     * @return Entity IRI prefix
     */
    EntityIriPrefix createEntityIriPrefix(String entityIriPrefix);

    /**
     * Create a suffix for entity names
     *
     * @param suffix    Entity name suffix
     * @return Entity name suffix instance
     */
    EntityNameSuffix createEntityNameSuffix(String suffix);

    /**
     * Create a prefix for entity names
     *
     * @param prefix    Entity name prefix
     * @return Entity name prefix instance
     */
    EntityNamePrefix createEntityNamePrefix(String prefix);

    /**
     * Create an identifier for an access control object
     *
     * @param id    Access control object identifier
     * @param type  Access control object type
     * @return Access control object identifier instance
     * @throws UnknownAccessControlObjectTypeException  Unknown access control object type
     */
    AccessControlObjectId createAccessControlObjectId(String id, AccessControlObjectIdType type) throws UnknownAccessControlObjectTypeException;

    /**
     * Create a new identifier for a role
     *
     * @param roleId    Role identifier
     * @return Role identifier instance
     */
    RoleId createRoleId(String roleId);

    /**
     * Create a new identifier for a user
     *
     * @param userId    User identifier
     * @return User identifier instance
     */
    UserId createUserId(String userId);

    /**
     * Create a new identifier for a project
     *
     * @param projectId Project identifier
     * @return Project identifier instance
     */
    ProjectId createProjectId(String projectId);

    /**
     * Create a new identifier for an operation
     *
     * @param operationId   Operation identifier
     * @return Operation identifier instance
     */
    OperationId createOperationId(String operationId);

}
