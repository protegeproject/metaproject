package edu.stanford.protege.metaproject;

import java.util.Map;
import java.util.Set;

/**
 * A policy associates users and roles
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface Policy {

    /**
     * Get the identifier of the policy
     *
     * @return Policy identifier instance
     */
    PolicyId getId();

    /**
     * Get the description of the policy
     *
     * @return Policy description instance
     */
    String getDescription();

    /**
     * Get the set of all users in the user-role registry
     *
     * @return Set of users
     */
    Set<User> getUsers();

    /**
     * Check if a given user has the specified role
     *
     * @param user  User
     * @param role  Role
     * @return true if user has specified role, false otherwise
     */
    boolean hasRole(User user, Role role);

    /**
     * Get the set of all roles associated with a user
     *
     * @param user  User
     * @return Set of roles the user has
     */
    Set<Role> getRoles(User user);

    /**
     * Get a map of users with their corresponding roles
     *
     * @return Map of users to their roles
     */
    Map<User,Set<Role>> getUserRoleMappings();

}
