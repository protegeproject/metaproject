package edu.stanford.protege.metaproject.api;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.IOException;

/**
 * @author Rafael Gonçalves <br>
 * Center for Biomedical Informatics Research <br>
 * Stanford University
 */
public interface ConfigurationWriter {

    /**
     * Set the server configuration managed by this object, and saves it to the specified file
     *
     * @param configuration Configuration
     * @param outputFile    Output file
     * @throws IOException  IO exception
     */
    void saveConfiguration(@Nonnull ServerConfiguration configuration, @Nonnull File outputFile) throws IOException;

}
