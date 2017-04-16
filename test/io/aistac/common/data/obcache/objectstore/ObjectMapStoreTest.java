/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package io.aistac.common.data.obcache.objectstore;

import io.aistac.common.data.obcache.objectstore.ObjectMapStore;
import io.aistac.common.canonical.data.example.BeanBuilder;
import io.aistac.common.canonical.data.example.ExampleBean;
import io.aistac.common.api.sockets.handler.connections.ConnectionBean;
import java.util.HashSet;
import java.util.LinkedList;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Darryl
 */
public class ObjectMapStoreTest {

    private ObjectMapStore<ExampleBean> manager;
    private ConnectionBean identity;
    private String managerName = identity.getOwner();

    @Before
    public void setUp() throws Exception {
        identity = (ConnectionBean) BeanBuilder.addBeanValues(new ConnectionBean());
        manager = new ObjectMapStore<>(identity);
    }

    @After
    public void tearDown() throws Exception {

    }

    /**
     * Test of getObject method, of class ObjectMapStore.
     */
    @Test
    public void testGetObject() throws Exception {
        // set the example data
        setExampleData();

        ExampleBean test = new ExampleBean(3, 101, "AA", managerName);
        assertEquals(test, manager.getObject(1, 3));

        // get a number that doesn't exist
        assertNull(manager.getObject(1, 123));
        assertNull(manager.getObject(0, 3));
        assertNull(manager.getObject(10, 3));

        // check the getObject where the key is unknown
        test = new ExampleBean(4, 102, "AB", managerName);
        assertEquals(test, manager.getObject(4));
        test = new ExampleBean(3, 101, "AA", managerName);
        assertEquals(test, manager.getObject(3));

    }

    /**
     * Test of getObjectKey method, of class ObjectMapStore.
     */
    @Test
    public void testGetAllKeysFromIntetifier() throws Exception {
        // set the example data
        setExampleData();

        assertEquals(1, manager.getAllKeysForIdentifier(2).size());
        assertTrue(manager.getAllKeysForIdentifier(2).contains(0));

        assertEquals(2, manager.getAllKeysForIdentifier(3).size());
        assertTrue(manager.getAllKeysForIdentifier(3).contains(1));
        assertTrue(manager.getAllKeysForIdentifier(3).contains(2));

        assertEquals(1, manager.getAllKeysForIdentifier(4).size());
                assertTrue(manager.getAllKeysForIdentifier(4).contains(1));

        assertEquals(2, manager.getAllKeysForIdentifier(5).size());
        assertTrue(manager.getAllKeysForIdentifier(5).contains(1));
        assertTrue(manager.getAllKeysForIdentifier(5).contains(2));
    }

    /**
     * Test of getAllObjects method, of class ObjectMapStore.
     */
    @Test
    public void testGetAllObjects() throws Exception {
        // set the example data
        setExampleData();

        LinkedList<ExampleBean> keyTest = new LinkedList<ExampleBean>();
        LinkedList<ExampleBean> allTest = new LinkedList<ExampleBean>();

        keyTest.add(new ExampleBean(3, 101, "AA", managerName));
        keyTest.add(new ExampleBean(4, 102, "AB", managerName));
        keyTest.add(new ExampleBean(5, 103, "AC", managerName));

        allTest.addAll(keyTest);
        allTest.add(new ExampleBean(2, 11, "Z", managerName));
        allTest.add(new ExampleBean(3, 102, "AC", managerName));
        allTest.add(new ExampleBean(5, 103, "AC", managerName));

        assertEquals(1, manager.getAllObjects(0).size());
        assertEquals(3, manager.getAllObjects(1).size());
        assertEquals(2, manager.getAllObjects(2).size());
        assertEquals(0, manager.getAllObjects(3).size());
        assertEquals(keyTest, manager.getAllObjects(1));
        assertEquals(6, manager.getAllObjects().size());
        for(ExampleBean bean : manager.getAllObjects()) {
            assertTrue(allTest.contains(bean));
        }
        keyTest.clear();
        assertEquals(0, manager.getAllObjects(1).size());
        assertEquals(0, manager.getAllObjects(3).size());


    }

    /**
     * Test of getAllKeys method, of class ObjectMapStore.
     */
    @Test
    public void testGetAllKeys() throws Exception {
        // set the example data
        setExampleData();

        HashSet<Integer> test = new HashSet<Integer>();
        test.add(0);
        test.add(1);
        test.add(2);
        assertEquals(test, manager.getAllKeys());
        test.clear();

        assertEquals(test, manager.getAllKeys());
    }

