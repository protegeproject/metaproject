package edu.stanford.protege.metaproject.impl;

import edu.stanford.protege.metaproject.Manager;
import edu.stanford.protege.metaproject.api.*;

import static com.google.common.base.Preconditions.checkNotNull;

public class MetaprojectBuilder {
    private Factory f = Manager.getFactory();
    private Policy policy = f.getPolicy();
    private RoleRegistry roleRegistry = f.getRoleRegistry();
    private OperationRegistry operationRegistry = f.getOperationRegistry();
    private UserRegistry userRegistry = f.getUserRegistry();
    private ProjectRegistry projectRegistry = f.getProjectRegistry();

    /**
     * Set the metaproject policy, defining what users have what roles in which projects
     *
     * @param policy    Policy
     * @return MetaprojectBuilder
     */
    public MetaprojectBuilder setPolicy(Policy policy) {
        this.policy = checkNotNull(policy);
        return this;
    }

    /**
     * Set the role registry
     *
     * @param roleRegistry  Role registry
     * @return MetaprojectBuilder
     */
    public MetaprojectBuilder setRoleRegistry(RoleRegistry roleRegistry) {
        this.roleRegistry = checkNotNull(roleRegistry);
        return this;
    }

    /**
     * Set the operation registry
     *
     * @param operationRegistry Operation registry
     * @return MetaprojectBuilder
     */
    public MetaprojectBuilder setOperationRegistry(OperationRegistry operationRegistry) {
        this.operationRegistry = checkNotNull(operationRegistry);
        return this;
    }

    /**
     * Set the user registry
     *
     * @param userRegistry  User registry
     * @return MetaprojectBuilder
     */
    public MetaprojectBuilder setUserRegistry(UserRegistry userRegistry) {
        this.userRegistry = checkNotNull(userRegistry);
        return this;
    }

    /**
     * Set the project registry
     *
     * @param projectRegistry    Project registry
     * @return MetaprojectBuilder
     */
    public MetaprojectBuilder setProjectRegistry(ProjectRegistry projectRegistry) {
        this.projectRegistry = checkNotNull(projectRegistry);
        return this;
    }

    /**
     * Create a metaproject instance
     *
     * @return Metaproject
     */
    public Metaproject createMetaproject() {
        return new MetaprojectImpl(policy, roleRegistry, operationRegistry, userRegistry, projectRegistry);
    }
}