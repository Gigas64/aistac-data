/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package io.aistac.common.data.obcache.objectstore;

import io.aistac.common.data.obcache.objectstore.ObjectSingleStore;
import io.aistac.common.canonical.data.ObjectEnum;
import io.aistac.common.canonical.data.example.ExampleBean;
import org.junit.After;
import java.io.File;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Administrator
 */
public class ObjectSingleStoreTest {
    private ObjectSingleStore<ExampleBean> manager;

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    /**
     * Test of getObject method, of class ObjectSingleStore.
     */
    @Test
    public void testGetAndSetObject() throws Exception {
        ExampleBean bean = new ExampleBean(1, 20, "AA", "tester");
        manager.setObject(bean);
        assertEquals(bean, manager.getObject());
        assertEquals(ObjectEnum.DEFAULT_ID.value(),manager.getObject().getId());
    }

}