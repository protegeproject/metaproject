package edu.stanford.protege.metaproject.api;

import edu.stanford.protege.metaproject.api.exception.ObjectConversionException;
import edu.stanford.protege.metaproject.api.exception.ServerConfigurationNotLoadedException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;

/**
 * A manager for a single server configuration.
 *
 * @author Rafael Gonçalves <br>
 * Center for Biomedical Informatics Research <br>
 * Stanford University
 */
public interface ConfigurationManager extends ConfigurationTransformer {

    /**
     * Load a server configuration from a file
     *
     * @param f Server configuration file
     * @return Server configuration
     * @throws FileNotFoundException    File specified was not found
     * @throws ObjectConversionException   JSON object could not be converted to a Java object
     */
    ServerConfiguration loadConfiguration(File f) throws FileNotFoundException, ObjectConversionException;

    /**
     * Load a server configuration from a reader
     *
     * @param reader Input reader
     * @return Server configuration
     * @throws ObjectConversionException   JSON object could not be converted to a Java object
     */
    ServerConfiguration loadConfiguration(Reader reader) throws ObjectConversionException;

    /**
     * Get the server configuration loaded by this manager
     *
     * @return Server configuration
     * @throws ServerConfigurationNotLoadedException    Server configuration has not been loaded
     */
    ServerConfiguration getConfiguration() throws ServerConfigurationNotLoadedException;

    /**
     * Set the active configuration to the given one
     *
     * @param configuration Server configuration
     */
    void setActiveConfiguration(ServerConfiguration configuration);

    /**
     * Set the server configuration managed by this object, and saves it to the specified file
     *
     * @param outputFile    Output file
     * @throws IOException  IO exception
     */
    void saveConfiguration(File outputFile) throws IOException;

    /**
     * Get the policy factory
     *
     * @return Policy factory
     */
    PolicyFactory getPolicyFactory();

}
