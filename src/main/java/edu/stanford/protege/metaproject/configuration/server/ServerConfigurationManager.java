package edu.stanford.protege.metaproject.configuration.server;

import edu.stanford.protege.metaproject.configuration.ConfigurationManager;
import edu.stanford.protege.metaproject.idgeneration.TermIdentifierGenerator;
import edu.stanford.protege.metaproject.project.Project;
import edu.stanford.protege.metaproject.role.Role;
import edu.stanford.protege.metaproject.user.User;

import java.util.Set;

/**
 * Manager for server-side configurations
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface ServerConfigurationManager extends ConfigurationManager {

    /**
     * Get the hostname of the server
     *
     * @return Server hostname
     */
    HostId getHostId();

    /**
     * Set the hostname of the server
     *
     * @param hostName  Server host name
     */
    void setHostId(HostId hostName);

    /**
     * Get the set of all projects
     *
     * @return Set of projects
     */
    Set<Project> getProjects();

    /**
     * Get the set of all roles
     *
     * @return Set of roles
     */
    Set<Role> getRoles();

    /**
     * Get the set of all users
     *
     * @return Set of users
     */
    Set<User> getUsers();

    /**
     * Get the term identifier generator
     *
     * @return Term identifier generator instance
     */
    TermIdentifierGenerator getTermIdentifierGenerator();

    /**
     * Set the unique term identifier generator
     *
     * @param idGenerator  Unique term identifier generator
     */
    void setTermIdentifierGenerator(TermIdentifierGenerator idGenerator);

}
