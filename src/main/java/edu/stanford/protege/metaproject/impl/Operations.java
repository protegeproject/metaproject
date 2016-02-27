package edu.stanford.protege.metaproject.impl;

import edu.stanford.protege.metaproject.Manager;
import edu.stanford.protege.metaproject.api.Factory;
import edu.stanford.protege.metaproject.api.Operation;
import edu.stanford.protege.metaproject.api.OperationType;

import java.util.Optional;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class Operations {
    private static Factory factory = Manager.getFactory();

    /**
     * Metaproject operations
     */

    // Add user
    public static Operation ADD_USER = factory.createOperation(
            factory.createOperationUuid(), factory.createName("Add user"),
            factory.createDescription("Add a user to the user registry"),
            OperationType.METAPROJECT, Optional.empty());

    // Remove user
    public static Operation REMOVE_USER = factory.createOperation(
            factory.createOperationUuid(), factory.createName("Remove user"),
            factory.createDescription("Remove a user from the user registry"),
            OperationType.METAPROJECT, Optional.empty());

    // Add project
    public static Operation ADD_PROJECT = factory.createOperation(
            factory.createOperationUuid(), factory.createName("Add project"),
            factory.createDescription("Add a project to the project registry"),
            OperationType.METAPROJECT, Optional.empty());

    // Remove project
    public static Operation REMOVE_PROJECT = factory.createOperation(
            factory.createOperationUuid(), factory.createName("Remove project"),
            factory.createDescription("Remove a project from the project registry"),
            OperationType.METAPROJECT, Optional.empty());

    // View project
    public static Operation VIEW_PROJECT = factory.createOperation(
            factory.createOperationUuid(), factory.createName("View project"),
            factory.createDescription("View a project in the project registry"),
            OperationType.METAPROJECT, Optional.empty());

    // Add role
    public static Operation ADD_ROLE = factory.createOperation(
            factory.createOperationUuid(), factory.createName("Add role"),
            factory.createDescription("Add a role to the role registry"),
            OperationType.METAPROJECT, Optional.empty());

    // Remove role
    public static Operation REMOVE_ROLE = factory.createOperation(
            factory.createOperationUuid(), factory.createName("Remove role"),
            factory.createDescription("Remove a role from the role registry"),
            OperationType.METAPROJECT, Optional.empty());

    // Add operation
    public static Operation ADD_OPERATION = factory.createOperation(
            factory.createOperationUuid(), factory.createName("Add operation"),
            factory.createDescription("Add an operation to the operation registry"),
            OperationType.METAPROJECT, Optional.empty());

    // Remove operation
    public static Operation REMOVE_OPERATION = factory.createOperation(
            factory.createOperationUuid(), factory.createName("Remove operation"),
            factory.createDescription("Remove an operation from the operation registry"),
            OperationType.METAPROJECT, Optional.empty());

    // Assign role
    public static Operation ASSIGN_ROLE = factory.createOperation(
            factory.createOperationUuid(), factory.createName("Assign role"),
            factory.createDescription("Assign a role to a user within a project"),
            OperationType.METAPROJECT, Optional.empty());

    // Retract role
    public static Operation RETRACT_ROLE = factory.createOperation(
            factory.createOperationUuid(), factory.createName("Retract role"),
            factory.createDescription("Retract a role from a user"),
            OperationType.METAPROJECT, Optional.empty());


    /**
     * Server operations
     */

    // Stop server
    public static Operation stopServer = factory.createOperation(
            factory.createOperationUuid(), factory.createName("Stop the server"),
            factory.createDescription("Stop the execution of the server"),
            OperationType.SERVER, Optional.empty());

    // Restart server
    public static Operation restartServer = factory.createOperation(
            factory.createOperationUuid(), factory.createName("Restart the server"),
            factory.createDescription("Restart the execution of the server"),
            OperationType.SERVER, Optional.empty());

    // Modify server configuration
    public static Operation modifyServerConfig = factory.createOperation(
            factory.createOperationUuid(), factory.createName("Modify server configuration"),
            factory.createDescription("Make changes to the configuration of the server"),
            OperationType.SERVER, Optional.empty());

}
