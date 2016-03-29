package edu.stanford.protege.metaproject.api;

import edu.stanford.protege.metaproject.api.exception.MetaprojectNotLoadedException;
import edu.stanford.protege.metaproject.api.exception.ObjectConversionException;
import edu.stanford.protege.metaproject.api.exception.ServerConfigurationNotLoadedException;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface ConfigurationManager {

    /**
     * Load a server configuration from a file
     *
     * @param f Server configuration file
     * @return Server configuration
     * @throws FileNotFoundException    File specified was not found
     * @throws ObjectConversionException   JSON object could not be converted to a Java object
     */
    ServerConfiguration loadServerConfiguration(File f) throws FileNotFoundException, ObjectConversionException;

    /**
     * Load a client configuration from a file
     *
     * @param f Client configuration file
     * @return Client configuration
     * @throws FileNotFoundException    File specified was not found
     * @throws ObjectConversionException   JSON object could not be converted to a Java object
     */
    ClientConfiguration loadClientConfiguration(File f) throws FileNotFoundException, ObjectConversionException;

    /**
     * Get the server configuration loaded by this manager
     *
     * @return Server configuration
     * @throws ServerConfigurationNotLoadedException    Server configuration has not been loaded
     */
    ServerConfiguration getServerConfiguration() throws ServerConfigurationNotLoadedException;

    /**
     * Get the client configuration for the user with the specified user identifier
     *
     * @param userId    User identifier
     * @return Client configuration
     * @throws ServerConfigurationNotLoadedException    Server configuration has not been loaded
     * @throws MetaprojectNotLoadedException    Metaproject has not been loaded
     */
    ClientConfiguration getClientConfiguration(UserId userId) throws MetaprojectNotLoadedException, ServerConfigurationNotLoadedException;

}
