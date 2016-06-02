package edu.stanford.protege.metaproject.impl;

import edu.stanford.protege.metaproject.Manager;
import edu.stanford.protege.metaproject.api.*;

import java.io.File;
import java.net.URISyntaxException;
import java.util.Optional;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public final class MetaprojectUtils {
    private final static MetaprojectFactory f = Manager.getFactory();

    private final static int
            OPTIONAL_PORT = 5200,
            HASH_BYTE_SIZE = 24,
            KEY_ITERATIONS = 20000;

    private final static String
            SERVER_URI = "rmi://localhost:5100",
            SERVER_ROOT = "root/data",
            SYSTEM_USER_ID = "system",
            ROOT_USER_ID = "root",
            ROOT_USER_NAME = "Root User",
            ROOT_USER_PASSWORD = "rootpwd",
            GUEST_USER_ID = "guest",
            GUEST_USER_NAME = "Guest User",
            GUEST_USER_PASSWORD = "guestpwd",
            ADMIN_ROLE_ID = "mp-admin",
            ADMIN_ROLE_NAME = "Administrator",
            ADMIN_ROLE_DESCRIPTION = "A user with this role is allowed to do any operation on the server",
            GUEST_ROLE_ID = "mp-guest",
            GUEST_ROLE_NAME = "Guest",
            GUEST_ROLE_DESCRIPTION = "A user with this role is allowed to do any read operation on the server",
            PROJECT_MANAGER_ROLE_ID = "mp-project-manager",
            PROJECT_MANAGER_ROLE_NAME = "Project Manager",
            PROJECT_MANAGER_ROLE_DESCRIPTION = "A user with this role is allowed to create, remove, modify and open a project, " +
                    "as well as to perform any ontology operations",
            UNIVERSAL_PROJECT_ID = "all-projects",
            UNIVERSAL_PROJECT_NAME = "All Projects",
            UNIVERSAL_PROJECT_DESCRIPTION = "A project that represents all projects, i.e., global rights";




    /**
     * Get the default server host
     *
     * @return Server host
     */
    public static Host getServerHost() {
        Host host = null;
        try {
            host = f.getHost(f.getUri(SERVER_URI), Optional.of(f.getPort(OPTIONAL_PORT)));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return host;
    }

    /**
     * Get the default server root directory
     *
     * @return Server root file
     */
    public static File getServerRoot() {
        return new File(SERVER_ROOT);
    }

    /**
     * Get the administrator role, which allows any operation
     *
     * @return Role
     */
    public static Role getAdminRole() {
        return f.getRole(f.getRoleId(ADMIN_ROLE_ID), f.getName(ADMIN_ROLE_NAME),
                f.getDescription(ADMIN_ROLE_DESCRIPTION), Operations.getDefaultOperationsIds());
    }

    /**
     * Get the guest role, which allows any operation of type 'read'
     *
     * @return Role
     */
    public static Role getGuestRole() {
        return f.getRole(f.getRoleId(GUEST_ROLE_ID), f.getName(GUEST_ROLE_NAME), f.getDescription(GUEST_ROLE_DESCRIPTION),
                Operations.getDefaultOperationsIds(OperationType.READ));
    }

    /**
     * Get the project manager role, which allows all ontology operations and the add-project operation
     *
     * @return Role
     */
    public static Role getProjectManagerRole() {
        Set<OperationId> operationIds = Operations.getOntologyOperationsIds();
        operationIds.add(Operations.ADD_PROJECT.getId());
        operationIds.add(Operations.REMOVE_PROJECT.getId());
        operationIds.add(Operations.MODIFY_PROJECT.getId());
        operationIds.add(Operations.OPEN_PROJECT.getId());
        return f.getRole(f.getRoleId(PROJECT_MANAGER_ROLE_ID), f.getName(PROJECT_MANAGER_ROLE_NAME),
                f.getDescription(PROJECT_MANAGER_ROLE_DESCRIPTION), operationIds);
    }

    /**
     * Get the root (administrator) user
     *
     * @return User
     */
    public static User getRootUser() {
        return f.getUser(f.getUserId(ROOT_USER_ID), f.getName(ROOT_USER_NAME), f.getEmailAddress(""));
    }

    /**
     * Get the guest user
     *
     * @return User
     */
    public static User getGuestUser() {
        return f.getUser(f.getUserId(GUEST_USER_ID), f.getName(GUEST_USER_NAME), f.getEmailAddress(""));
    }

    /**
     * Get the universal project that represents all projects (global rights)
     *
     * @return Project
     */
    public static Project getUniversalProject() {
        return f.getProject(getUniversalProjectId(), f.getName(UNIVERSAL_PROJECT_NAME), f.getDescription(UNIVERSAL_PROJECT_DESCRIPTION),
                new File(""), f.getUserId(SYSTEM_USER_ID), Optional.empty());
    }

    /**
     * Get the universal project identifier
     *
     * @return Project identifier
     */
    public static ProjectId getUniversalProjectId() {
        return f.getProjectId(UNIVERSAL_PROJECT_ID);
    }

    /**
     * Get the default authentication credentials for the root user
     *
     * @return Authentication details
     */
    public static AuthenticationDetails getRootUserCredentials() {
        return getAuthenticationDetails(MetaprojectUtils.getRootUser().getId(), f.getPlainPassword(ROOT_USER_PASSWORD));
    }

    /**
     * Get the default authentication credentials for the guest user
     *
     * @return Authentication details
     */
    public static AuthenticationDetails getGuestUserCredentials() {
        return getAuthenticationDetails(MetaprojectUtils.getGuestUser().getId(), f.getPlainPassword(GUEST_USER_PASSWORD));
    }

    private static AuthenticationDetails getAuthenticationDetails(UserId userId, PlainPassword password) {
        checkNotNull(userId);
        checkNotNull(password);
        PasswordHasher h = f.getPasswordHasher();
        SaltedPasswordDigest passwordDigest = h.hash(password, f.getSaltGenerator().generate());
        return f.getAuthenticationDetails(userId, passwordDigest);
    }

    /**
     * Get the default hash byte size
     *
     * @return Integer
     */
    public static int getHashByteSize() {
        return HASH_BYTE_SIZE;
    }

    /**
     * Get the default number of key-stretching iterations performed by the default hash function
     *
     * @return Integer
     */
    public static int getKeyStretchingIterations() {
        return KEY_ITERATIONS;
    }
}
