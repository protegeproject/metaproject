package edu.stanford.protege.metaproject.api;

import edu.stanford.protege.metaproject.api.exception.IdAlreadyInUseException;
import edu.stanford.protege.metaproject.api.exception.UnknownMetaprojectObjectIdException;

import java.util.Set;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface Registry<T extends MetaprojectObject> {

    /**
     * Add the given object to the registry
     *
     * @param obj   Object to add to the registry
     * @throws IdAlreadyInUseException  Object
     */
    void add(T obj) throws IdAlreadyInUseException;

    /**
     * Remove the specified object from the registry
     *
     * @param obj   Object to remove from registry
     */
    void remove(T obj);

    /**
     * Get all the entries in the registry
     *
     * @return Set of registry entries
     */
    Set<T> getEntries();

    /**
     * Get the metaproject object with the given identifier
     *
     * @param id    Metaproject object identifier
     * @param <E>   Type of identifier
     * @return Metaproject object
     * @throws UnknownMetaprojectObjectIdException  The specified identifier is not recognized
     */
    <E extends MetaprojectObjectId> T get(E id) throws UnknownMetaprojectObjectIdException;

    /**
     * Check whether the registry contains the given entry
     *
     * @param obj   Registry entry
     * @return true if the specified entry exists in the registry, false otherwise
     */
    boolean contains(T obj);

    /**
     * Check whether the registry contains an entry with the given identifier
     *
     * @param id    Entry identifier
     * @param <E>   Metaproject object identifier type
     * @return true if there is an entry with the specified identifier, false otherwise
     */
    <E extends MetaprojectObjectId> boolean contains(E id);

    /**
     * Update the object with the specified identifier with the given, updated object
     *
     * @param id    Project identifier
     * @param newObj    New project instance
     * @param <E>   Metaproject object identifier type
     * @throws UnknownMetaprojectObjectIdException  The specified identifier is not recognized
     */
    <E extends MetaprojectObjectId> void update(E id, T newObj) throws UnknownMetaprojectObjectIdException;

}
