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


/**
 * The {@code ObjectOrderSetStore} Class is a class used to Store other objects and provide a central
 * management class for storing Beans. Beans stored must extend ObjectBean. In addition
 * {@code ObjectOrderSetStore} also maintains the position of the stored objects and allows
 * the objects to be placed at specific index within the store. the order of the store is then
 * kept even through persistence. {@code ObjectOrderSetStore} is an
 * implementation of ObjectDB exposing only a single Map key and should be used when only
 * a single set of related data is to be stored. This extends ObjectSetStore.
 *
 * @param <T> The T specialisation class
 *
 * @author 		Darryl Oatridge
 * @version 	2.01 20-July-2010
 * @see ObjectBean
 */
public class ObjectOrderSetStore<T extends ObjectBean> extends ObjectSetStore<T> {

    /**
     * The constructor for {@code ObjectSetStore} Class provides information to set up a connection the the persistence
     * server along with the type of data store to implement. The {@code ConnnectionBean} owner will be the name of the manager
     *
     * @param connection the {@code ConnnectionBean} with where to save data too
     * @param dataOptions [optional] object data
     */
    public ObjectOrderSetStore(ConnectionBean connection, DataOptionsEnum... dataOptions) {
        super(connection, dataOptions);
    }

    /**
     * The {@code init()} must be called upon instantiation. The method returns this instance to enable chaining with the constructor.
     * @return this instance.
     */
    public ObjectOrderSetStore<T> init() {
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
     * @param index the index of the object
     * @return the object T or null if not found
     */
    public T getObjectAt(int index) {
        return getObjectInKeyAt(ObjectEnum.SINGLE_KEY.value(), index);
    }

    /**
     * gets the first object in the list referenced by the key
     * <p>
     * Optionally you can include the enumerations ARCHIVE or PERSIST from the ObjectDataOptionsEnum.
     * ARCHIVE will 'get' from current and archive data held on disk.
     * PERISTENCE will 'get' current data from disk even if store type is MEMORY
     * </p>
     *
     * @return the object T or null if not found
     */
    public T getFirstObject() {
        return getFirstObjectInKey(ObjectEnum.SINGLE_KEY.value());
    }

    /**
     * gets the last object in the list referenced by the key
     * <p>
     * Optionally you can include the enumerations ARCHIVE or PERSIST from the ObjectDataOptionsEnum.
     * ARCHIVE will 'get' from current and archive data held on disk.
     * PERISTENCE will 'get' current data from disk even if store type is MEMORY
     * </p>
     *
     * @return the object T or null if not found
     */
    public T getLastObject() {
        return getLastObjectInKey(ObjectEnum.SINGLE_KEY.value());
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
     * @param identifier the identifier of the object
     * @return the index of the object or -1 if not found
     */
    public int getIndexOf(int identifier) {
        return getIndexInKeyOf(ObjectEnum.SINGLE_KEY.value(), identifier);
    }

    /**
     * Sets an object to the first position in the list held within a key
     *
     * @param ob the object to be stored
     * @return T
     */
    public T setFirstObject(T ob) {
        if(ob == null) {
            throw new NullPointerException("Unable to save object. The object passed is null");
        }
        return (setFirstObjectInKey(ObjectEnum.SINGLE_KEY.value(), ob));
    }

    /**
     * Sets an object to the last position in the list held within a key
     *
     * @param ob the object to be stored
     * @return T
     */
    public T setLastObject(T ob) {
        if(ob == null) {
            throw new NullPointerException("Unable to save object. The object passed is null");
        }
        return (setLastObjectInKey(ObjectEnum.SINGLE_KEY.value(), ob));
    }

    /**
     * Sets an object to the specified position index in the list held within a key
     *
     * @param ob the object to be stored
     * @param index the index where the object should be placed
     * @return T
     */
    public T setObjectAt(T ob, int index) {
        if(ob == null) {
            throw new NullPointerException("Unable to save object. The object passed is null");
        }
        return (setObjectInKeyAt(ObjectEnum.SINGLE_KEY.value(), ob, index));
    }
}
