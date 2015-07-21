package edu.stanford.protege.metaproject.api;

public class AccessControlPolicyBuilder {
    private RoleManager roleManager;
    private OperationManager operationManager;
    private UserManager userManager;
    private ProjectManager projectManager;

    public AccessControlPolicyBuilder setRoleManager(RoleManager roleManager) {
        this.roleManager = roleManager;
        return this;
    }

    public AccessControlPolicyBuilder setOperationManager(OperationManager operationManager) {
        this.operationManager = operationManager;
        return this;
    }

    public AccessControlPolicyBuilder setUserManager(UserManager userManager) {
        this.userManager = userManager;
        return this;
    }

    public AccessControlPolicyBuilder setProjectManager(ProjectManager projectManager) {
        this.projectManager = projectManager;
        return this;
    }

    public AccessControlPolicy createAccessControlPolicy() {
        return new AccessControlPolicy(roleManager, operationManager, userManager, projectManager);
    }
}