    /**
     * Test of getAllIdentifier method, of class ObjectMapStore.
     */
    @Test
    public void testGetAllIdentifier() throws Exception {
        // set the example data
        setExampleData();

        HashSet<Integer> test = new HashSet<Integer>();
        test.add(2);
        test.add(3);
        test.add(4);
        test.add(5);
        assertEquals(test, manager.getAllIdentifier());
        test.remove(2);
        assertEquals(test, manager.getAllIdentifier(1));
        test.clear();
        test.add(2);
        assertEquals(test, manager.getAllIdentifier(0));
    }

    /**
     * Test of isIdentifier method, of class ObjectMapStore.
     */
    @Test
    public void testIsIdentifier() throws Exception {
        // set the example data
        setExampleData();

        assertTrue(manager.isIdentifier(2));
        assertTrue(manager.isIdentifier(3));
        assertFalse(manager.isIdentifier(1));
        assertTrue(manager.isIdentifier(0, 2));
        assertTrue(manager.isIdentifier(1, 3));
        assertFalse(manager.isIdentifier(0, 3));
        assertFalse(manager.isIdentifier(1, 2));

    }

    /**
     * Test of setObject method, of class ObjectMapStore.
     */
    @Test
    public void testSetObject() throws Exception {
        // set the example data
        setExampleData();

        long create = manager.getObject(1, 3).getCreated();
        long modified = manager.getObject(1, 3).getModified();
        String managerName = manager.getObject(1, 3).getOwner();
        ExampleBean test = new ExampleBean(3, 999, "ZZZ", managerName);
        assertEquals(3, manager.getAllObjects(1).size());
        manager.setObject(1, test);
        assertEquals(3, manager.getAllObjects(1).size());
        assertEquals(test, manager.getObject(1, 3));
        assertEquals(create, manager.getObject(1, 3).getCreated());
        assertNotSame(modified, manager.getObject(1, 3).getModified());
        assertEquals(managerName, manager.getObject(1, 3).getOwner());

    }

    /**
     * Test of removeObject method, of class ObjectMapStore.
     */
    @Test
    public void testRemoveObject() throws Exception {
        // set the example data
        setExampleData();

        LinkedList<ExampleBean> test = new LinkedList<ExampleBean>();
        test.add(new ExampleBean(3, 101, "AA", managerName));
        test.add(new ExampleBean(4, 102, "AB", managerName));
        manager.removeObject(1, 5);
        assertEquals(test, manager.getAllObjects(1));
        assertFalse(manager.getAllIdentifier(1).contains(5));
        assertNull(manager.removeObject(1, 2));
    }

    /**
     * Test of removeKey method, of class ObjectMapStore.
     */
    @Test
    public void testRemoveKey() throws Exception {
        // set the example data
        setExampleData();

        manager.removeKey(1);
        assertTrue(manager.getAllObjects(1).isEmpty());

    }

    @Test
    public void testXMLDOM() throws Exception {
        // set the example data
        setExampleData();

        String xml = manager.getObject(1, 3).toXML();
        ExampleBean other = new ExampleBean();
        other.setXMLDOM(manager.getObject(1, 3).getXMLDOM().detachRootElement());
        assertEquals(manager.getObject(1, 3), other);
        assertEquals(xml, other.toXML());
    }

    @Test
    public void testNoPersistence() {
        // set the example data
        setExampleData();


        manager = null;
        manager = new ObjectMapStore<>(identity);


        manager.setObject(1, new ExampleBean(1, 10, "name1", managerName));
        manager.setObject(1, new ExampleBean(2, 11, "name2", managerName));

        assertEquals(2, manager.getAllObjects(1).size());
        manager = null;
        manager = new ObjectMapStore<>(identity);

        assertEquals(0, manager.getAllObjects(1).size());
    }

    /**
     * Unit test:
     */
    @Test
    public void unit01_getAllKeysInMap() {
        // set the example data
        setExampleData();
        assertEquals(3, manager.getAllKeys().size());

    }

    private void setExampleData() {
        manager.setObject(0, new ExampleBean(2, 11, "Z", managerName));
        manager.setObject(1, new ExampleBean(3, 101, "AA", managerName));
        manager.setObject(1, new ExampleBean(4, 102, "AB", managerName));
        manager.setObject(1, new ExampleBean(5, 103, "AC", managerName));
        manager.setObject(2, new ExampleBean(3, 102, "AC", managerName));
        manager.setObject(2, new ExampleBean(5, 103, "AC", managerName));

    }
}
