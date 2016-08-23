package edu.stanford.protege.metaproject.api;

import edu.stanford.protege.metaproject.api.exception.ObjectConversionException;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.Reader;

/**
 * A generic serializer that can be used to parse a file into a Java object representation, and,
 * analogously, write out some string-representation of the specified Java object.
 *
 * @author Rafael Gonçalves <br>
 * Center for Biomedical Informatics Research <br>
 * Stanford University
 */
public interface Serializer {

    /**
     * Process the stream in the given reader and return the desired Java class instance
     *
     * @param reader    Reader
     * @param cls   Class
     * @param <T>   Type
     * @return Instance of the specified class
     * @throws ObjectConversionException   JSON object could not be converted to a Java object
     */
    @Nonnull
    <T> T parse(@Nonnull Reader reader, @Nonnull Class<T> cls) throws ObjectConversionException;

    /**
     * Parse a given file and return the desired Java class instance
     *
     * @param f File
     * @param cls   Class
     * @param <T>   Type
     * @return Instance of the specified class
     * @throws FileNotFoundException    Specified file was not found
     * @throws ObjectConversionException   JSON object could not be converted to a Java object
     */
    @Nonnull
    <T> T parse(@Nonnull File f, @Nonnull Class<T> cls) throws FileNotFoundException, ObjectConversionException;

    /**
     * Write into a string a representation of the given object
     *
     * @param obj   Object to getProject a string representation of
     * @param cls   Class
     * @return String representation of specified object
     */
    @Nonnull
    String write(@Nonnull Object obj, @Nonnull Class cls);

}
