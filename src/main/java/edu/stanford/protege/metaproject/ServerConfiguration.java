package edu.stanford.protege.metaproject;

import java.util.Set;

/**
 * Manager for server-side configurations
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface ServerConfiguration extends Configuration {

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