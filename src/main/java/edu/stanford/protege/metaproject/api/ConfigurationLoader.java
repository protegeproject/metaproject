package edu.stanford.protege.metaproject.api;

import edu.stanford.protege.metaproject.api.exception.ObjectConversionException;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.Reader;

/**
 * @author Rafael Gonçalves <br>
 * Center for Biomedical Informatics Research <br>
 * Stanford University
 */
public interface ConfigurationLoader {

    /**
     * Load a server configuration from a file
     *
     * @param f Server configuration file
     * @return Server configuration
     * @throws FileNotFoundException    File specified was not found
     * @throws ObjectConversionException   JSON object could not be converted to a Java object
     */
    @Nonnull
    ServerConfiguration loadConfiguration(@Nonnull File f) throws FileNotFoundException, ObjectConversionException;

    /**
     * Load a server configuration from a reader
     *
     * @param reader Input reader
     * @return Server configuration
     * @throws ObjectConversionException   JSON object could not be converted to a Java object
     */
    @Nonnull
    ServerConfiguration loadConfiguration(@Nonnull Reader reader) throws ObjectConversionException;

}
