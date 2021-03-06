package edu.stanford.protege.metaproject.impl;

import edu.stanford.protege.metaproject.ConfigurationManager;
import edu.stanford.protege.metaproject.api.PolicyFactory;
import edu.stanford.protege.metaproject.api.Operation;
import edu.stanford.protege.metaproject.api.OperationId;
import edu.stanford.protege.metaproject.api.OperationType;

import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.ThreadSafe;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Rafael Gonçalves <br>
 * Center for Biomedical Informatics Research <br>
 * Stanford University
 */
@Immutable
@ThreadSafe
public final class Operations {
    private static PolicyFactory factory = ConfigurationManager.getFactory();
    private static Set<Operation> defaultOperations = new HashSet<>();

    /*
     * Metaproject operations
     */

    // Add user
    public static final Operation ADD_USER = factory.getSystemOperation(
            factory.getOperationId("add-user"), factory.getName("Add user"),
            factory.getDescription("Add a user to the user registry"), OperationType.WRITE, Operation.Scope.POLICY);

    // Remove user
    public static final Operation REMOVE_USER = factory.getSystemOperation(
            factory.getOperationId("remove-user"), factory.getName("Remove user"),
            factory.getDescription("Remove a user from the user registry"), OperationType.WRITE, Operation.Scope.POLICY);

    // Modify user
    public static final Operation MODIFY_USER = factory.getSystemOperation(
            factory.getOperationId("modify-user"), factory.getName("Modify user"),
            factory.getDescription("Modify user details (name and email address)"), OperationType.WRITE, Operation.Scope.POLICY);

    // Add project
    public static final Operation ADD_PROJECT = factory.getSystemOperation(
            factory.getOperationId("add-project"), factory.getName("Add project"),
            factory.getDescription("Add a project to the project registry"), OperationType.WRITE, Operation.Scope.POLICY);

    // Remove project
    public static final Operation REMOVE_PROJECT = factory.getSystemOperation(
            factory.getOperationId("remove-project"), factory.getName("Remove project"),
            factory.getDescription("Remove a project from the project registry"), OperationType.WRITE, Operation.Scope.POLICY);

    // Modify project
    public static final Operation MODIFY_PROJECT = factory.getSystemOperation(
            factory.getOperationId("modify-project"), factory.getName("Modify project"),
            factory.getDescription("Modify project details and options"), OperationType.WRITE, Operation.Scope.POLICY);

    // View project
    public static final Operation OPEN_PROJECT = factory.getSystemOperation(
            factory.getOperationId("open-project"), factory.getName("Open project"),
            factory.getDescription("Open a project in the project registry"), OperationType.READ, Operation.Scope.POLICY);

    // Add role
    public static final Operation ADD_ROLE = factory.getSystemOperation(
            factory.getOperationId("add-role"), factory.getName("Add role"),
            factory.getDescription("Add a role to the role registry"), OperationType.WRITE, Operation.Scope.POLICY);

    // Remove role
    public static final Operation REMOVE_ROLE = factory.getSystemOperation(
            factory.getOperationId("remove-role"), factory.getName("Remove role"),
            factory.getDescription("Remove a role from the role registry"), OperationType.WRITE, Operation.Scope.POLICY);

    // Modify role
    public static final Operation MODIFY_ROLE = factory.getSystemOperation(
            factory.getOperationId("modify-role"), factory.getName("Modify role"),
            factory.getDescription("Modify role details and associated operations"), OperationType.WRITE, Operation.Scope.POLICY);

    // Add operation
    public static final Operation ADD_OPERATION = factory.getSystemOperation(
            factory.getOperationId("add-operation"), factory.getName("Add operation"),
            factory.getDescription("Add an operation to the operation registry"), OperationType.WRITE, Operation.Scope.POLICY);

    // Remove operation
    public static final Operation REMOVE_OPERATION = factory.getSystemOperation(
            factory.getOperationId("remove-operation"), factory.getName("Remove operation"),
            factory.getDescription("Remove an operation from the operation registry"), OperationType.WRITE, Operation.Scope.POLICY);

