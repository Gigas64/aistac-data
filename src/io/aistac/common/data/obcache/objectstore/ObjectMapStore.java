/**
 * @(#)ObjectMapStore.java
 *
 * Copyright:	Copyright (c) 2009
 * Company:		Oathouse.com Ltd
 */
package io.aistac.common.data.obcache.objectstore;

import io.aistac.common.canonical.data.ObjectBean;
import io.aistac.common.api.sockets.handler.connections.ConnectionBean;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * The {@code ObjectMapStore} Class is a class used to Store other objects and provide a central
 * management class for storing Beans. Beans stored must extend ObjectBean. ObjectSetStore is an
 * implementation of Object Store exposing set of ObjectBeans that can be grouped into key referenced
 * groups.
 *
 * @param <T> The T specialisation class
 *
 * @author 		Darryl Oatridge
 * @version 	2.01 20-July-2010
 * @see ObjectBean
 */
public class ObjectMapStore<T extends ObjectBean> extends ObjectStore<T> {

    /**
     * The constructor for {@code ObjectMapStore} Class provides information to set up a connection the the persistence
     * server along with the type of data store to implement. The {@code ConnnectionBean} owner will be the name of the manager
     *
     * @param connection the {@code ConnnectionBean} with where to save data too
     * @param dataOptions [optional] object data
     */
    public ObjectMapStore(ConnectionBean connection, DataOptionsEnum... dataOptions) {
        super(connection, dataOptions);
    }

    /**
     * The {@code init()} must be called upon instantiation. The method returns this instance to enable chaining with the constructor.
     * @return this instance.
     */
    public ObjectMapStore<T> init() {
        super.getPersistentData();
        return this;
    }

    /**
     * returns the object T with the specified identifier and key.
     * <p>
     * Optionally you can include the enumerations ARCHIVE or PERSIST from the ObjectDataOptionsEnum.
     * ARCHIVE will 'get' from current and archive data held on disk.
     * PERISTENCE will 'get' current data from disk even if store type is MEMORY
     * </p>
     *
     * @param key reference value to a set of identifiers
     * @param identifier the identifier of the object T
     * @return T the object bean
     */
    public T getObject(int key, int identifier) {
        LOGGER.debug(DATA, "getObject( key = " + key + ", id = " + identifier + ")");
        return getObjectInKey(key, identifier);
    }

    /**
     * If the key is unknown this will search all the keys to find the first occurrence of
     * object with the identifier.
     * <p>
     * Optionally you can include the enumerations ARCHIVE or PERSIST from the ObjectDataOptionsEnum.
     * ARCHIVE will 'get' from current and archive data held on disk.
     * PERISTENCE will 'get' current data from disk even if store type is MEMORY
     * </p>
     *
     * @param identifier the identifier of the object T
     * @return The first occurrence of T object or null if not found
     */
    public T getObject(int identifier) {
        LOGGER.debug(DATA, "getObject( id = " + identifier + ")");
        for(int key : getAllKeysInMap()) {
            if(getAllIdentifiersInKey(key).contains(identifier)) {
                return (getObject(key, identifier));
            }
        }
        return null;
    }

    /**
     * returns all objects for a specified key
     * <p>
     * Optionally you can include the enumerations ARCHIVE or PERSIST from the ObjectDataOptionsEnum.
     * ARCHIVE will 'get' from current and archive data held on disk.
     * PERISTENCE will 'get' current data from disk even if store type is MEMORY
     * </p>
     *
     * @param key reference value to a set of identifiers
     * @return LinkedList of T
     */
    public List<T> getAllObjects(int key) {
        LOGGER.debug(DATA, "getAllObject( key = " + key + ")");
        return (super.getAllObjectsInKey(key));
    }

    /**
     * returns all objects in every key. This returns the entire store
     * <p>
     * Optionally you can include the enumerations ARCHIVE or PERSIST from the ObjectDataOptionsEnum.
     * ARCHIVE will 'get' from current and archive data held on disk.
     * PERISTENCE will 'get' current data from disk even if store type is MEMORY
     * </p>
     *
     * @return LinkedList of T
     */
    public List<T> getAllObjects() {
        LOGGER.debug(DATA, "getAllObject()");
        LinkedList<T> rtnList = new LinkedList<>();
        super.getAllKeysInMap().stream().forEach((key) -> {
            rtnList.addAll(super.getAllObjectsInKey(key));
        });
        return rtnList;
    }

    /**
     * Returns all the keys that are currently being held
     * <p>
     * Optionally you can include the enumerations ARCHIVE or PERSIST from the ObjectDataOptionsEnum.
     * ARCHIVE will 'get' from current and archive data held on disk.
     * PERISTENCE will 'get' current data from disk even if store type is MEMORY
     * </p>
     *
     * @return set of integers
     */
    public Set<Integer> getAllKeys() {
        LOGGER.debug(DATA, "getAllKeys()");
        return (super.getAllKeysInMap());
    }

