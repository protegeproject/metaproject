package edu.stanford.protege.metaproject.api;

/**
 * A representation of a "metaproject" configuration, which contains a policy that defines what
 * users have what roles on which projects, and the role, user, operation, and project registries.
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface Metaproject {

    /**
     * Get the access control policy, where users get assigned roles within projects
     *
     * @return Policy
     */
    Policy getPolicy();

    /**
     * Get the policy agent that allows one to verify if a user is allowed to do some operation within a project
     *
     * @return Policy agent
     */
    PolicyChecker getPolicyAgent();

    /**
     * Get the role registry, which allows one to create, remove, or modify roles
     *
     * @return Role registry
     */
    RoleRegistry getRoleRegistry();

    /**
     * Get the operation registry
     *
     * @return Operation registry
     */
    OperationRegistry getOperationRegistry();

    /**
     * Get the user registry that is responsible for creating, removing or modifying users
     *
     * @return User registry
     */
    UserRegistry getUserRegistry();

    /**
     * Get the project registry
     *
     * @return Project registry
     */
    ProjectRegistry getProjectRegistry();

}