    // Modify operation
    public static final Operation MODIFY_OPERATION = factory.getSystemOperation(
            factory.getOperationId("modify-operation"), factory.getName("Modify operation"),
            factory.getDescription("Modify operation details"), OperationType.WRITE, Operation.Scope.POLICY);

    // Assign role
    public static final Operation ASSIGN_ROLE = factory.getSystemOperation(
            factory.getOperationId("assign-role"), factory.getName("Assign role"),
            factory.getDescription("Assign a role to a user within a project"), OperationType.WRITE, Operation.Scope.POLICY);

    // Retract role
    public static final Operation RETRACT_ROLE = factory.getSystemOperation(
            factory.getOperationId("retract-role"), factory.getName("Retract role"),
            factory.getDescription("Retract a role from a user"), OperationType.WRITE, Operation.Scope.POLICY);


    /*
     * Server operations
     */

    // Stop server
    public static final Operation STOP_SERVER = factory.getSystemOperation(
            factory.getOperationId("stop-server"), factory.getName("Stop the server"),
            factory.getDescription("Stop the execution of the server"), OperationType.EXECUTE, Operation.Scope.SERVER);

    // Modify server configuration
    public static final Operation MODIFY_SERVER_SETTINGS = factory.getSystemOperation(
            factory.getOperationId("modify-settings"), factory.getName("Modify server settings"),
            factory.getDescription("Make changes to the settings of the server, such as host or custom properties"), OperationType.WRITE, Operation.Scope.SERVER);


    /*
     * Broad categories of OWL ontology operations following top-level OWLAPI OWLOntologyChange subtypes
     */

    // Add axiom
    public static final Operation ADD_AXIOM = factory.getSystemOperation(
            factory.getOperationId("add-axiom"), factory.getName("Add axiom"),
            factory.getDescription("Add an axiom to the ontology"), OperationType.WRITE, Operation.Scope.ONTOLOGY);

    // Remove axiom
    public static final Operation REMOVE_AXIOM = factory.getSystemOperation(
            factory.getOperationId("remove-axiom"), factory.getName("Remove axiom"),
            factory.getDescription("Remove an axiom from the ontology"), OperationType.WRITE, Operation.Scope.ONTOLOGY);

    // Add ontology annotation
    public static final Operation ADD_ONTOLOGY_ANNOTATION = factory.getSystemOperation(
            factory.getOperationId("add-ontology-annotation"), factory.getName("Add ontology annotation"),
            factory.getDescription("Add an annotation to the ontology"), OperationType.WRITE, Operation.Scope.ONTOLOGY);

    // Remove ontology annotation
    public static final Operation REMOVE_ONTOLOGY_ANNOTATION = factory.getSystemOperation(
            factory.getOperationId("remove-ontology-annotation"), factory.getName("Remove ontology annotation"),
            factory.getDescription("Remove an annotation from the ontology"), OperationType.WRITE, Operation.Scope.ONTOLOGY);

    // Add import
    public static final Operation ADD_IMPORT = factory.getSystemOperation(
            factory.getOperationId("add-import"), factory.getName("Add ontology import"),
            factory.getDescription("Add an imported ontology to the ontology"), OperationType.WRITE, Operation.Scope.ONTOLOGY);

    // Remove import
    public static final Operation REMOVE_IMPORT = factory.getSystemOperation(
            factory.getOperationId("remove-import"), factory.getName("Remove ontology import"),
            factory.getDescription("Remove an imported ontology from the ontology"), OperationType.WRITE, Operation.Scope.ONTOLOGY);

    // Modify ontology IRI
    public static final Operation MODIFY_ONTOLOGY_IRI = factory.getSystemOperation(
            factory.getOperationId("modify-ontology-iri"), factory.getName("Modify the ontology IRI"),
            factory.getDescription("Modify the IRI of the ontology"), OperationType.WRITE, Operation.Scope.ONTOLOGY);

    // Accept change in revision history
    public static final Operation ACCEPT_CHANGE = factory.getSystemOperation(
            factory.getOperationId("accept-change"), factory.getName("Accept change"),
            factory.getDescription("Accept a change in the change history"), OperationType.WRITE, Operation.Scope.ONTOLOGY);