    /**
     * returns all the identifiers currently being held. This returns every identifier under
     * every key.
     * <p>
     * Optionally you can include the enumerations ARCHIVE or PERSIST from the ObjectDataOptionsEnum.
     * ARCHIVE will 'get' from current and archive data held on disk.
     * PERISTENCE will 'get' current data from disk even if store type is MEMORY
     * </p>
     *
     * @return a set of Integer Id values
     */
    public Set<Integer> getAllIdentifier() {
        LOGGER.debug(DATA, "getAllIdentifiers()");
        Set<Integer> allIdentifier = new ConcurrentSkipListSet<>();
        super.getAllKeysInMap().stream().forEach((key) -> {
            allIdentifier.addAll(super.getAllIdentifiersInKey(key));
        });
        return (allIdentifier);
    }

    /**
     * gets all the identifers for a particular key
     * <p>
     * Optionally you can include the enumerations ARCHIVE or PERSIST from the ObjectDataOptionsEnum.
     * ARCHIVE will 'get' from current and archive data held on disk.
     * PERISTENCE will 'get' current data from disk even if store type is MEMORY
     * </p>
     *
     * @param key reference value to a set of identifiers
     * @return a set of integer id values
     */
    public Set<Integer> getAllIdentifier(int key) {
        LOGGER.debug(DATA, "getAllIdentifier( key = " + key + ")");
        return (super.getAllIdentifiersInKey(key));
    }

    /**
     * returns a set of all keys where the identifier is found within it.
     * <p>
     * Optionally you can include the enumerations ARCHIVE or PERSIST from the ObjectDataOptionsEnum.
     * ARCHIVE will 'get' from current and archive data held on disk.
     * PERISTENCE will 'get' current data from disk even if store type is MEMORY
     * </p>
     *
     * @param identifier the identifier of the object T
     * @return set of key values where the identifier is found in the key.
     */
    public Set<Integer> getAllKeysForIdentifier(int identifier) {
        LOGGER.debug(DATA, "getAllKeysForIdentifier( id = " + identifier + ")");
        Set<Integer> rtnSet = new ConcurrentSkipListSet<>();
        getAllKeysInMap().stream().filter((key) -> (getAllIdentifiersInKey(key).contains(identifier))).forEach((key) -> {
            rtnSet.add(key);
        });
        return rtnSet;
    }

    /**
     * checks to see if a given identifier is present in all keys
     * <p>
     * Optionally you can include the enumerations ARCHIVE or PERSIST from the ObjectDataOptionsEnum.
     * ARCHIVE will 'get' from current and archive data held on disk.
     * PERISTENCE will 'get' current data from disk even if store type is MEMORY
     * </p>
     *
     * @param identifier identifier the identifier of the object T
     * @return true if found, false if not
     */
    public boolean isIdentifier(int identifier) {
        if(getAllIdentifier().contains(identifier)) {
            return (true);
        }
        return (false);
    }

    /**
     * returns true if the identifier exists within a certain key.
     * <p>
     * Optionally you can include the enumerations ARCHIVE or PERSIST from the ObjectDataOptionsEnum.
     * ARCHIVE will 'get' from current and archive data held on disk.
     * PERISTENCE will 'get' current data from disk even if store type is MEMORY
     * </p>
     *
     * @param key reference value to a set of identifiers
     * @param identifier the identifier of the object T
     * @return true if found and false if not
     */
    public boolean isIdentifier(int key, int identifier) {
        if(getAllIdentifier(key).contains(identifier)) {
            return (true);
        }
        return (false);
    }

    /**
     * saves an object to the store and persists the object to disk. If the object
     * identifier exists, the object replaces the existing one and modifies the
     * ObjectBean modified parameter.
     *
     * @param key reference value to a set of identifiers
     * @param ob the object bean
     * @return the object set
     */
    public T setObject(int key, T ob)  {
        LOGGER.debug(DATA, "setObject( key = " + key + ", id = " + ob.getIdentifier() + ")");
        if(ob == null) {
            throw new NullPointerException("Unable to save object. The object passed is null");
        }
        return (super.setObjectInKey(key, ob));
    }

    /**
     * Removes a object with the provided key and identifier from the memory store and
     * the persistence store.
     *
     * @param key reference value to a set of identifiers
     * @param identifier the identifier of the object T
     * @return the object T that was deleted
     */
    public T removeObject(int key, int identifier) {
        LOGGER.debug(DATA, "removeObject( key = " + key + ", id = " + identifier + ")");
        return super.removeObjectInKey(key, identifier);
    }

    /**
     * Removes a whole key and all the objects within that key from the memory store
     * and from persistence
     *
     * @param key reference value to a set of identifiers
     */
    public void removeKey(int key) {
        LOGGER.debug(DATA, "removeKey( key = " + key + ")");
        super.removeAllObjectsInKey(key);
    }

}
