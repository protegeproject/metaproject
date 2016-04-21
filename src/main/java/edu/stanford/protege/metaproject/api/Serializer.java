package edu.stanford.protege.metaproject.api;

import edu.stanford.protege.metaproject.api.exception.ObjectConversionException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Reader;

/**
 * A generic serializer that can be used to parse a file into a Java object representation, and,
 * analogously, write out some string-representation of the specified Java object.
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface Serializer<E> {

    /**
     * Get the instance of the serializer
     *
     * @return Serializer instance
     */
    E getInstance();

    /**
     * Process the stream in the given reader and return the desired Java class instance
     *
     * @param reader    Reader
     * @param cls   Class
     * @param <T>   Type
     * @return Instance of the specified class
     * @throws FileNotFoundException    Specified file was not found
     * @throws ObjectConversionException   JSON object could not be converted to a Java object
     */
    <T> T parse(Reader reader, Class<T> cls) throws FileNotFoundException, ObjectConversionException;

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
    <T> T parse(File f, Class<T> cls) throws FileNotFoundException, ObjectConversionException;

    /**
     * Write into a string a representation of the given object
     *
     * @param obj   Object to get a string representation of
     * @return String representation of specified object
     */
    String write(Object obj);

}
