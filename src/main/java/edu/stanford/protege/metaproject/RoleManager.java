package edu.stanford.protege.metaproject;

import java.util.Set;

/**
 * A manager for roles played by users
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface RoleManager {

    /**
     * Add the specified role
     *
     * @param role  Role
     */
    void addRole(Role role);

    /**
     * Remove a given role
     *
     * @param role  Role
     */
    void removeRole(Role role);

    /**
     * Get the set of all roles
     *
     * @return Set of existing roles
     */
    Set<Role> getRoles();

    /**
     * Get a role based on its identifier
     *
     * @param roleId    Role identifier
     * @return Role instance
     */
    Role getRole(RoleId roleId);

    /**
     * Change the name of the given role to a new one
     *
     * @param role  Role instance
     * @param roleName  New role name
     */
    void changeRoleName(Role role, RoleName roleName);

    /**
     * Change the description of the given role to a new one
     *
     * @param role  Role instance
     * @param roleDescription   New role description
     */
    void changeRoleDescription(Role role, RoleDescription roleDescription);

}
