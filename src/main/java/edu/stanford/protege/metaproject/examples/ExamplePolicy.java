package edu.stanford.protege.metaproject.examples;

import edu.stanford.protege.metaproject.api.*;
import edu.stanford.protege.metaproject.api.exception.EmailAddressAlreadyInUseException;
import edu.stanford.protege.metaproject.api.exception.UserIdAlreadyInUseException;
import edu.stanford.protege.metaproject.api.impl.AccessControlPolicyImpl;
import edu.stanford.protege.metaproject.api.impl.OperationPrerequisiteImpl;
import org.semanticweb.owlapi.model.IRI;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class ExamplePolicy {

    public ExamplePolicy() {}

    public AccessControlPolicy getPolicy() {
        AccessControlPolicy accessControlPolicy = new AccessControlPolicyImpl.Builder().createAccessControlPolicy();

        // access control object managers
        UserManager userManager = accessControlPolicy.getUserManager();
        ProjectManager projectManager = accessControlPolicy.getProjectManager();
        OperationManager operationManager = accessControlPolicy.getOperationManager();
        RoleManager roleManager = accessControlPolicy.getRoleManager();
        PolicyManager policyManager = accessControlPolicy.getPolicyManager();

        // create new user
        User user1 = userManager.create("user1", "user 1", "test_user_1@test.com");
        User user2 = userManager.create("user2", "user 2", "test_user_2@test.com");
        User user3 = userManager.create("user3", "user 3", "test_user_3@test.com");
        try {
            userManager.add(user1, user2, user3);
        } catch (EmailAddressAlreadyInUseException | UserIdAlreadyInUseException e) {
            e.printStackTrace();
        }

        // project administrators
        Set<UserId> project1_admins = new HashSet<>(); project1_admins.add(user1.getId()); project1_admins.add(user2.getId());
        Set<UserId> project2_admins = new HashSet<>(); project2_admins.add(user2.getId());

        // create new projects
        Project project1 = projectManager.create("project 1", "test project 1", "here", user1.getId(), project1_admins);
        Project project2 = projectManager.create("project 2", "test project 2", "there", user2.getId(), project2_admins);
        projectManager.add(project1, project2);

        // create prerequisites for operations
        Set<OperationPrerequisite> op3_prerequisites = new HashSet<>();
        op3_prerequisites.add(new OperationPrerequisiteImpl(IRI.create("http://test.com/class_C"), OperationPrerequisite.Modifier.PRESENT));
        op3_prerequisites.add(new OperationPrerequisiteImpl(IRI.create("http://test.com/class_D"), OperationPrerequisite.Modifier.ABSENT));

        Set<OperationPrerequisite> op1_prerequisites = new HashSet<>();
        op1_prerequisites.add(new OperationPrerequisiteImpl(IRI.create("http://test.com/class_A"), OperationPrerequisite.Modifier.PRESENT));

        // create new operations
        Operation operation1 = operationManager.create("operation 1", "test operation 1", OperationType.READ, Optional.of(op1_prerequisites));
        Operation operation2 = operationManager.create("operation 2", "test operation 2", OperationType.WRITE, Optional.empty());
        Operation operation3 = operationManager.create("operation 3", "test operation 3", OperationType.POLICY, Optional.of(op3_prerequisites));
        operationManager.add(operation1, operation2, operation3);

        // create a set of operations to use when formulating roles
        Set<OperationId> role1_operations = new HashSet<>();
        role1_operations.add(operation1.getId());
        role1_operations.add(operation2.getId());
        role1_operations.add(operation3.getId());

        Set<OperationId> role2_operations = new HashSet<>();
        role2_operations.add(operation3.getId());

        // create a set of projects to use when formulating roles
        Set<ProjectId> role1_projects = new HashSet<>();
        role1_projects.add(project1.getId());
        role1_projects.add(project2.getId());

        Set<ProjectId> role2_projects = new HashSet<>();
        role2_projects.add(project1.getId());

        // create new roles
        Role role1 = roleManager.create("role 1", "test role 1", role1_projects, role1_operations);
        Role role2 = roleManager.create("role 2", "test role 2", role2_projects, role2_operations);
        roleManager.add(role1, role2);

        // add pairs to policy
        policyManager.add(user1.getId(), role1.getId());
        policyManager.add(user1.getId(), role2.getId());
        policyManager.add(user2.getId(), role2.getId());

        return accessControlPolicy;
    }
}

