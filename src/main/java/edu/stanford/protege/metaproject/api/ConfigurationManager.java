package edu.stanford.protege.metaproject.api;

import edu.stanford.protege.metaproject.api.exception.ObjectConversionException;
import edu.stanford.protege.metaproject.api.exception.ServerConfigurationNotLoadedException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * A manager for a single server configuration, from which client configurations
 * can be extrapolated. This manager allows loading from and saving configurations
 * to files.
 *
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
     * Get the server configuration loaded by this manager
     *
     * @return Server configuration
     * @throws ServerConfigurationNotLoadedException    Server configuration has not been loaded
     */
    ServerConfiguration getServerConfiguration() throws ServerConfigurationNotLoadedException;

    /**
     * Set the server configuration managed by this object
     *
     * @param serverConfiguration   Server configuration
     */
    void setServerConfiguration(ServerConfiguration serverConfiguration);

    /**
     * Save the server configuration currently set in this manager
     *
     * @param outputFile    Output file
     * @throws IOException  IO exception
     * @throws ServerConfigurationNotLoadedException    Server configuration has not been loaded
     */
    void saveServerConfiguration(File outputFile) throws IOException, ServerConfigurationNotLoadedException;

}
