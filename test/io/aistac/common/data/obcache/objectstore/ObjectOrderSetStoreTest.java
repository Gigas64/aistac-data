/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package io.aistac.common.data.obcache.objectstore;

import io.aistac.common.data.obcache.objectstore.ObjectOrderSetStore;
import io.aistac.common.canonical.data.example.BeanBuilder;
import io.aistac.common.canonical.data.example.ExampleBean;
import io.aistac.common.api.sockets.handler.connections.ConnectionBean;
import java.io.File;
import java.util.concurrent.ConcurrentSkipListSet;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Darryl
 */
public class ObjectOrderSetStoreTest {

    private ObjectOrderSetStore<ExampleBean> manager;
    private ConcurrentSkipListSet<ExampleBean> testStore;
    private ConnectionBean identity;
    private String owner = identity.getOwner();

    @Before
    public void setUp() throws Exception {
        identity = (ConnectionBean) BeanBuilder.addBeanValues(new ConnectionBean());
        manager = new ObjectOrderSetStore<>(identity);


    }

    @After
    public void tearDown() throws Exception {
    }

    /**
     * Test of init method, of class ObjectManager.
     */
    @Test
    public void testInit() throws Exception {

        manager.setObject(new ExampleBean(1, 101, "AA", owner));
        manager.setObject(new ExampleBean(7, 102, "BB", owner));
        manager.setObject(new ExampleBean(11, 103, "CC", owner));
        assertEquals(1, manager.getObjectAt(0).getId());
        assertEquals(7, manager.getObjectAt(1).getId());
        assertEquals(11, manager.getObjectAt(2).getId());
        manager.setFirstObject(new ExampleBean(11, 103, "CC", owner));
        assertEquals(11, manager.getObjectAt(0).getId());
        assertEquals(1, manager.getObjectAt(1).getId());
        assertEquals(7, manager.getObjectAt(2).getId());

    }

    @Test
    public void testGetAllObjects() throws Exception {

        manager.setObject(new ExampleBean(1, 101, "AA", owner));
        manager.setObject(new ExampleBean(7, 102, "BB", owner));
        manager.setObject(new ExampleBean(11, 103, "CC", owner));
        assertEquals(false, manager.getAllObjects().isEmpty());
        assertEquals(3, manager.getAllObjects().size());
    }

    /**
     * Test of getObject method, of class ObjectManager.
     */
    @Test
    public void testGetObject() throws Exception {

        manager.setObject(new ExampleBean(3, 101, "AA", owner));
        manager.setObject(new ExampleBean(4, 102, "AB", owner));
        manager.setObject(new ExampleBean(5, 103, "AC", owner));
        // create a test store
        testStore = null;
        testStore = new ConcurrentSkipListSet<ExampleBean>();
        testStore.add(new ExampleBean(3, 101, "AA", owner));
        testStore.add(new ExampleBean(4, 102, "AB", owner));
        testStore.add(new ExampleBean(5, 103, "AC", owner));
        int counter = 0;
        for(ExampleBean e : testStore) {
            assertEquals(manager.getObject(e.getId()), e);
            counter++;
        }
        assertEquals(3, counter);
        assertNull(manager.getObject(1));
    }

    /**
     * Test of method, of class Objectmanager.
     */
    @Test
    public void testGetObjectAt() throws Exception {

        manager.setLastObject(new ExampleBean(3, 101, "AA", owner));
        manager.setFirstObject(new ExampleBean(4, 102, "AB", owner));
        manager.setObjectAt(new ExampleBean(5, 103, "AC", owner), 1);
        assertEquals(4, manager.getFirstObject().getId());
        assertEquals(5, manager.getObjectAt(1).getId());
        assertEquals(3, manager.getLastObject().getId());
    }
}
