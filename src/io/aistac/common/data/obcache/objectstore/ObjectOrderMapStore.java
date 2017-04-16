/**
 * @(#)ObjectStore.java
 *
 * Copyright:	Copyright (c) 2009
 * Company:		Oathouse.com Ltd
 */
package io.aistac.common.data.obcache.objectstore;

import io.aistac.common.canonical.data.ObjectBean;
import io.aistac.common.api.sockets.handler.connections.ConnectionBean;

/**
 * The {@code ObjectOrderMapStore} Class is a class used to Store other objects and provide a central
 * management class for storing Beans. Beans stored must extend ObjectBean. In addition
 * {@code ObjectOrderMapStore} also maintains the position of the stored objects and allows
 * the objects to be placed at specific index within the store. the order of the store is then
 * kept even through persistence.
 *
 * @param <T> The T specialisation class
 *
 * @author 		Darryl Oatridge
 * @version 	1.00 12-Jun-2010
 * @see ObjectBean
 */
public class ObjectOrderMapStore<T extends ObjectBean> extends ObjectMapStore<T> {

    /**
     * The constructor for {@code ObjectOrderMapStore} Class provides information to set up a connection the the persistence
     * server along with the type of data store to implement. The {@code ConnnectionBean} owner will be the name of the manager
     *
     * @param connection the {@code ConnnectionBean} with where to save data too
     * @param dataOptions [optional] object data
     */
    public ObjectOrderMapStore(ConnectionBean connection, DataOptionsEnum... dataOptions) {
        super(connection, dataOptions);
    }

    /**
     * The {@code init()} must be called upon instantiation. The method returns this instance to enable chaining with the constructor.
     * @return this instance.
     */
    @Override
    public ObjectOrderMapStore<T> init() {
        super.getPersistentData();
        return this;
    }

    /**
     *  retrieves an object at the specific index in a List referenced by the key
     * <p>
     * Optionally you can include the enumerations ARCHIVE or PERSIST from the ObjectDataOptionsEnum.
     * ARCHIVE will 'get' from current and archive data held on disk.
     * PERISTENCE will 'get' current data from disk even if store type is MEMORY
     * </p>
     *
     * @param key reference value to a list of objects
     * @param index the index of the object
     * @return the object T or null if not found
     */
    public T getObjectAt(int key, int index) {
        LOGGER.debug(DATA, "getObjectAt( key = " + key + ", index = " + index + ")");
        return getObjectInKeyAt(key, index);
    }

    /**
     * gets the first object in the list referenced by the key
     * <p>
     * Optionally you can include the enumerations ARCHIVE or PERSIST from the ObjectDataOptionsEnum.
     * ARCHIVE will 'get' from current and archive data held on disk.
     * PERISTENCE will 'get' current data from disk even if store type is MEMORY
     * </p>
     *
     * @param key reference value to a list of objects
     * @return the object T or null if not found
     */
    public T getFirstObject(int key) {
        LOGGER.debug(DATA, "getFirstObject( key = " + key + ")");
        return getFirstObjectInKey(key);
    }

    /**
     * gets the last object in the list referenced by the key
     * <p>
     * Optionally you can include the enumerations ARCHIVE or PERSIST from the ObjectDataOptionsEnum.
     * ARCHIVE will 'get' from current and archive data held on disk.
     * PERISTENCE will 'get' current data from disk even if store type is MEMORY
     * </p>
     *
     * @param key reference value to a list of objects
     * @return the object T or null if not found
     */
    public T getLastObject(int key) {
        LOGGER.debug(DATA, "getLastObject( key = " + key + ")");
        return getLastObjectInKey(key);
    }

    /**
     * gets the index of an object referenced by the key where the object has the identifier
     * passed
     * <p>
     * Optionally you can include the enumerations ARCHIVE or PERSIST from the ObjectDataOptionsEnum.
     * ARCHIVE will 'get' from current and archive data held on disk.
     * PERISTENCE will 'get' current data from disk even if store type is MEMORY
     * </p>
     *
     * @param key reference value to a list of objects
     * @param identifier the identifier of the object
     * @return the index of the object or -1 if not found
     */
    public int getIndexOf(int key, int identifier) {
        LOGGER.debug(DATA, "getIndexOf( key = " + key + ", id = " + identifier + ")");
        return getIndexInKeyOf(key, identifier);
    }

    /**
     * Sets an object to the first position in the list held within a key
     *
     * @param key reference value to a list of objects
     * @param ob the object to be stored
     * @return The object that was set
     */
    public T setFirstObject(int key, T ob) {
        LOGGER.debug(DATA, "setFirstObject( key = " + key + ", id = " + ob.getIdentifier() + ")");
        if(ob == null) {
            throw new NullPointerException("Unable to save object. The object passed is null");
        }
        return (setFirstObjectInKey(key, ob));
    }

    /**
     * Sets an object to the last position in the list held within a key
     *
     * @param key reference value to a list of objects
     * @param ob the object to be stored
     * @return The object that was set
     */
    public T setLastObject(int key, T ob) {
        LOGGER.debug(DATA, "setLastObject( key = " + key + ", id = " + ob.getIdentifier() + ")");
        if(ob == null) {
            throw new NullPointerException("Unable to save object. The object passed is null");
        }
        return (setLastObjectInKey(key, ob));
    }

    /**
     * Sets an object to the specified position index in the list held within a key
     *
     * @param key reference value to a list of objects
     * @param ob the object to be stored
     * @param index the index where the object should be placed
     * @return The object that was set
     */
    public T setObjectAt(int key, T ob, int index) {
        LOGGER.debug(DATA, "setObjectAt( key = " + key + ", index = " + index + ")");
        if(ob == null) {
            throw new NullPointerException("Unable to save object. The object passed is null");
        }
        return (setObjectInKeyAt(key, ob, index));
    }

    /**
     * This method allows the creation of a key but with no objects in it.
     *
     * @param key the key to set
     */
    @Override
    public void setKey(int key) {
        LOGGER.debug(DATA, "setKey( key = " + key + ")");
        super.setKey(key);
    }
}
