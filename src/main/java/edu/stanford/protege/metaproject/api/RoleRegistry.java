package edu.stanford.protege.metaproject.api;

import edu.stanford.protege.metaproject.api.exception.UnknownMetaprojectObjectIdException;

/**
 * A manager for roles, i.e., containers of operations that are allowed to be performed.
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface RoleRegistry extends Registry<Role> {

    /**
     * Change the name of the given role to a new one
     *
     * @param roleId  Role identifier
     * @param roleName  New role name
     * @throws UnknownMetaprojectObjectIdException    Role identifier is not recognized
     */
    void setName(RoleId roleId, Name roleName) throws UnknownMetaprojectObjectIdException;

    /**
     * Change the description of the given role to a new one
     *
     * @param roleId  Role identifier
     * @param roleDescription   New role description
     * @throws UnknownMetaprojectObjectIdException    Role identifier is not recognized
     */
    void setDescription(RoleId roleId, Description roleDescription) throws UnknownMetaprojectObjectIdException;

    /**
     * Add one or more operations to the permitted operations of the given role
     *
     * @param roleId    Role identifier
     * @param operationIds   Operation identifier(s)
     * @throws UnknownMetaprojectObjectIdException    Role identifier is not recognized
     */
    void addOperation(RoleId roleId, OperationId... operationIds) throws UnknownMetaprojectObjectIdException;

    /**
     * Remove one or more operations from the permitted operations of the given role
     *
     * @param roleId    Role identifier
     * @param operationIds   Operation identifier(s)
     * @throws UnknownMetaprojectObjectIdException    Role identifier is not recognized
     */
    void removeOperation(RoleId roleId, OperationId... operationIds) throws UnknownMetaprojectObjectIdException;

}
