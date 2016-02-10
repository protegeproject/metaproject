package edu.stanford.protege.metaproject.examples;

import com.google.gson.Gson;
import edu.stanford.protege.metaproject.api.*;
import edu.stanford.protege.metaproject.api.exception.*;

import java.io.FileNotFoundException;
import java.util.*;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class MetaprojectExplorationExample {

    public static void main(String[] args) throws FileNotFoundException, EmailAddressAlreadyInUseException, UserIdAlreadyInUseException, UnknownUserIdException, UnknownOperationIdException, UnknownRoleIdException {
        AccessControlPolicy accessControlPolicy = Utils.getAccessControlPolicy();
        Gson gson = Utils.getGson();


        /*   Serializing access control policy   */

        // convert access control policy to its JSON representation
        String jsonAccessControlPolicy = gson.toJson(accessControlPolicy, AccessControlPolicy.class);

        // print JSON representation
        Utils.println(Utils.sep + "\nJSON representation of a sample access control policy\n" + Utils.sep + "\n\n" + jsonAccessControlPolicy);


        /*   Users   */

        UserManager userManager = accessControlPolicy.getUserManager();
        Utils.println("\n" + Utils.sep + "\nUsers\n" + Utils.sep);

        // get the known users (i.e., those registered with the user manager)
        Set<User> userSet = userManager.getUsers();

        // sort users into a list
        List<User> userList = new ArrayList<>(userSet);
        Collections.sort(userList);

        // print out the name, (login) identifier, and email address of each user
        for (User user : userList) {
            Utils.println(user.getName().get() + " (id: '" + user.getId().get() + "') - " + user.getEmailAddress().get());
        }


        /*   Projects   */

        Utils.println("\n" + Utils.sep + "\nProjects\n" + Utils.sep);
        ProjectManager projectManager = accessControlPolicy.getProjectManager();

        // get the known projects (i.e., those registered with the project manager)
        Set<Project> projects = projectManager.getProjects();

        // print out the name, description, address, owner and administrators of each project
        for (Project project : projects) {
            Utils.print(project.getName().get() + ": " + project.getDescription().get() +
                    "\n\taddress: " + project.getAddress().get() +
                    "\n\towner: " + project.getOwner().get() +
                    "\n\tadmins: ");
            for (UserId userId : project.getAdministrators()) {
                Utils.print("'" + userManager.getUser(userId).getName().get() + "' ");
            }
            Utils.println();
        }


        /*  Operations   */

        Utils.println("\n" + Utils.sep + "\nOperations\n" + Utils.sep);
        OperationManager operationManager = accessControlPolicy.getOperationManager();

        // get the known operations (i.e., those registered with the operation manager)
        Set<Operation> operations = operationManager.getOperations();

        // print out the name, description and type of each operation
        for(Operation operation : operations) {
            Utils.println(operation.getName().get() + ": " + operation.getDescription().get() +
            "\n\ttype: " + operation.getType() );
        }


        /*   Roles   */

        Utils.println("\n" + Utils.sep + "\nRoles\n" + Utils.sep);
        RoleManager roleManager = accessControlPolicy.getRoleManager();

        // get the known roles (i.e., those registered with the role manager)
        Set<Role> roles = roleManager.getRoles();

        for(Role role : roles) {
            Utils.print(role.getName().get() + ": " + role.getDescription().get() + "\n\toperations: " );

            // get identifiers of operations allowed by the role
            Set<OperationId> operationIds = role.getOperations();

            // print names of operations
            for(OperationId operationId : operationIds) {
                // fetch operation from operation manager, in order to get to its name
                Operation operation = operationManager.getOperation(operationId);
                Utils.print("'" + operation.getName().get() + "' ");
            }
            Utils.println();
        }


        /*   Policy   */

        Utils.println("\n" + Utils.sep + "\nPolicy\n" + Utils.sep);
        PolicyManager policyManager = accessControlPolicy.getPolicyManager();

        Map<UserId, Set<RoleId>> policyMap = policyManager.getUserRoleMappings();
        for(UserId userId : policyMap.keySet()) {
            Utils.print(userId.get() + " has role(s): " );
            for(RoleId roleId : policyMap.get(userId)) {
                Utils.print("'" + roleManager.getRole(roleId).getName().get() + "' ");
            }
            Utils.println();
        }
        Utils.println();
    }
}
