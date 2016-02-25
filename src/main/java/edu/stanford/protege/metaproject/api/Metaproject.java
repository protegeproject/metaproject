package edu.stanford.protege.metaproject.api;

/**
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
