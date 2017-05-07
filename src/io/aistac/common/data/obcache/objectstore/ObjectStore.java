 /*
 * @(#)ObjectStore.java
 *
 * Copyright:	Copyright (c) 2016
 * Company:		Oathouse.com Ltd
 */
package io.aistac.common.data.obcache.objectstore;

import io.aistac.common.api.sockets.handler.connections.ConnectionBean;
import io.aistac.common.api.sockets.transport.TransportBean;
import io.aistac.common.api.sockets.transport.TransportQueueInterface;
import io.aistac.common.api.sockets.valueholder.CommandBits;
import io.aistac.common.canonical.data.AbstractMemoryBeanCache;
import io.aistac.common.canonical.data.ObjectBean;
import io.aistac.common.canonical.exceptions.ObjectBeanException;
import io.aistac.common.canonical.log.LoggerQueueService;
import io.aistac.common.canonical.properties.TaskPropertiesService;
import static io.aistac.common.data.obcache.objectstore.DataOptionsEnum.QUEUE;

/**
 * The {@code ObjectStore} Class
 *
 * @author Darryl Oatridge
 * @version 1.00 17-Mar-2016
 * @param <T> the object type to be stored
 */
public abstract class ObjectStore<T extends ObjectBean> extends AbstractMemoryBeanCache<T> implements TransportQueueInterface {

    protected final LoggerQueueService LOGGER = LoggerQueueService.getInstance();
    protected final String DATA;

    private final ConnectionBean connection;
    // default object
    private volatile T defaultObject;
    // The data options for storage
    private final DataOptionsEnum[] dataStoreOptions;

    /**
     * The constructor for {@code ObjectDBMS} Class provides information to set up a connection the the persistence
     * server along with the type of data store to implement. The {@code ConnnectionBean} owner will be the name of the manager
     *
     * @param connection the {@code ConnnectionBean} with where to save data too
     * @param dataStoreOptions an array of the type of storage to be used
     */
    public ObjectStore(ConnectionBean connection, DataOptionsEnum... dataStoreOptions) {
        this.connection = connection;
        this.dataStoreOptions = DataOptionsEnum.addToArray(dataStoreOptions);
        this.DATA = "DATA." + this.getClass().getSimpleName().toUpperCase();
        defaultObject = null;
    }

    /**
     * sends a request to retrieve any data persisted at the {@code ConnectionBean} connection.
     */
    protected void getPersistentData() {
        final int command = CommandBits.CMD_REQUEST | CommandBits.REQ_ALL | CommandBits.DATA_NOT_USED;
        this.sendData(command, "");
    }

    /**
     * @return the name of the Task as defined by the {@code TaskPropertiesService}
     */
    public String getTaskOwner() {
        return TaskPropertiesService.TASK_OWNER();
    }

    /*
     * **************************************************
     * P U B L I C    M E T H O D S    F O R    Q U E U E
     * ************************************************
     */

    @Override
    public void deliver(TransportBean t) {
        if(t == null) {
            LOGGER.error(DATA, "The Queue In for '" + connection + "' has recieved ");
            return;
        }
        if(CommandBits.contain(t.getCommand(), CommandBits.CMD_FAILURE)) {
            String msg = "Failure command received from the Transport Delivery";
            if(CommandBits.contain(t.getCommand(), CommandBits.DATA_TEXT) && t.getData() != null && !t.getData().isEmpty()) {
                msg += " with message: " + t.getData();
            }
            LOGGER.error(DATA, msg);
        } else if(CommandBits.contain(t.getCommand(), CommandBits.DATA_BEANXML) && t.getData() != null && !t.getData().isEmpty()) {
            String data = t.getData();
            T transport;
            try {
                transport = T.buildObjectBean(data);
                setObjectInKeyAt(transport.getGroupKey(), transport, transport.getIndex());
            } catch(ObjectBeanException ex) {
                LOGGER.error(DATA, "Error when building the ObjectBean from the Transport Delivery: " + ex);
            }
        } else {
            LOGGER.error(DATA, "Unknown command return from Transport Delivery: " + CommandBits.getStringFromBits(t.getCommand()));
        }
    }

    @Override
    public TransportBean sendData(int command, String data) {
        // check we want send to the queue
        if(QUEUE.isIn(dataStoreOptions)) {
            return TransportQueueInterface.super.sendData(command, data);
        }
        return null;
    }

    @Override
    public ConnectionBean getConnection() {
        return connection;
    }
}
