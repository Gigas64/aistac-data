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
 * The {@code ObjectSingleStore} Class is a class used to Store a single bean.
 *
 * @param <T> The T specialisation class
 *
 * @author 		Darryl Oatridge
 * @version 	2.01 20-July-2010
 * @see ObjectBean
 */
public class ObjectSingleStore<T extends ObjectBean> extends ObjectStore<T> {

    /**
     * The constructor for {@code ObjectSingleStore} Class provides information to set up a connection the the persistence
     * server along with the type of data store to implement. The {@code ConnnectionBean} owner will be the name of the manager
     *
     * @param connection the {@code ConnnectionBean} with where to save data too
     * @param dataOptions optional list of ObjectDataOptionsEnum representing the type of store required
     */
    public ObjectSingleStore(ConnectionBean connection, DataOptionsEnum... dataOptions) {
        super(connection, dataOptions);
    }

    /**
     * The {@code init()} must be called upon instantiation. The method returns this instance to enable chaining with the constructor.
     * @return this instance.
     */
    public ObjectSingleStore<T> init() {
        super.getPersistentData();
        return this;
    }

    /**
     * returns the object T .
     *
     * @return T the object bean or null if not found
     */
    public T getObject() {
        return getObjectInKey(ObjectEnum.SINGLE_KEY.value(), ObjectEnum.DEFAULT_ID.value());
    }

    /**
     * saves an object to the store and persists the object to disk. If the object
     * exists, the object replaces the existing one and modifies the
     * ObjectBean modified parameter. The object identifier is always the DEAFULT_ID
     *
     * @param obj the object to be set
     * @return The object saved
     */
    public T setObject(T obj) {
        T rtnObject = obj.clone(ObjectEnum.DEFAULT_ID.value());
        return (super.setObjectInKey(ObjectEnum.SINGLE_KEY.value(), rtnObject));
    }
}
