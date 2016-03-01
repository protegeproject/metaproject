package edu.stanford.protege.metaproject.impl;

import edu.stanford.protege.metaproject.Manager;
import edu.stanford.protege.metaproject.api.Factory;
import edu.stanford.protege.metaproject.api.Operation;
import edu.stanford.protege.metaproject.api.OperationType;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public final class Operations {
    private static Factory factory = Manager.getFactory();
    private final static List<Operation> defaultOperations = new ArrayList<>();

    /**
     * Metaproject operations
     */

    // Add user
    public static final Operation ADD_USER = factory.createOperation(
            factory.createOperationId("add-user"), factory.createName("Add user"),
            factory.createDescription("Add a user to the user registry"),
            OperationType.METAPROJECT, Optional.empty());

    // Remove user
    public static final Operation REMOVE_USER = factory.createOperation(
            factory.createOperationId("remove-user"), factory.createName("Remove user"),
            factory.createDescription("Remove a user from the user registry"),
            OperationType.METAPROJECT, Optional.empty());

    // Add project
    public static final Operation ADD_PROJECT = factory.createOperation(
            factory.createOperationId("add-project"), factory.createName("Add project"),
            factory.createDescription("Add a project to the project registry"),
            OperationType.METAPROJECT, Optional.empty());

    // Remove project
    public static final Operation REMOVE_PROJECT = factory.createOperation(
            factory.createOperationId("remove-project"), factory.createName("Remove project"),
            factory.createDescription("Remove a project from the project registry"),
            OperationType.METAPROJECT, Optional.empty());

    // View project
    public static final Operation VIEW_PROJECT = factory.createOperation(
            factory.createOperationId("view-project"), factory.createName("View project"),
            factory.createDescription("View a project in the project registry"),
            OperationType.METAPROJECT, Optional.empty());

    // Add role
    public static final Operation ADD_ROLE = factory.createOperation(
            factory.createOperationId("add-role"), factory.createName("Add role"),
            factory.createDescription("Add a role to the role registry"),
            OperationType.METAPROJECT, Optional.empty());

    // Remove role
    public static final Operation REMOVE_ROLE = factory.createOperation(
            factory.createOperationId("remove-role"), factory.createName("Remove role"),
            factory.createDescription("Remove a role from the role registry"),
            OperationType.METAPROJECT, Optional.empty());

    // Add operation
    public static final Operation ADD_OPERATION = factory.createOperation(
            factory.createOperationId("add-operation"), factory.createName("Add operation"),
            factory.createDescription("Add an operation to the operation registry"),
            OperationType.METAPROJECT, Optional.empty());

    // Remove operation
    public static final Operation REMOVE_OPERATION = factory.createOperation(
            factory.createOperationId("remove-operation"), factory.createName("Remove operation"),
            factory.createDescription("Remove an operation from the operation registry"),
            OperationType.METAPROJECT, Optional.empty());

    // Assign role
    public static final Operation ASSIGN_ROLE = factory.createOperation(
            factory.createOperationId("assign-role"), factory.createName("Assign role"),
            factory.createDescription("Assign a role to a user within a project"),
            OperationType.METAPROJECT, Optional.empty());

    // Retract role
    public static final Operation RETRACT_ROLE = factory.createOperation(
            factory.createOperationId("retract-role"), factory.createName("Retract role"),
            factory.createDescription("Retract a role from a user"),
            OperationType.METAPROJECT, Optional.empty());


    /**
     * Server operations
     */

    // Stop server
    public static final Operation STOP_SERVER = factory.createOperation(
            factory.createOperationId("stop-server"), factory.createName("Stop the server"),
            factory.createDescription("Stop the execution of the server"),
            OperationType.SERVER, Optional.empty());

    // Restart server
    public static final Operation RESTART_SERVER = factory.createOperation(
            factory.createOperationId("restart-server"), factory.createName("Restart the server"),
            factory.createDescription("Restart the execution of the server"),
            OperationType.SERVER, Optional.empty());

    // Modify server configuration
    public static final Operation MODIFY_SERVER_CONFIG = factory.createOperation(
            factory.createOperationId("modify-config"), factory.createName("Modify server configuration"),
            factory.createDescription("Make changes to the configuration of the server"),
            OperationType.SERVER, Optional.empty());


    /**
     * OWL ontology operations
     */

    // Add ontology annotation
    public static final Operation ADD_ONTOLOGY_ANNOTATION = factory.createOperation(
            factory.createOperationId("add-ontology-annotation"), factory.createName("Add ontology annotation"),
            factory.createDescription("Add an annotation to the ontology"),
            OperationType.ONTOLOGY, Optional.empty());

    // Remove ontology annotation
    public static final Operation REMOVE_ONTOLOGY_ANNOTATION = factory.createOperation(
            factory.createOperationId("remove-ontology-annotation"), factory.createName("Remove ontology annotation"),
            factory.createDescription("Remove an annotation from the ontology"),
            OperationType.ONTOLOGY, Optional.empty());

    // Add import
    public static final Operation ADD_IMPORT = factory.createOperation(
            factory.createOperationId("add-import"), factory.createName("Add ontology import"),
            factory.createDescription("Add an imported ontology to the ontology"),
            OperationType.ONTOLOGY, Optional.empty());

    // Remove import
    public static final Operation REMOVE_IMPORT = factory.createOperation(
            factory.createOperationId("remove-import"), factory.createName("Remove ontology import"),
            factory.createDescription("Remove an imported ontology from the ontology"),
            OperationType.ONTOLOGY, Optional.empty());

    // Modify ontology IRI
    public static final Operation MODIFY_ONTOLOGY_IRI = factory.createOperation(
            factory.createOperationId("modify-ontology-iri"), factory.createName("Modify the ontology IRI"),
            factory.createDescription("Modify the IRI of the ontology"),
            OperationType.ONTOLOGY, Optional.empty());


    /**
     * Create a list of all operations declared as members of this class
     */
    static {
        Field[] fields = Operations.class.getDeclaredFields();
        for(Field f : fields) {
            if(f.getType().isAssignableFrom(Operation.class)) {
                try {
                    defaultOperations.add((Operation) f.get(Operation.class));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Get a list of all the default operations
     *
     * @return List of operations
     */
    public static List<Operation> getDefaultOperations() {
        return defaultOperations;
    }
}
