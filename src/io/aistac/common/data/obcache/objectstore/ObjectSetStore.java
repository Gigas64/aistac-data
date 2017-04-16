/**
 * @(#)ObjectSetStore.java
 *
 * Copyright:	Copyright (c) 2009
 * Company:		Oathouse.com Ltd
 */
package io.aistac.common.data.obcache.objectstore;

import io.aistac.common.canonical.data.ObjectBean;
import io.aistac.common.canonical.data.ObjectEnum;
import io.aistac.common.api.sockets.handler.connections.ConnectionBean;
import java.util.List;
import java.util.Set;

/**
 * The {@code ObjectSetStore} Class is a class used to Store other objects and provide a central
 * management class for storing Beans. Beans stored must extend ObjectBean. ObjectSetStore is an
 * implementation of Object Store exposing only a single Map key and should be used when only
 * a single set of related data is to be stored.
 *
 * @param <T> The T specialisation class
 *
 * @author 		Darryl Oatridge
 * @version 	2.01 20-July-2010
 * @see ObjectBean
 */
public class ObjectSetStore<T extends ObjectBean> extends ObjectStore<T> {

    /**
     * The constructor for {@code ObjectSetStore} Class provides information to set up a connection the the persistence
     * server along with the type of data store to implement. The {@code ConnnectionBean} owner will be the name of the manager
     *
     * @param connection the {@code ConnnectionBean} with where to save data too
     * @param dataOptions [optional] object data options
     */
    public ObjectSetStore(ConnectionBean connection, DataOptionsEnum... dataOptions) {
        super(connection, dataOptions);
    }

    /**
     * The {@code init()} must be called upon instantiation. The method returns this instance to enable chaining with the constructor.
     * @return this instance.
     */
    public ObjectSetStore<T> init() {
        super.getPersistentData();
        return this;
    }

    /**
     * returns the object T with the specified identifier.
     * <p>
     * Optionally you can include the enumerations ARCHIVE or PERSIST from the ObjectDataOptionsEnum.
     * ARCHIVE will 'get' from current and archive data held on disk.
     * PERISTENCE will 'get' current data from disk even if store type is MEMORY
     * </p>
     *
     * @param identifier the identifier of the object T
     * @return T the object bean or null if not found
     */
    public T getObject(int identifier) {
        return getObjectInKey(ObjectEnum.SINGLE_KEY.value(), identifier);
    }

    /**
     * returns all the object in the store.
     * <p>
     * Optionally you can include the enumerations ARCHIVE or PERSIST from the ObjectDataOptionsEnum.
     * ARCHIVE will 'get' from current and archive data held on disk.
     * PERISTENCE will 'get' current data from disk even if store type is MEMORY
     * </p>
     *
     * @return LinkedList of T
     */
    public List<T> getAllObjects() {
        return (super.getAllObjectsInKey(ObjectEnum.SINGLE_KEY.value()));
    }

    /**
     * returns all the identifiers currently being held.
     * <p>
     * Optionally you can include the enumerations ARCHIVE or PERSIST from the ObjectDataOptionsEnum.
     * ARCHIVE will 'get' from current and archive data held on disk.
     * PERISTENCE will 'get' current data from disk even if store type is MEMORY
     * </p>
     *
     * @return a set of Integer Id's
     */
    public Set<Integer> getAllIdentifier() {
        return (super.getAllIdentifiersInKey(ObjectEnum.SINGLE_KEY.value()));
    }

    /**
     * checks to see if a given object identifier is present
     * <p>
     * Optionally you can include the enumerations ARCHIVE or PERSIST from the ObjectDataOptionsEnum.
     * ARCHIVE will 'get' from current and archive data held on disk.
     * PERISTENCE will 'get' current data from disk even if store type is MEMORY
     * </p>
     *
     * @param identifier the identifier of the object T
     * @return true if found, false if not found
     */
    public boolean isIdentifier(int identifier) {
        if(getAllIdentifier().contains(identifier)) {
            return (true);
        }
        return (false);
    }

    /**
     * saves an object to the store and persists the object to disk. If the object
     * identifier exists, the object replaces the existing one and modifies the
     * ObjectBean modified parameter.
     *
     * @param ob the object to be saved
     * @return The object saved
     */
    public T setObject(T ob) {
        if(ob == null) {
            throw new NullPointerException("Unable to save object. The object passed is null");
        }
        return (super.setObjectInKey(ObjectEnum.SINGLE_KEY.value(), ob));
    }

    /**
     * Removes a object with the provided identifier from the memory store and
     * the persistence store.
     *
     * @param identifier the id of the object to be removed
     * @return the object that was deleted
     */
    public T removeObject(int identifier) {
        return super.removeObjectInKey(ObjectEnum.SINGLE_KEY.value(), identifier);
    }

}
