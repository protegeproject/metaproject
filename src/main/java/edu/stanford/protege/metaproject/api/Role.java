package edu.stanford.protege.metaproject.api;

import com.google.common.collect.ImmutableSet;

import javax.annotation.Nonnull;

/**
 * A role defines a set of allowed operations. It essentially represents a permission set
 * that can be reused for different users in different projects.
 *
 * @author Rafael Gonçalves <br>
 * Center for Biomedical Informatics Research <br>
 * Stanford University
 */
public interface Role extends PolicyObject<RoleId>, HasDescription, Comparable<Role> {

    /**
     * Get the set of operations associated with this role
     *
     * @return Set of operations identifiers
     */
    @Nonnull
    ImmutableSet<OperationId> getOperations();

}
