package edu.stanford.protege.metaproject.api;

import edu.stanford.protege.metaproject.api.impl.*;
import org.semanticweb.owlapi.model.IRI;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class TestUtils {

    /*   identifiers   */

    public static UserId getUserId(String userId) {
        return new UserIdImpl(userId);
    }

    public static RoleId getRoleId(String roleId) {
        return new RoleIdImpl(roleId);
    }

    public static ProjectId getProjectId(String projectId) {
        return new ProjectIdImpl(projectId);
    }

    public static OperationId getOperationId(String operationId) {
        return new OperationIdImpl(operationId);
    }

    /*   basic elements   */

    public static Name getName(String name) {
        return new NameImpl(name);
    }

    public static Description getDescription(String description) {
        return new DescriptionImpl(description);
    }

    public static Address getAddress(String address) {
        return new AddressImpl(address);
    }

    public static OperationPrerequisite getOperationPrerequisite(IRI iri, OperationPrerequisite.Modifier modifier) {
        return new OperationPrerequisiteImpl(iri, modifier);
    }

    public static OperationPrerequisite getOperationPrerequisite(OperationPrerequisite.Modifier modifier) {
        return getOperationPrerequisite(getIRI(), modifier);
    }

    public static IRI getIRI(String iri) {
        return IRI.create(iri);
    }

    public static IRI getIRI() {
        return IRI.create("http://protege.stanford.edu/test/" + newUUID());
    }

    public static String newUUID() {
        return UUID.randomUUID().toString();
    }

    public static SaltedPassword getSaltedPassword(String password, Salt salt) {
        return new SaltedPasswordImpl(password, salt);
    }

    public static Salt getSalt(byte[] bytes) {
        return new SaltImpl(bytes);
    }

    public static UserAuthenticationDetails getUserAuthenticationDetails(UserId userId, SaltedPassword password, Optional<Salt> salt) {
        return new UserAuthenticationDetailsImpl(userId, password, salt);
    }

    public static OntologyTermIdPrefix getOntologyTermIdPrefix(String prefix) {
        return new OntologyTermIdPrefixImpl(prefix);
    }

    public static OntologyTermIdSuffix getOntologyTermIdSuffix(String suffix) {
        return new OntologyTermIdSuffixImpl(suffix);
    }

    /*   access control policy objects   */

    public static Project getProject(ProjectId id, Name name, Description description, Address address, UserId owner, Set<UserId> administrators) {
        return new ProjectImpl(id, name, description, address, owner, administrators);
    }

    public static Operation getOperation(OperationId id, Name operationName, Description description, OperationType type, Optional<Set<OperationPrerequisite>> prerequisites) {
        return new OperationImpl(id, operationName, description, type, prerequisites);
    }

    public static Role getRole(RoleId id, Name name, Description description, Set<ProjectId> projects, Set<OperationId> operations) {
        return new RoleImpl(id, name, description, projects, operations);
    }

    public static User getUser(UserId userId, Name name, Address emailAddress) {
        return new UserImpl(userId, name, emailAddress);
    }

    /*   sets of identifiers   */

    public static Set<UserId> getUserIdSet(String... userIds) {
        Set<UserId> userIdSet = new HashSet<>();
        for(String userId : userIds) {
            userIdSet.add(getUserId(userId));
        }
        return userIdSet;
    }

    public static Set<UserId> getUserIdSet(UserId... userIds) {
        Set<UserId> userIdSet = new HashSet<>();
        for(UserId userId : userIds) {
            userIdSet.add(userId);
        }
        return userIdSet;
    }

    public static Set<OperationId> getOperationIdSet(String... operationIds) {
        Set<OperationId> operationIdSet = new HashSet<>();
        for(String operationId : operationIds) {
            operationIdSet.add(getOperationId(operationId));
        }
        return operationIdSet;
    }

    public static Set<ProjectId> getProjectIdSet(String... projectIds) {
        Set<ProjectId> projectIdSet = new HashSet<>();
        for(String projectId : projectIds) {
            projectIdSet.add(getProjectId(projectId));
        }
        return projectIdSet;
    }

    /*   sets of other things   */

    public static Set<OperationPrerequisite> getOperationPrerequisiteSet(OperationPrerequisite... operationPrerequisites) {
        Set<OperationPrerequisite> operationPrerequisiteSet = new HashSet<>();
        for(OperationPrerequisite prerequisite : operationPrerequisites) {
            operationPrerequisiteSet.add(prerequisite);
        }
        return operationPrerequisiteSet;
    }
}
