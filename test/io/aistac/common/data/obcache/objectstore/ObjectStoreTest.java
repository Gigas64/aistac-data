/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package io.aistac.common.data.obcache.objectstore;

// common imports
import io.aistac.common.data.obcache.objectstore.ObjectMapStore;
import io.aistac.common.data.obcache.objectstore.DataOptionsEnum;
import io.aistac.common.canonical.queue.ObjectBeanQueue;
import io.aistac.common.canonical.data.example.ExampleBean;
import io.aistac.common.data.obcache.testlog.LocalLogger;
import io.aistac.common.api.sockets.handler.connections.ConnectionBean;
import io.aistac.common.api.sockets.handler.connections.ConnectionService;
import io.aistac.common.api.sockets.handler.connections.ConnectionTypeEnum;
import io.aistac.common.api.sockets.transport.TransportBean;
import io.aistac.common.api.sockets.transport.TransportQueueService;
import io.aistac.common.api.sockets.valueholder.CommandBits;
import java.io.File;
import java.util.*;
import java.util.concurrent.ConcurrentSkipListSet;
import static org.hamcrest.CoreMatchers.*;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author Darryl Oatridge
 */
public class ObjectStoreTest {

    private ObjectMapStore<ExampleBean> manager;
    private ConnectionBean connection = new ConnectionBean(0, ConnectionTypeEnum.CLIENT, "localhost", 14164, -1, "ObjectStoreTest");
    private final ObjectBeanQueue<TransportBean> queueIn = TransportQueueService.queue(connection.getQueueIn());
    private final ObjectBeanQueue<TransportBean> queueOut = TransportQueueService.queue(connection.getQueueOut());

    @Before
    public void setLogging() {
        LocalLogger.log();
    }

    @Test(timeout = 500)
    public void testQueueDeliver() throws Exception {
        // start up loging
        final int command = CommandBits.CMD_RESPONSE | CommandBits.REQ_NOT_USED | CommandBits.DATA_BEANXML;
        // register the connection
        connection = ConnectionService.registerConnection(ConnectionTypeEnum.CLIENT, "localhost", 14164);
        // start the manager
        DataOptionsEnum options = DataOptionsEnum.QUEUE;
        manager = new ObjectMapStore<ExampleBean>(connection, options).init();
        assertThat(manager.getAllIdentifier().size(), is(0));
        ExampleBean example = new ExampleBean(2, 11, "Z", connection.getOwner());
        TransportBean t = new TransportBean(1, 2, command, example.toXML(), connection.getOwner());
        queueIn.add(t);
        while(manager.getAllIdentifier().isEmpty()) {}
        assertThat(manager.getAllIdentifier().isEmpty(), is(false));
    }

    @Test
    public void testQueueSend() throws Exception {
        fail("Not yet implemented");
    }

    @Test
    public void testInit() throws Exception {
        fail("Not yet implemented");
    }

        private void setExampleData()  {
        manager.setObject(0, new ExampleBean(2, 11, "Z", connection.getOwner()));
        manager.setObject(1, new ExampleBean(3, 101, "AA", connection.getOwner()));
        manager.setObject(2, new ExampleBean(5, 103, "AC", connection.getOwner()));
    }
}
