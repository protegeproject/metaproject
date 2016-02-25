package edu.stanford.protege.metaproject.api;

import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import edu.stanford.protege.metaproject.api.exception.ObjectConversionException;

import java.io.File;
import java.io.FileNotFoundException;

/**
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
     * Parse a given file and return the desired Java class instance
     *
     * @param f File
     * @param cls   Class
     * @param <T>   Type
     * @return Instance of the specified class
     * @throws FileNotFoundException    Specified file was not found
     * @throws JsonSyntaxException  JSON syntax exception
     * @throws JsonIOException  JSON IO exception
     * @throws ObjectConversionException   JSON object could not be converted to a Java object
     */
    <T> T parse(File f, Class<T> cls) throws FileNotFoundException, JsonSyntaxException, JsonIOException, ObjectConversionException;

}
