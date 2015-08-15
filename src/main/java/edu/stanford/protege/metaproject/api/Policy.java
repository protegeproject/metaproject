package edu.stanford.protege.metaproject.api;

import edu.stanford.protege.metaproject.api.exception.PolicyException;

import java.util.Map;
import java.util.Set;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface Policy {

    void addPolicy(User user, Role role) throws PolicyException;

    void addPolicy(Set<User> user, Role role) throws PolicyException;

    void addPolicy(User user, Set<Role> role) throws PolicyException;

    void removePolicy(User user, Role role) throws PolicyException;

    boolean isOperationAllowed(Operation operation, Project project, User user) throws PolicyException;

    boolean hasRole(User user, Role role) throws PolicyException;

    Set<Role> getRoles(User user) throws PolicyException;

    Map<User,Set<Role>> getUserRoleMappings();

}