    // Reject change in revision history
    public static final Operation REJECT_CHANGE = factory.getSystemOperation(
            factory.getOperationId("reject-change"), factory.getName("Reject change"),
            factory.getDescription("Reject a change in the change history"), OperationType.WRITE, Operation.Scope.ONTOLOGY);


    /*
     * Protege GUI component visibility operations
     */

    public static final Operation USE_VIEWS_MENU = factory.getSystemOperation(
            factory.getOperationId("use-views-menu"), factory.getName("Use the Views menu"),
            factory.getDescription("Use the 'Window > Views' menu item"), OperationType.EXECUTE, Operation.Scope.GUI);

    public static final Operation USE_ENTITIES_TAB = factory.getSystemOperation(
            factory.getOperationId("use-entities-tab"), factory.getName("Use the Entities tab"),
            factory.getDescription("Use the 'Entities' tab"), OperationType.EXECUTE, Operation.Scope.GUI);

    public static final Operation USE_ACTIVE_ONTOLOGY_TAB = factory.getSystemOperation(
            factory.getOperationId("use-active-ontology-tab"), factory.getName("Use the Active Ontology tab"),
            factory.getDescription("Use the 'Active Ontology' tab"), OperationType.EXECUTE, Operation.Scope.GUI);

    public static final Operation USE_CLASSES_TAB = factory.getSystemOperation(
            factory.getOperationId("use-classes-tab"), factory.getName("Use the Classes tab"),
            factory.getDescription("Use the 'Classes' tab"), OperationType.EXECUTE, Operation.Scope.GUI);

    public static final Operation USE_OBJECT_PROPERTIES_TAB = factory.getSystemOperation(
            factory.getOperationId("use-object-properties-tab"), factory.getName("Use the Object Properties tab"),
            factory.getDescription("Use the 'Object Properties' tab"), OperationType.EXECUTE, Operation.Scope.GUI);

    public static final Operation USE_DATA_PROPERTIES_TAB = factory.getSystemOperation(
            factory.getOperationId("use-data-properties-tab"), factory.getName("Use the Data Properties tab"),
            factory.getDescription("Use the 'Data Properties' tab"), OperationType.EXECUTE, Operation.Scope.GUI);

    public static final Operation USE_ANNOTATION_PROPERTIES_TAB = factory.getSystemOperation(
            factory.getOperationId("use-annotation-properties-tab"), factory.getName("Use the Annotation Properties tab"),
            factory.getDescription("Use the 'Annotation Properties' tab"), OperationType.EXECUTE, Operation.Scope.GUI);

    public static final Operation USE_INDIVIDUALS_TAB = factory.getSystemOperation(
            factory.getOperationId("use-individuals-tab"), factory.getName("Use the Annotation Properties tab"),
            factory.getDescription("Use the 'Annotation Properties' tab"), OperationType.EXECUTE, Operation.Scope.GUI);

    public static final Operation USE_REVISION_LOG_TAB = factory.getSystemOperation(
            factory.getOperationId("use-revision-log-tab"), factory.getName("Use the Revision Log diff tab"),
            factory.getDescription("Use the 'Revision Log diff' tab"), OperationType.EXECUTE, Operation.Scope.GUI);

    public static final Operation USE_SERVER_ADMIN_TAB = factory.getSystemOperation(
            factory.getOperationId("use-server-admin-tab"), factory.getName("Use the Server Administration tab"),
            factory.getDescription("Use the 'Server Administration' tab"), OperationType.EXECUTE, Operation.Scope.GUI);


    /*
      Create a list of all operations declared as members of this class
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
        return defaultOperations.stream().filter(op -> op.getScope().equals(Operation.Scope.ONTOLOGY)).collect(Collectors.toSet());
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
        return defaultOperations.stream().filter(op -> op.getScope().equals(Operation.Scope.SERVER)).collect(Collectors.toSet());
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
        return defaultOperations.stream().filter(op -> op.getScope().equals(Operation.Scope.POLICY)).collect(Collectors.toSet());
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
