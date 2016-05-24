package edu.stanford.protege.metaproject.impl;

import edu.stanford.protege.metaproject.Manager;
import edu.stanford.protege.metaproject.api.MetaprojectFactory;
import edu.stanford.protege.metaproject.api.Operation;
import edu.stanford.protege.metaproject.api.OperationId;
import edu.stanford.protege.metaproject.api.OperationType;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public final class Operations {
    private static MetaprojectFactory factory = Manager.getFactory();
    private static Set<Operation> defaultOperations = new HashSet<>();

    /**
     * Metaproject operations
     */

    // Add user
    public static final Operation ADD_USER = factory.getMetaprojectOperation(
            factory.getOperationId("add-user"), factory.getName("Add user"),
            factory.getDescription("Add a user to the user registry"), OperationType.WRITE);

    // Remove user
    public static final Operation REMOVE_USER = factory.getMetaprojectOperation(
            factory.getOperationId("remove-user"), factory.getName("Remove user"),
            factory.getDescription("Remove a user from the user registry"), OperationType.WRITE);

    // Modify user
    public static final Operation MODIFY_USER = factory.getMetaprojectOperation(
            factory.getOperationId("modify-user"), factory.getName("Modify user"),
            factory.getDescription("Modify user details (name and email address)"), OperationType.WRITE);

    // Add project
    public static final Operation ADD_PROJECT = factory.getMetaprojectOperation(
            factory.getOperationId("add-project"), factory.getName("Add project"),
            factory.getDescription("Add a project to the project registry"), OperationType.WRITE);

    // Remove project
    public static final Operation REMOVE_PROJECT = factory.getMetaprojectOperation(
            factory.getOperationId("remove-project"), factory.getName("Remove project"),
            factory.getDescription("Remove a project from the project registry"), OperationType.WRITE);

    // Modify project
    public static final Operation MODIFY_PROJECT = factory.getMetaprojectOperation(
            factory.getOperationId("modify-project"), factory.getName("Modify project"),
            factory.getDescription("Modify project details and options"), OperationType.WRITE);

    // View project
    public static final Operation OPEN_PROJECT = factory.getMetaprojectOperation(
            factory.getOperationId("open-project"), factory.getName("Open project"),
            factory.getDescription("Open a project in the project registry"), OperationType.READ);

    // Add role
    public static final Operation ADD_ROLE = factory.getMetaprojectOperation(
            factory.getOperationId("add-role"), factory.getName("Add role"),
            factory.getDescription("Add a role to the role registry"), OperationType.WRITE);

    // Remove role
    public static final Operation REMOVE_ROLE = factory.getMetaprojectOperation(
            factory.getOperationId("remove-role"), factory.getName("Remove role"),
            factory.getDescription("Remove a role from the role registry"), OperationType.WRITE);

    // Modify role
    public static final Operation MODIFY_ROLE = factory.getMetaprojectOperation(
            factory.getOperationId("modify-role"), factory.getName("Modify role"),
            factory.getDescription("Modify role details and associated operations"), OperationType.WRITE);

    // Add operation
    public static final Operation ADD_OPERATION = factory.getMetaprojectOperation(
            factory.getOperationId("add-operation"), factory.getName("Add operation"),
            factory.getDescription("Add an operation to the operation registry"), OperationType.WRITE);

    // Remove operation
    public static final Operation REMOVE_OPERATION = factory.getMetaprojectOperation(
            factory.getOperationId("remove-operation"), factory.getName("Remove operation"),
            factory.getDescription("Remove an operation from the operation registry"), OperationType.WRITE);

    // Modify operation
    public static final Operation MODIFY_OPERATION = factory.getMetaprojectOperation(
            factory.getOperationId("modify-operation"), factory.getName("Modify operation"),
            factory.getDescription("Modify operation details"), OperationType.WRITE);

    // Assign role
    public static final Operation ASSIGN_ROLE = factory.getMetaprojectOperation(
            factory.getOperationId("assign-role"), factory.getName("Assign role"),
            factory.getDescription("Assign a role to a user within a project"), OperationType.WRITE);

    // Retract role
    public static final Operation RETRACT_ROLE = factory.getMetaprojectOperation(
            factory.getOperationId("retract-role"), factory.getName("Retract role"),
            factory.getDescription("Retract a role from a user"), OperationType.WRITE);


    /**
     * Server operations
     */

    // Stop server
    public static final Operation STOP_SERVER = factory.getServerOperation(
            factory.getOperationId("stop-server"), factory.getName("Stop the server"),
            factory.getDescription("Stop the execution of the server"), OperationType.EXECUTE);

    // Restart server
    public static final Operation RESTART_SERVER = factory.getServerOperation(
            factory.getOperationId("restart-server"), factory.getName("Restart the server"),
            factory.getDescription("Restart the execution of the server"), OperationType.EXECUTE);

    // Modify server configuration
    public static final Operation MODIFY_SERVER_CONFIG = factory.getServerOperation(
            factory.getOperationId("modify-config"), factory.getName("Modify server configuration"),
            factory.getDescription("Make changes to the configuration of the server"), OperationType.WRITE);


    /**
     * Broad categories of OWL ontology operations following top-level OWLAPI OWLOntologyChange subtypes
     */

    // Add axiom
    public static final Operation ADD_AXIOM = factory.getOntologyOperation(
            factory.getOperationId("add-axiom"), factory.getName("Add axiom"),
            factory.getDescription("Add an axiom to the ontology"), OperationType.WRITE);

    // Remove axiom
    public static final Operation REMOVE_AXIOM = factory.getOntologyOperation(
            factory.getOperationId("remove-axiom"), factory.getName("Remove axiom"),
            factory.getDescription("Remove an axiom from the ontology"), OperationType.WRITE);

    // Add ontology annotation
    public static final Operation ADD_ONTOLOGY_ANNOTATION = factory.getOntologyOperation(
            factory.getOperationId("add-ontology-annotation"), factory.getName("Add ontology annotation"),
            factory.getDescription("Add an annotation to the ontology"), OperationType.WRITE);

    // Remove ontology annotation
    public static final Operation REMOVE_ONTOLOGY_ANNOTATION = factory.getOntologyOperation(
            factory.getOperationId("remove-ontology-annotation"), factory.getName("Remove ontology annotation"),
            factory.getDescription("Remove an annotation from the ontology"), OperationType.WRITE);

    // Add import
    public static final Operation ADD_IMPORT = factory.getOntologyOperation(
            factory.getOperationId("add-import"), factory.getName("Add ontology import"),
            factory.getDescription("Add an imported ontology to the ontology"), OperationType.WRITE);

    // Remove import
    public static final Operation REMOVE_IMPORT = factory.getOntologyOperation(
            factory.getOperationId("remove-import"), factory.getName("Remove ontology import"),
            factory.getDescription("Remove an imported ontology from the ontology"), OperationType.WRITE);

    // Modify ontology IRI
    public static final Operation MODIFY_ONTOLOGY_IRI = factory.getOntologyOperation(
            factory.getOperationId("modify-ontology-iri"), factory.getName("Modify the ontology IRI"),
            factory.getDescription("Modify the IRI of the ontology"), OperationType.WRITE);

    // Accept change in revision history
    public static final Operation ACCEPT_CHANGE = factory.getOntologyOperation(
            factory.getOperationId("accept-change"), factory.getName("Accept change"),
            factory.getDescription("Accept a change in the change history"), OperationType.WRITE);

    // Reject change in revision history
    public static final Operation REJECT_CHANGE = factory.getOntologyOperation(
            factory.getOperationId("reject-change"), factory.getName("Reject change"),
            factory.getDescription("Reject a change in the change history"), OperationType.WRITE);


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
     * Get a set of all the default operations
     *
     * @return Set of operations
     */
    public static Set<Operation> getDefaultOperations() {
        return defaultOperations;
    }

    /**
     * Get a set of all the default operations' identifiers
     *
     * @return Set of operation identifiers
     */
    public static Set<OperationId> getDefaultOperationsIds() {
        return defaultOperations.stream().map(Operation::getId).collect(Collectors.toSet());
    }

    /**
     * Get the set of default operations of the specified type
     *
     * @param type  Operation type
     * @return Set of operations
     */
    public static Set<Operation> getDefaultOperations(OperationType type) {
        return defaultOperations.stream().filter(op -> op.getType().equals(type)).collect(Collectors.toSet());
    }

    /**
     * Get the set of default operation identifiers of the specified type
     *
     * @param type  Operation type
     * @return Set of operation identifiers
     */
    public static Set<OperationId> getDefaultOperationsIds(OperationType type) {
        return getDefaultOperations(type).stream().map(Operation::getId).collect(Collectors.toSet());
    }

    /**
     * Get the set of default ontology operations
     *
     * @return Set of operations
     */
    public static Set<Operation> getOntologyOperations() {
        return defaultOperations.stream().filter(Operation::isOntologyOperation).collect(Collectors.toSet());
    }

    /**
     * Get the set of default ontology operations identifiers
     *
     * @return Set of operation identifiers
     */
    public static Set<OperationId> getOntologyOperationsIds() {
        return getOntologyOperations().stream().map(Operation::getId).collect(Collectors.toSet());
    }

    /**
     * Get the set of default server operations
     *
     * @return Set of operations
     */
    public static Set<Operation> getServerOperations() {
        return defaultOperations.stream().filter(Operation::isServerOperation).collect(Collectors.toSet());
    }

    /**
     * Get the set of default server operations identifiers
     *
     * @return Set of operation identifiers
     */
    public static Set<OperationId> getServerOperationsIds() {
        return getServerOperations().stream().map(Operation::getId).collect(Collectors.toSet());
    }

    /**
     * Get the set of default metaproject operations
     *
     * @return Set of operations
     */
    public static Set<Operation> getMetaprojectOperations() {
        return defaultOperations.stream().filter(Operation::isMetaprojectOperation).collect(Collectors.toSet());
    }

    /**
     * Get the set of default metaproject operations identifiers
     *
     * @return Set of operation identifiers
     */
    public static Set<OperationId> getMetaprojectOperationsIds() {
        return getMetaprojectOperations().stream().map(Operation::getId).collect(Collectors.toSet());
    }
}
